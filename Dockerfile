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
ENV WORK_HOME="/data/app" \
    PROT="8001"

WORKDIR  $WORK_HOME
#挂载宿主机/data/app目录
VOLUME ["/data/app","/data/logs"]

# Add files required to build this image
# ---------------
ADD  $APP_PATH  $WORK_HOME

# Expose default port
# ---------------
EXPOSE $PROT

# Container entry
ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar  ${WORK_HOME}/app.jar"]

