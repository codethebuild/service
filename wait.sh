#!/bin/bash

CONTAINER_NAME=$1
CONTAINER_PORT=$2

if [ -z $DOCKER_MACHINE_NAME ]; then
  echo Assume docker engine is used directly
  IP_CONTAINER=`docker inspect --format='{{.NetworkSettings.IPAddress}}' $CONTAINER_NAME`
  CONTAINER_PORT=8080
else
  IP_CONTAINER=`docker-machine ip $DOCKER_MACHINE_NAME`
  CONTAINER_PORT=8888
fi

echo until nc -z $IP_CONTAINER $CONTAINER_PORT
until nc -z $IP_CONTAINER $CONTAINER_PORT
do
    echo "waiting for container on port 8888..."
    sleep 0.5
done
