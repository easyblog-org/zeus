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
    PROT="8001"

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
ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar /app.jar"]

