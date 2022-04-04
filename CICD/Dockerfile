FROM java:8

ARG APP_PATH

ENV WORK_HOME "/docker/app"
ENV NAME $APP_NAME
ENV JAVA_OPTS = "-Djava.security.egd=file:/dev/./urandom  -Dlog4j2.formatMsgNoLookups=true"

WORKDIR  $WORK_HOME
#挂载宿主机/tmp目录
VOLUME ["/docker/app"]
#把本地jar包添加进来作为根应用
ADD  $APP_PATH  /app.jar
# 容器暴露8001端口
EXPOSE 8001
#启动容器
ENTRYPOINT ["java","$JAVA_OPTS","-jar","/app.jar"]

