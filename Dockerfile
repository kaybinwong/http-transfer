FROM java:8-jre-alpine

#add timezone and default it to Shanghai
ENV TZ=Asia/Shanghai

WORKDIR /
COPY  ./target/*.jar  /app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Xms1024m", "-Xmx1024m", "-Xss512k", "-jar","app.jar", "--server.port=8080"]
CMD []