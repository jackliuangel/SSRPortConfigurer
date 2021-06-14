FROM maven:3.5.4-jdk-8-alpine as maven
WORKDIR /home/app
ENV LD_LIBRARY_PAH /usr/lib
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn dependency:go-offline -B
RUN mvn package
FROM openjdk:8u171-jre-alpine


EXPOSE 8080

COPY ./docker/startup.sh ./
CMD ./startup.sh

COPY --from=maven target/*.jar ./app.jar
