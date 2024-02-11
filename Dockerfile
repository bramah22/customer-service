FROM openjdk:21-ea-8-jdk-oracle
ARG JAR_FILE=target/customer-service.jar

WORKDIR /opt/app

COPY ${JAR_FILE} customer-service.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]