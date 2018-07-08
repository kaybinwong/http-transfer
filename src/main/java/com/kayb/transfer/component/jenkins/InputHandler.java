package com.kayb.transfer.component.jenkins;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huangkaibin
 * @date 2018-07-06
 **/
@Slf4j
@Component
public class InputHandler {

    public String doAction(HttpServletRequest request) throws IOException {
        request.getParameterMap().entrySet().forEach(entry -> {
            log.info("{}: {}", entry.getKey(), Arrays.toString(entry.getValue()));
        });
        String buildUrl = request.getParameter("buildUrl");
        String action = request.getParameter("action");
        String auth = "Basic " + request.getParameter("token");
        // fetch input id
        log.info("fetch input {}", buildUrl + "input");
        final Request requestInput = new Request.Builder()
                .url(buildUrl + "input")
                .addHeader("Authorization", auth)
                .get()
                .build();
        String response = okHttpClient.newCall(requestInput).execute().body().string();
        String approveId = fetchInputs(response);

        String postUrl = buildUrl.endsWith("/") ? buildUrl : buildUrl + "/";
        if (action.equals("agree")) {
            postUrl = (postUrl + "input/" + approveId +"/proceedEmpty");
        } else {
            postUrl = (postUrl + "input/" + approveId +"/abort");
        }
        log.info("post to url {}", postUrl);
        final Request req = new Request.Builder()
                .url(postUrl)
                .addHeader("Authorization", auth)
                .post(RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), new byte[0]))
                .build();
        return okHttpClient.newCall(req).execute().body().string();
    }

    public String fetchInputs(String content) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            log.debug("{}, {}", matcher.group("approveId"), matcher.start()+"...."+matcher.end());
            return matcher.group("approveId");
        }
        throw new RuntimeException("未找到需要的approve信息");
    }
    private OkHttpClient okHttpClient = new OkHttpClient();
    private final Pattern pattern = Pattern.compile("action=\"(?<approveId>.*?)/submit\"");
}
