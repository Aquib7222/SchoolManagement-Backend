# FROM eclipse-temurin:21-jdk

# WORKDIR /app

# COPY . .

# RUN chmod +x mvnw
# RUN ./mvnw clean package -DskipTests

# EXPOSE 8080

# CMD ["java", "-jar", "target/schoolmanagementwebsite-0.0.1-SNAPSHOT.jar"]


FROM eclipse-temurin:21-jdk

# Install MySQL client for mysqldump
RUN apt-get update \
    && apt-get install -y default-mysql-client \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/schoolmanagementwebsite-0.0.1-SNAPSHOT.jar"]