#!/bin/bash
#Copyright (c) 2019-2022 EasyBlog and/or its affiliates. All rights reserved.
#
# EASYBLOG ZEUS MODULE STARTUP SCRIPTS
#---------------------------
set -e

#项目名称
ARTIFACT="zeus"
echo ${ARTIFACT}

#版本
VERSION="1.0.0"
ARTIFACT_VERSION='1.0.0'
BUILD_VERSION='1.0.0'
BASE_DIR="/app"
if [ ! -d "${BASE_DIR}/${ARTIFACT}/data/logs" ]; then
  mkdir -p ${BASE_DIR}/${ARTIFACT}/logs
fi

cd ${BASE_DIR}/${ARTIFACT}


#===========================================================================================
# Check JAVA_HOME Configuration
#===========================================================================================
[ ! -e "${JAVA_HOME}/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "${JAVA_HOME}/bin/java" ] && JAVA_HOME=/usr/java
[ ! -e "${JAVA_HOME}/bin/java" ] && JAVA_HOME=/opt/taobao/java
[ ! -e "${JAVA_HOME}/bin/java" ] && unset JAVA_HOME

echo "JAVA_HOME:" ${JAVA_HOME}

if [ -z "${JAVA_HOME}" ]; then
  if $darwin; then

    if [ -x '/usr/libexec/java_home' ] ; then
      export {JAVA_HOME}=`/usr/libexec/java_home`

    elif [ -d "/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home" ]; then
      export {JAVA_HOME}="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home"
    fi
  else
    JAVA_PATH=`dirname $(readlink -f $(which javac))`
    if [ "x${JAVA_PATH}" != "x" ]; then
      export {JAVA_HOME}=`dirname ${JAVA_PATH} 2>/dev/null`
    fi
  fi
  if [ -z "${JAVA_HOME}" ]; then
        error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
  fi
fi


#===========================================================================================
# Running Params Configuration
#===========================================================================================
ACTIVE_PROFILE="dev"
SERVER_PORT="8001"
while getopts ":m:p:" opt
do
    case $opt in
        m)
            ACTIVE_PROFILE=$OPTARG;;
        p)
            SERVER_PORT=$OPTARG;;    
        ?)
        echo "Unknown parameter"
        exit 1;;
    esac
done

#===========================================================================================
# Packing
#===========================================================================================
echo ">>>>>> Start package the project..."
mvn -v >/dev/null
# shellcheck disable=SC2181
if [ "$?" -ne 0 ]; then
    echo "Maven is not installed or properly configured on your device"
    exit 1
fi
mvn clean package
echo ">>>>>> Package the project successfully!"


#===========================================================================================
# Build Docker Image
#===========================================================================================
app_path="./${ARTIFACT}-web/target/${ARTIFACT}-web-*.jar"
echo ">>>>>> Start to build docker image: ${ARTIFACT}"
docker build --no-cache \
    --build-arg APP_PATH="${app_path}" \
    --build-arg ACTIVE_PROFILE="${ACTIVE_PROFILE}" \
    -t "${ACTIVE_PROFILE}" .

#===========================================================================================
# JVM Configuration
#===========================================================================================
JVM_PARAMS="${JVM_PARAMS} -XX:+UseG1GC"
JVM_PARAMS="${JVM_PARAMS} -Xms1G -Xmx1G"
JVM_PARAMS="${JVM_PARAMS} -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps"
JVM_PARAMS="${JVM_PARAMS} -Xloggc:/data/logs/gc.log"
JVM_PARAMS="${JVM_PARAMS} -XX:NumberOfGCLogFiles=3"
JVM_PARAMS="${JVM_PARAMS} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/data/logs/app.bin"


#===========================================================================================
# Runing Configuration
#===========================================================================================
JAVA_OPTS="${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTS="${JAVA_OPTS} -Dlog4j2.formatMsgNoLookups=true"
JAVA_OPTS="${JAVA_OPTS} -Dlog4j2.formatMsgNoLookups=true"
JAVA_OPTS="${JAVA_OPTS} -Dfile.encoding=UTF-8"
JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=${ACTIVE_PROFILE}"


#===========================================================================================
# Run applicaton
#===========================================================================================
echo ">>>>>> Starting application in ${ACTIVE_PROFILE} env..."
instances=$(docker ps -q -a --filter "name=${ARTIFACT}")
for ins in $instances; do
    docker stop "$ins" && docker rm "$ins"
done

latest_image="${ARTIFACT}"
docker run -e JVM_PARAMS="${JVM_PARAMS}" -e JAVA_OPTS="${JAVA_OPTS}" --name ${ARTIFACT}-${SERVER_PORT} -p ${SERVER_PORT}:${SERVER_PORT} -d "${latest_image}"

echo "Start application on port ${SERVER_PORT} successfully! You can check ${BASE_DIR}/${ARTIFACT}/data/logs/info.log"
