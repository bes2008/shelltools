#!/bin/bash

CURRENT_SCRIPT=`readlink -f $0`
CURRENT_DIR=`dirname $CURRENT_SCRIPT`
echo "current directory: $CURRENT_DIR"
DC_HOME=$CURRENT_DIR/..
DC_CONF_DIR=$DC_HOME/conf
DC_LOGS_DIR=$DC_HOME/logs
DC_VAR_DIR=$DC_HOME/var


# JAVA_HOME
if [[ -n "$JAVA_17_HOME" ]]; then
  export JAVA_HOME=$JAVA_17_HOME
  export PATH=$JAVA_HOME/bin:$PATH
fi	


currentTime=`date +%Y-%m-%dT%H%M%S`

out_dir=$projectWorkingDir/out
task_log=$DC_LOGS_DIR/update-$currentTime.log

JDBC_URL=$(cat $DC_CONF_DIR/jdbc.conf    | grep "data.connection_string=")
JDBC_URL=${JDBC_URL:23}
JDBC_DRIVER=$(cat $DC_CONF_DIR/jdbc.conf | grep "data.driver_name=")
JDBC_DRIVER=${JDBC_DRIVER:17}
JDBC_USER=$(cat $DC_CONF_DIR/jdbc.conf   | grep "data.user=")
JDBC_USER=${JDBC_USER:10}
JDBC_PASS=$(cat $DC_CONF_DIR/jdbc.conf   | grep "data.password=")
JDBC_PASS=${JDBC_PASS:14}




echo "[WARN]!!! JAVA version >= 1.8.0_251 required"
echo "start to update known vulnerabilities data at $currentTime"

$DC_HOME/bin/dependency-check.sh  --updateonly --propertyfile $DC_CONF_DIR/updatedata.properties --log $task_log --dbDriverName $JDBC_DRIVER --dbUser $JDBC_USER --dbPassword "$JDBC_PASS" --connectionString "$JDBC_URL"














