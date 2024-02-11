#!/bin/sh

echo "The App is starting..."
exec java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} "customer-service.jar"