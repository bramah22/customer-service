FROM openjdk:17-alpine3.14
ARG JAR_FILE=target/customer-service.jar

WORKDIR /opt/app

COPY ${JAR_FILE} customer-service.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]