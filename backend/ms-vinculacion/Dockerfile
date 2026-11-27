FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
RUN groupadd --gid 10001 corpaliv && useradd --uid 10001 --gid 10001 --no-create-home --shell /usr/sbin/nologin corpaliv \
    && mkdir -p /app/data && chown 10001:10001 /app/data
COPY --from=build /build/target/ms-vinculacion-0.0.1-SNAPSHOT.jar /app/app.jar
USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
