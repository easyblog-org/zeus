#!/bin/bash
#Copyright (c) 2019-2022 EasyBlog and/or its affiliates. All rights reserved.
#
# EASYBLOG MODULE USER STARTUP SCRIPTS
#---------------------------
set -e

#项目名称
PROJECT_NAME="user"
#版本
VERSION="1.0.0"
PROJECT_VERSION='1.0.0'
BUILD_VERSION='1.0.0'
PRODUCTION_MODE="dev"
#启动参数
JAVA_OPTS=""

#---------------------------
# Init build and run env
#---------------------------
init_env() {
  init_product_model "$1"
  # shellcheck disable=SC2181
  if [ "$?" -ne 0 ]; then
    log_error "INIT BUILD ENV FAILED"
    exit
  fi
  JAVA_OPTS="${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom \
                            -Dlog4j2.formatMsgNoLookups=true \
                            -Dspring.profiles.active=$PRODUCTION_MODE"
}

#---------------------------
# Init profiles,default profiles is 'dev'
#---------------------------
init_product_model() {
  profile=$1
  if [ -n "$profile" ] && [ "$profile" == "prod" ]; then
    PRODUCTION_MODE="prod"
  else
    PRODUCTION_MODE="dev"
  fi
  return 0
}

#---------------------------
# Build project with maven
#---------------------------
package() {
  mvn -v >/dev/null
  # shellcheck disable=SC2181
  if [ "$?" -ne 0 ]; then
    log_error "Maven is not installed or properly configured on your device"
    return 1
  fi
  log_show "Start build the project..."
  mvn clean package && version 1 && BUILD_VERSION="$VERSION" && log_success 'BUILD VERSION: '"$BUILD_VERSION"
  return $?
}

#---------------------------
# Get maven project version or generate build version
#---------------------------
version() {
  PROJECT_VERSION="$(mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.version -q -DforceStdout)"
  random_version=$(date +%s%N | md5sum | head -c 8)
  opt=$1
  if [ "$opt" -eq 1 ]; then
    VERSION="$PROJECT_VERSION""$random_version"
  elif [ "$opt" -eq 2 ]; then
    VERSION="$random_version"
  elif [ "$opt" -eq 3 ]; then
    VERSION="$PROJECT_VERSION"
  fi
  return 0
}

#---------------------------
# Build docker image based maven build output(jar package)
#---------------------------
build_docker_image() {
  version 3
  app_path="./${PROJECT_NAME}-web/target/${PROJECT_NAME}-web-${PROJECT_VERSION}.jar"
  log_show "Start to build docker image: ${PROJECT_NAME}:${BUILD_VERSION}" "APP_PATH=$app_path" "Model: $PRODUCTION_MODE"
  docker build --no-cache \
    --build-arg APP_PATH="$app_path" \
    --build-arg PRODUCTION_MODE="${PRODUCTION_MODE}" \
    -t "${PROJECT_NAME}:${BUILD_VERSION}" .
  return 0
}

#---------------------------
# Stop all running or stopped docker instances whose name is 'app'
#---------------------------
stop_old_instance_if_need() {
  instances=$(docker ps -q -a --filter "name=app")
  for item in $instances; do
    docker stop "$item" && docker rm "$item"
  done
}

#---------------------------
# Run application
#---------------------------
start() {
  log_show "Start application in ${PRODUCTION_MODE} model..."
  stop_old_instance_if_need
  # shellcheck disable=SC2181
  if [ "$?" -ne 0 ]; then
    log_error "BUILD DOCKER IMAGE FAILED"
  fi
  latest_image="${PROJECT_NAME}:${BUILD_VERSION}"
  if [ "prod" == "$PRODUCTION_MODE" ]; then
    docker run -e JAVA_OPTS="$JAVA_OPTS" --name app-8001 -p 8001:8001 -d "${latest_image}"
    docker run -e JAVA_OPTS="$JAVA_OPTS" --name app-8002 -p 8002:8001 -d "${latest_image}"
  else
    docker run -e JAVA_OPTS="$JAVA_OPTS" --name app-8001 -p 8001:8001 -d "${latest_image}"
  fi
}

#---------------------------
# Check the application startup status: If one of the three request attempts to
# the specified health check interface successfully responds, the startup can be determined to be successful.
#---------------------------
check_health() {
  times=3
  while [ "$times" -gt 0 ]; do
    sleep 20s
    rest="$(curl localhost:8001/status)"
    echo "$rest"
    ((times -= 1))
  done
  if [ "$times" -ge 0 ]; then
    log_success "Startup application success!"
  else
    log_error "Startup application failed!"
  fi
}

#---------------------------
# Common log output style
#---------------------------
log() {
  echo -e "\033[${1}m[$2] ------------------------------------------------------------------------\033[0m"
  echo -e "\033[${1}m[$2] $3\033[0m"
  echo -e "\033[${1}m[$2] ------------------------------------------------------------------------\033[0m"
}

#---------------------------
# Debug mode log, output character color is red
#---------------------------
log_error() {
  log 31 "ERROR" "$*"
}

#---------------------------
# Success mode log, output character color is green
#---------------------------
log_success() {
  log 32 "SUCCESS" "$*"
}

#---------------------------
# Info mode log, output character color is blue
#---------------------------
log_show() {
  log 34 "INFO" "$*"
}


#---------------------------
# main
#---------------------------
init_env "$1" &&
  package &&
  build_docker_image &&
  start &&
  check_health

