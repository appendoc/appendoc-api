FROM amazoncorretto:17-alpine3.16
ARG JAR_FILE=appendoc-webserver/build/libs/appendoc-webserver.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
