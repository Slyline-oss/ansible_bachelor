FROM amazoncorretto:21-alpine3.17

WORKDIR /app

EXPOSE 8081:8081

COPY app.jar application.properties ./

CMD ["java", "-jar", "app.jar", "--spring.config.location=application.properties"]