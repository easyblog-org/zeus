#!/bin/bash
#########################################################################

#Copyright @ www.easyblog.top 2022
#########################################################################
set -e

#项目名称
PROJECT_NAME="user"
#版本
VERSION="1.0.0"
PROJECT_VERSION='1.0.0'
BUILD_VERSION='1.0.0'

######################################################BUILD START#######################################################
#maven编译打包
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

#获取项目maven版本或者根据maven版本生成build版本
version() {
  PROJECT_VERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.version -q -DforceStdout)
  random_version=$(date +%s%N | md5sum | head -c 8)
  opt=$1
  if [ "$opt" -eq 1 ]; then
    VERSION="$PROJECT_VERSION""$random_version"
  elif [ "$opt" -eq 2 ]; then
    VERSION="$random_version"
  elif [ "$opt" -eq 3 ]; then
    VERSION="$PROJECT_VERSION"
  fi
}

#构建docker镜像
buildDockerImage() {
  version 3
  log_show "Start to build docker image: ""$PROJECT_NAME":"$BUILD_VERSION"
  app_path=./"$PROJECT_NAME"-web/target/"$PROJECT_NAME"-web-"$PROJECT_VERSION".jar
  docker build --build-arg APP_PATH="$app_path" -t "$PROJECT_NAME":"$BUILD_VERSION" .
}

#######################################################BUILD END########################################################

#######################################################RUN START########################################################
# 检查应用的启动状态：3次调用指定健康状况检查接口，有1次响应即可
checkHealth() {
  times=3
  while [ "$times" -gt 0 ]; do
    sleep 10s
    rest=$(curl localhost:8001/status)
    echo "$rest"
    ((times -= 1))
  done
  if [ "$times" -ge 0 ]; then
    log_success "Startup application success!"
  else
    log_error "Startup application failed!"
  fi
}

stopOldInstanceIfNeed() {
  instances=$(docker ps -q -a --filter "name=app")
  for item in $instances; do
    docker stop "$item" && docker rm "$item"
  done
}

start() {
  profile=$1
  if [ -z "$profile" ]; then
    profile="dev"
  fi
  log_show "Start application in dev ${profile}..."
  stopOldInstanceIfNeed
  # shellcheck disable=SC2181
  if [ "$?" -ne 0 ]; then
    log_error "BUILD DOCKER IMAGE FAILED"
  fi
  latest_image=$(docker images | grep "user" | awk -F" " '{printf("%s:%s\n",$1,$2)}' | head -n 1)
  if [ "prod" == "$profile" ]; then
    docker run --name app-8001 -p 8001:8001 --link mysql:mysql --link redis:redis -d "${latest_image}"
    docker run --name app-8002 -p 8002:8001 --link mysql:mysql --link redis:redis -d "${latest_image}"
  else
    docker run --name app-8001 -p 8001:8001 --link mysql:mysql --link redis:redis -d "${latest_image}"
  fi
}
########################################################RUN END#########################################################

#######################################################LOG START########################################################
log() {
  echo -e "\033[${1}m[$2] ------------------------------------------------------------------------\033[0m"
  echo -e "\033[${1}m[$2] $3\033[0m"
  echo -e "\033[${1}m[$2] ------------------------------------------------------------------------\033[0m"
}

log_debug() {
  log 30 "DEBUG" "$*"
}

log_error() {
  log 31 "ERROR" "$*"
}

log_success() {
  log 32 "SUCCESS" "$*"
}

log_warn() {
  log 33 "WARN" "$*"
}

log_show() {
  log 34 "INFO" "$*"
}
########################################################LOG END#########################################################

#main
package &&
  buildDockerImage &&
  start "$1" &&
  checkHealth
