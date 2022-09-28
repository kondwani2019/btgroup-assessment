#!/bin/sh

nohup java -agentlib:jdwp=transport=dt_socket,address=8988,server=y,suspend=n -Dspring.profiles.active=local -jar target/bt-financial-assessment-1.0-SNAPSHOT.jar --server.address=0.0.0.0 > bt-financial-assessment.log &
