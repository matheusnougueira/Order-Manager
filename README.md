# Order Manager API

This is a simple Order Manager API built with Spring Boot, Java 17, Hibernate, and PostgreSQL. The API allows users to create, manage, and fulfill orders based on available stock. Orders are automatically fulfilled as soon as stock is available, and email notifications are sent to users when their orders are completed.

## Features

- Create, read, update, delete, and list all entities (Item, Order, StockMovement, User).
- Automatically fulfill orders when stock is available.
- Track the list of stock movements that fulfill each order.
- Send email notifications to users when an order is completed.
- Log all completed orders, stock movements, emails sent, and errors.

## Project Status

This project was developed in **Spring Boot** due to time constraints and the faster development process provided by the framework. I am proficient in both Spring Boot and **Java EE** and can switch to Java EE if required. The core features of the project are complete, including stock movement and order management. However, there is an issue with sending email notifications via the SMTP (Outlook) configuration, which will be resolved in the next update.

## Requirements

- Docker (for PostgreSQL)
- Java 17
- Maven
- Git

## Setup

### Step 1: Clone the repository

First, clone the project to your local machine:

`git clone <repository-url>`

`cd <repository-name>`

### Step 2: Run PostgreSQL with Docker

Make sure Docker is installed and running on your machine. Run the following command to set up a PostgreSQL container for the project:

`docker run -d --name order_manager -p 5432:5432 -e POSTGRES_DB=order_manager -e POSTGRES_USER=sibs -e POSTGRES_PASSWORD=sibs -e PGSSLMODE=disable postgres`

This will start a PostgreSQL database running on port 5432 with the database name `order_manager`, username `sibs`, and password `sibs`.

### Step 3: Configure `application.properties`

Before running the application, configure the `application.properties` file for database and email settings. Make sure to adjust these configurations to fit your environment.

Edit the file at `src/main/resources/application.properties`:

```
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/order_manager
spring.datasource.username=sibs
spring.datasource.password=sibs
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Logging
logging.level.root=DEBUG
logging.file.name=logs/application.log
logging.level.org.hibernate.SQL=DEBUG

# SMTP Configuration (for sending email notifications)
spring.mail.host=smtp.office365.com
spring.mail.port=587
spring.mail.username=your-email@outlook.com
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Step 4: Build and Run the Application

After configuring the database and email, you can now build and run the application using Maven.

`mvn clean install`

`mvn spring-boot:run`

This will start the application, and the API will be accessible at `http://localhost:8080`.

### Step 5: Access Swagger Documentation

The API is fully documented with Swagger. After running the application, you can explore the API endpoints at:

`http://localhost:8080/swagger-ui.html`

## API Endpoints

### User

- **Create User**

`POST /api/users`

Request Body:

```
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

Response:

```
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

- **Get All Users**

`GET /api/users`

Response:

```
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com"
  }
]
```

- **Get User by ID**

`GET /api/users/{id}`

Response:

```
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

- **Update User**

`PUT /api/users/{id}`

Request Body:

```
{
  "name": "Jane Doe",
  "email": "jane.doe@example.com"
}
```

- **Delete User**

`DELETE /api/users/{id}`

### Item

- **Create Item**

`POST /api/items`

Request Body:

```
{
  "name": "Laptop"
}
```

Response:

```
{
  "id": 1,
  "name": "Laptop"
}
```

- **Get All Items**

`GET /api/items`

Response:

```
[
  {
    "id": 1,
    "name": "Laptop"
  }
]
```

- **Get Item by ID**

`GET /api/items/{id}`

Response:

```
{
  "id": 1,
  "name": "Laptop"
}
```

- **Update Item**

`PUT /api/items/{id}`

Request Body:

```
{
  "name": "Desktop"
}
```

- **Delete Item**

`DELETE /api/items/{id}`

### Order

- **Create Order**

`POST /api/orders`

Request Body:

```
{
  "itemId": 1,
  "userId": 1,
  "quantity": 5
}
```

Response:

```
{
  "id": 1,
  "creationDate": "2024-09-16T12:00:00",
  "itemName": "Laptop",
  "quantity": 5,
  "userName": "John Doe",
  "isComplete": false
}
```

- **Get All Orders**

`GET /api/orders`

Response:

```
[
  {
    "id": 1,
    "creationDate": "2024-09-16T12:00:00",
    "itemName": "Laptop",
    "quantity": 5,
    "userName": "John Doe",
    "isComplete": false
  }
]
```

- **Get Order by ID**

`GET /api/orders/{id}`

Response:

```
{
  "id": 1,
  "creationDate": "2024-09-16T12:00:00",
  "itemName": "Laptop",
  "quantity": 5,
  "userName": "John Doe",
  "isComplete": false
}
```

- **Update Order**

`PUT /api/orders/{id}`

Request Body:

```
{
  "itemId": 1,
  "userId": 1,
  "quantity": 3
}
```

- **Delete Order**

`DELETE /api/orders/{id}`

### Stock Movement

- **Create Stock Movement**

`POST /api/stock-movements`

Request Body:

```
{
  "itemId": 1,
  "quantity": 10
}
```

Response:

```
{
  "id": 1,
  "creationDate": "2024-09-16T12:00:00",
  "itemName": "Laptop",
  "quantity": 10
}
```

- **Get All Stock Movements**

`GET /api/stock-movements`

Response:

```
[
  {
    "id": 1,
    "creationDate": "2024-09-16T12:00:00",
    "itemName": "Laptop",
    "quantity": 10
  }
]
```

- **Get Stock Movement by ID**

`GET /api/stock-movements/{id}`

Response:

```
{
  "id": 1,
  "creationDate": "2024-09-16T12:00:00",
  "itemName": "Laptop",
  "quantity": 10
}
```

- **Delete Stock Movement**

`DELETE /api/stock-movements/{id}`

## Conclusion

This API is ready for further enhancements and bug fixes. The project is fully operational with stock movements, order management, and item management. The current issue with email notifications can be resolved once the SMTP configuration is reviewed. Although this project is built in Spring Boot due to time constraints, I am proficient in Java EE and can make adjustments if required.
