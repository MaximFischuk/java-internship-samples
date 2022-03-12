# Build Java project within gradle
FROM gradle:jdk17-alpine AS build

WORKDIR /app

ADD . ./

RUN gradle build -x test

# Copy compiled java application and run within OpenJDK container
FROM openjdk:17-slim-buster

RUN mkdir ./app/

COPY --from=build /app/build/libs/demo-0.0.1-SNAPSHOT.jar ./app/demo-service.jar
COPY --from=0 /app/src/main/resources/bootstrap.yaml ./app/bootstrap.yaml

EXPOSE 80

CMD java -jar ./app/demo-service.jar --PORT=80
