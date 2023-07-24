FROM maven:3.6.3-jdk-11-slim@sha256:68ce1cd457891f48d1e137c7d6a4493f60843e84c9e2634e3df1d3d5b381d36c as build_step
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim@sha256:31a5d3fa2942eea891cf954f7d07359e09cf1b1f3d35fb32fedebb1e3399fc9e
RUN mkdir /application
COPY --from=build_step /project/target/ads-0.0.1-SNAPSHOT.jar /application
WORKDIR /application
CMD ["java", "-jar", "ads-0.0.1-SNAPSHOT.jar"]