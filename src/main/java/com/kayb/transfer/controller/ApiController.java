package com.kayb.transfer.controller;

import com.kayb.transfer.component.jenkins.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author huangkaibin
 * @date 2018-07-08
 */
@RestController
public class ApiController {

    @Autowired
    private InputHandler inputHandler;

    /**
     * get转发处理Jenkins的input事件
     * buildUrl=${BUILD_URL}&token=${APIToken}&action=agree|reject
     * buildUrl: jenkins任务地址
     * token: 用户访问jenkins的token
     * action: 同意或拒绝
     */
    @GetMapping("/jenkins/inputs")
    public String handler(HttpServletRequest httpServletRequest) throws IOException {
        return inputHandler.doAction(httpServletRequest);
    }

}
