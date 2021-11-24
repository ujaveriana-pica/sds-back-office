FROM maven:3.6.3-jdk-11-slim as BUILD
WORKDIR /usr/src/app
COPY pom.xml .
COPY checkstyle.xml .
COPY checkstyle-supressions.xml .
#RUN mvn dependency:go-offline
COPY src src
RUN mvn -B -e -C -T 1C package -Dquarkus.package.type=uber-jar

# Start with a base image containing Java runtime
FROM openjdk:11.0.13-jre-slim

# Add Maintainer Info
LABEL maintainer="juancastellanosm@gmail.com"
#RUN sed -i 's/main/main contrib/g' /etc/apt/sources.list && apt-get update && apt-get install -y msttcorefonts && apt-get install -y ttf-mscorefonts-installer && fc-cache
# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
COPY --from=BUILD /usr/src/app/target/*.jar  quarkus-run.jar

# Run the jar file 
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/quarkus-run.jar"]
