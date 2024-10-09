# Build stage
FROM maven AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Run stage
FROM openjdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]