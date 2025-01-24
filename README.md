# Workouts Tracker

## Introduction

Workouts Tracker is a **Spring Boot** application that helps you store your training data, track your progress, and generate statistics about your workouts. This app solves the problem of workout tracking by providing a structured and efficient way to log exercises and analyze performance trends.

## Installation

To install and run Workouts Tracker, follow these steps:

### **1. Clone the repository**

```sh
git clone https://github.com/jakubkras/api.git
cd api
```

### **2. Configure the database (PostgreSQL)**

Ensure you have **PostgreSQL** installed and running. Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/workouts_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### **3. Build and run the application**

```sh
mvn clean install
java -jar target/workouts-tracker.jar
```

Alternatively, you can use the built-in Spring Boot command:

```sh
mvn spring-boot:run
```

## Usage

Once the application is running, you can interact with it via the provided REST API. Below are some example endpoints:

### **Get all workouts**

```sh
curl -X GET http://localhost:8080/api/workouts
```

### **Add a new workout**

```sh
curl -X POST http://localhost:8080/api/workouts \
     -H "Content-Type: application/json" \
     -d '{"name": "Leg Day", "date": "2024-01-01"}'
```

For a full list of API endpoints, refer to the API documentation (Swagger or Postman collection).

## Docker Support

If you prefer to run the app using **Docker**, use the following command:

```sh
docker-compose up --build
```

Ensure that `docker-compose.yml` is correctly configured with PostgreSQL settings.

## Contributing

If you'd like to contribute to **Workouts Tracker**, follow these steps:

1. **Fork the repository.**
2. **Create a new branch** for your changes.
3. **Implement your changes.**
4. **Write tests** to ensure stability.
5. **Run tests** with:
   ```sh
   mvn test
   ```
6. **Push your changes and submit a pull request.**

## Authors and Acknowledgment

Workouts Tracker was created by **[Jakub Kraś](https://github.com/jakubkras)**.

## Changelog

- **v1.0**: Initial release with basic workout tracking features.
- **v1.1**: Added Docker support and API improvements.
- **v1.2**: Improved database handling and error responses.

## Conclusion

This README provides all the necessary information to install, run, and contribute to the **Workouts Tracker** project. If you have any questions or suggestions, feel free to open an issue on GitHub!

