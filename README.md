# Reward Management Spring Boot Application

This repository contains the Reward Management Spring Boot application. This guide will walk you through the steps required to pull the repository, set it up locally, and configure the necessary properties to get the application running. Additionally, it includes instructions for accessing and using the Swagger API documentation.

## Prerequisites

Before you start, ensure you have the following installed on your local machine:

- **Java 17** or higher
- **Maven 3.6.3** or higher
- **PostgreSQL** database
- **Git**

## Getting Started

### 1. Clone the Repository

First, clone the repository to your local machine using the following command:

```bash
git clone https://github.com/your-username/reward-management.git
```

Navigate into the project directory:

```bash
cd reward-management
```

### 2. Configure Application Properties

The application uses a few externalized configurations. You need to set up the environment variables or update the `application.properties` file with your specific settings.

#### a. Environment Variables

You can create environment variables on your system for sensitive information such as database connection details and secret keys. Set the following environment variables:

```bash
export connection_url=jdbc:postgresql://localhost:5432/your_database
export password=your_db_password
export SECRET_KEY=your_secret_key
```

#### b. Update `application.properties`

Alternatively, you can directly update the `src/main/resources/application.properties` file with your configuration. Replace the placeholders with your actual values:

```properties
# Application Name
spring.application.name=reward_management

# Logging Configuration
spring.main.banner-mode=off
logging.pattern.console = %green(%d{HH:mm:ss.SSS}) %blue(%-5level) %red([%thread]) %yellow(%logger{15})- %msg%n

# Datasource Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=postgres
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=validate

#Springdoc
springdoc.swagger-ui.path=/docs

# Migration Configuration
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.location=classpath:db/migration
spring.flyway.url=jdbc:postgresql://localhost:5432/your_database
spring.flyway.user=postgres
spring.flyway.password=your_db_password
spring.flyway.baseline-version=1

# Database Connection Pooling
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.pool-name=HikariCP
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.leak-detection-threshold=15000

# Exception Handling
spring.mvc.throw-exception-if-no-handler-found=true

# Secret Key Configuration
secretKey=your_secret_key
```

### 3. Build and Run the Application

Use Maven to build and run the application:

```bash
mvn clean install
mvn spring-boot:run
```

### 4. Access the Application

Once the application is running, you can access it at `http://localhost:8080`.

### 5. Access Swagger API Documentation

The application uses Swagger for API documentation. To access Swagger UI:

1. Open your web browser and go to `http://localhost:8080/docs`.
2. You will see an interactive documentation interface where you can explore the available API endpoints, view request and response formats, and test the API methods.

### Swagger Configuration

Swagger UI is configured to provide interactive documentation for the RESTful APIs in this application. It is set up to show API endpoints, their parameters, and responses.

#### Swagger Annotations

The application uses Swagger annotations to document the API. Key annotations include:

- `@OpenAPIDefinition`: Configures OpenAPI specifications.
- `@Operation`: Documents individual API operations.
- `@ApiResponse`: Describes possible responses for an operation.

You can find the Swagger configuration and annotations in the `src/main/java/reward_management/config` package and throughout the controller classes.

## Additional Information

- **Database Migrations**: The application uses Flyway for database migrations. Ensure that your PostgreSQL database is set up correctly and that the migration scripts are located in the `classpath:db/migration` directory.

- **Logging**: Logging is configured to output logs to the console with specific formatting. You can adjust the logging level or pattern as needed in the `application.properties` file.

- **Security**: Ensure that your `SECRET_KEY` is securely managed and not hard-coded in the application code.

## API Request and Response samples
- [Authentication](document/user.md)
- [Reward Management](document/management.md)

## Database Design
- [Database design schema](document/db_design.png)

## Conclusion

With the above steps, you should have the Reward Management application up and running locally. You can also access the Swagger UI to interact with and test the API endpoints. If you encounter any issues, please check the configuration or consult the documentation for further details.
