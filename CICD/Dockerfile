#Copyright (c) 2019-2022 EasyBlog and/or its affiliates. All rights reserved.
#
# EASYBLOG DOCKERFILES PROJECT
#---------------------------

# Pull base image
# ---------------
FROM java:8

# Maintainer
# ----------
MAINTAINER Frank.HUANG <hx981230@163.com>

# BUILD ARG: target application file path
# ---------------
ARG APP_PATH

# BUILD ARG: applicatgion run profile
# ---------------
ARG PRODUCTION_MODE

# Application run Configuration
# ---------------------------
ENV WORK_HOME="/docker/app" \
    PROT="8001" \
    JAVA_OPTS = "-Djava.security.egd=file:/dev/./urandom \
                 -Dlog4j2.formatMsgNoLookups=true \
                 -Dspring.profiles.active=$PRODUCTION_MODE"

WORKDIR  $WORK_HOME
#挂载宿主机/tmp目录
VOLUME ["/docker/app"]

# Add files required to build this image
# ---------------
ADD  $APP_PATH  /app.jar

# Expose default port
# ---------------
EXPOSE $PROT

# Container entry
ENTRYPOINT ["java","$JAVA_OPTS","-jar","/app.jar"]

