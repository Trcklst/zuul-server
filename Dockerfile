FROM maven:3.6.2-jdk-11-slim as maven
# set deployment directory
WORKDIR /app
# copy the Project Object Model file
COPY ./pom.xml ./pom.xml
# fetch all dependencies
RUN mvn dependency:go-offline -B
# copy your other files
COPY ./src ./src
# build for release
RUN mvn package && cp target/zuul-server-*.jar zuul-server.jar

FROM openjdk:11
# set deployment directory
WORKDIR /app
# copy over the built artifact from the maven image
COPY --from=maven /app/zuul-server.jar ./zuul-server.jar
# set the startup command to run your binary
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "/app/zuul-server.jar"]