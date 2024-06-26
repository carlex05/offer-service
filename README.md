# Offer Service

This project is a simple RESTful service for managing offers, developed with Spring Boot and following a hexagonal architecture. It includes various design patterns and architectural principles like microservices, Domain-Driven Design (DDD), Repository Pattern, Dependency Injection, and Test-Driven Development (TDD). The service uses Spring Data JDBC and an in-memory H2 database for simplicity and efficiency.

## Prerequisites

- Java 21
- Maven 3.x

## Project Setup

### Clone the Repository

```sh
git clone https://github.com/carlex05/offer-service.git
cd offer-service
```

### Build the Project

```sh
mvn clean install
```
## Running the Application
The application uses an in-memory H2 database, which will be initialized at startup. The database is ephemeral and will be recreated every time the application starts.
```sh
mvn spring-boot:run
```
## Testing the Application

The project includes a Postman collection named Offer-Service.postman_collection.json that can be used to test the service endpoints.
### Import the Collection

- Open Postman.
- Click on the Import button.
- Select the Offer-Service.postman_collection.json file and import it.

### Available Endpoints

- POST /offers: Create a new offer.
- GET /offers/{id}: Retrieve an offer by its ID.
- GET /offers: Retrieve all offers.
- PUT /offers/{id}: Update an offer by its ID.
- DELETE /offers/{id}: Delete an offer by its ID.

### Example Offer JSON

```json
{
"offerId": 1,
"brandId": 1,
"startDate": "2024-01-01T00:00:00Z",
"endDate": "2024-12-31T23:59:59Z",
"priceListId": 1,
"productPartnumber": "000100233",
"priority": 0,
"price": 35.50,
"currencyIso": "EUR"
}
```

## Project Details
### Architecture

This project is developed following a hexagonal architecture, also known as clean architecture. This approach ensures that the core business logic (domain) is independent of external systems like databases, UI, or frameworks.
### Design Patterns and Principles

- Microservices: The service is designed to be part of a microservices architecture.
- DDD (Domain-Driven Design): The project focuses on the domain of Offer.
- Repository Pattern: Used to separate the logic that retrieves data from the data access logic.
- Dependency Injection: Implemented using Spring's @Autowired and constructor injection.
- TDD (Test-Driven Development): Tests are written prior to implementation to ensure the functionality works as expected.
- Inversion of Dependencies: The service depends on abstractions rather than concrete implementations.
- KISS (Keep It Simple): The project avoids overcomplicating the design and implementation.

### Decisions and Trade-offs

- Spring Data JDBC vs. JPA: The project uses Spring Data JDBC for simplicity, lightness, and better performance for this specific case. JPA is more suitable for complex systems with extensive relationships and requirements.
- In-Memory Database: H2 is used for simplicity and ease of testing. In a real-world scenario, this would be replaced with a persistent database.

### Validations

The OfferDto class includes validation annotations to ensure the correctness of the input data:

- @NotNull for mandatory fields.
- @Size for ensuring the productPartnumber has exactly 9 characters.

