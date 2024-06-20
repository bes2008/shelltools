#!/bin/bash


function func_usage() {
  echo "usage: analyize.sh <projectName> <path> [propertyfile]"
  echo "args:"
  echo "  projectName: the project name, any string without space. required"
  echo "  path: the ANT path of a directory or a file which will be scanned. required"
  echo "  propertyfile: the property file for scan. optional. ref: $DC_HOME/conf/simple-java.properties"
}

if [[ $# < 2 ]]; then
  func_usage
  exit 1
fi	

CURRENT_SCRIPT=`readlink -f $0`
CURRENT_DIR=`dirname $CURRENT_SCRIPT`
echo "current directory: $CURRENT_DIR"
DC_HOME=$CURRENT_DIR/..

pushd $DC_HOME > /dev/null
DC_HOME=`pwd`
echo "depencency home: $DC_HOME"
popd > /dev/null

DC_CONF_DIR=$DC_HOME/conf
DC_LOGS_DIR=$DC_HOME/logs
DC_VAR_DIR=$DC_HOME/var
DC_DATA_DIR=$DC_HOME/data


projectName=$1
path=$2
propertyfile=$3



# JAVA_HOME
if [[ -n "$JAVA_17_HOME" ]]; then
  export JAVA_HOME=$JAVA_17_HOME
  export PATH=$JAVA_HOME/bin:$PATH
fi



currentTime=`date +%Y-%m-%dT%H%M%S`
projectWorkingDir=$DC_VAR_DIR/projects/$projectName

out_dir=$projectWorkingDir/out
mkdir -p $out_dir

task_log=$projectWorkingDir/$projectName-$currentTime.log

JDBC_URL=$(cat $DC_CONF_DIR/jdbc.conf    | grep "data.connection_string=")
JDBC_URL=${JDBC_URL:23}
JDBC_DRIVER=$(cat $DC_CONF_DIR/jdbc.conf | grep "data.driver_name=")
JDBC_DRIVER=${JDBC_DRIVER:17}
JDBC_USER=$(cat $DC_CONF_DIR/jdbc.conf   | grep "data.user=")
JDBC_USER=${JDBC_USER:10}
JDBC_PASS=$(cat $DC_CONF_DIR/jdbc.conf   | grep "data.password=")
JDBC_PASS=${JDBC_PASS:14}


echo "[WARN]!!! JAVA version >= 1.8.0_251 required"
echo "start to analyize project '$projectName' at $currentTime"


SUPPRESSION_ARGS=""
if [[ -f "$DC_CONF_DIR/publishedSuppressions.xml" ]]; then
  SUPPRESSION_ARGS="$SUPPRESSION_ARGS --suppression $DC_CONF_DIR/publishedSuppressions.xml"	
fi 	
if [[ -f "$DC_DATA_DIR/publishedSuppressions.xml" ]]; then
  SUPPRESSION_ARGS="$SUPPRESSION_ARGS --suppression $DC_DATA_DIR/publishedSuppressions.xml"
fi	


PROPERTY_FILE_ARG=""
if [[ -f "$propertyfile" ]]; then
  PROPERTY_FILE_ARG="--propertyfile $propertyfile"
fi	


$DC_HOME/bin/dependency-check.sh $SUPPRESSION_ARGS $PROPERTY_FILE_ARG --project $projectName --prettyPrint --noupdate --format HTML --format CSV --log $task_log --out $out_dir --dbDriverName $JDBC_DRIVER --dbUser $JDBC_USER --dbPassword "$JDBC_PASS" --connectionString "$JDBC_URL" --scan $path




















