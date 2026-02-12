# spring-boot-category-tree

A Spring Boot v3.4 application implementing a CRUD API for a category tree with parent-child relationships.

## Features

- **Complete CRUD operations** for categories
- **Hierarchical tree structure** with parent-child relationships
- **REST API** with JSON endpoints
- **MapStruct** for entity-DTO mapping
- **JPA/Hibernate** for data persistence
- **H2 in-memory database** for development and testing
- **No Lombok dependency** - all getters/setters manually implemented
- **Bean Validation** for input validation

## Technology Stack

- Spring Boot 3.4.2
- Java 17
- Spring Data JPA
- Hibernate 6.6.5
- MapStruct 1.5.5
- H2 Database
- Gradle 8.5

## Architecture

The application follows a layered architecture:

1. **Controller Layer** (`CategoryController`) - REST API endpoints
2. **Service Layer** (`CategoryService`) - Business logic and transactions
3. **Repository Layer** (`CategoryRepository`) - Data access
4. **Mapper Layer** (`CategoryMapper`) - Entity-DTO conversions using MapStruct
5. **DTO Layer** - Data Transfer Objects for API requests/responses
6. **Entity Layer** (`Category`) - JPA entities

## API Endpoints

### Create a Category
```bash
POST /api/categories
Content-Type: application/json

{
  "name": "Electronics",
  "description": "Electronic devices",
  "parentId": null  // optional, null for root categories
}
```

### Get a Category by ID
```bash
GET /api/categories/{id}
```

### Get All Categories
```bash
GET /api/categories
```

### Get Root Categories (with children)
```bash
GET /api/categories/roots
```

### Get Children of a Category
```bash
GET /api/categories/{id}/children
```

### Update a Category
```bash
PUT /api/categories/{id}
Content-Type: application/json

{
  "name": "Updated Name",
  "description": "Updated description",
  "parentId": 1
}
```

### Delete a Category
```bash
DELETE /api/categories/{id}
```

**Note:** Deleting a category will cascade delete all its children.

## Building and Running

### Prerequisites
- Java 17 or higher
- Gradle 8.5 or use the included Gradle wrapper

### Build the Project
```bash
./gradlew clean build
```

### Run the Application
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### Run Tests
```bash
./gradlew test
```

## Database

The application uses H2 in-memory database for development. The H2 console is available at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Example Usage

### Create a hierarchical structure
```bash
# Create root category
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name":"Electronics","description":"Electronic devices"}'

# Create child category
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name":"Computers","description":"Computing devices","parentId":1}'

# Create grandchild category
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptops","description":"Portable computers","parentId":2}'
```

### Retrieve the tree
```bash
curl http://localhost:8080/api/categories/roots | jq .
```

## Project Structure

```
src/
├── main/
│   ├── java/com/example/categorytree/
│   │   ├── CategoryTreeApplication.java    # Main application class
│   │   ├── controller/
│   │   │   └── CategoryController.java     # REST endpoints
│   │   ├── dto/
│   │   │   ├── CategoryDTO.java           # Response DTO
│   │   │   ├── CategoryCreateDTO.java     # Create request DTO
│   │   │   └── CategoryUpdateDTO.java     # Update request DTO
│   │   ├── entity/
│   │   │   └── Category.java              # JPA entity
│   │   ├── mapper/
│   │   │   └── CategoryMapper.java        # MapStruct mapper
│   │   ├── repository/
│   │   │   └── CategoryRepository.java    # JPA repository
│   │   └── service/
│   │       └── CategoryService.java       # Business logic
│   └── resources/
│       └── application.properties         # Configuration
└── test/
    └── java/com/example/categorytree/
        └── CategoryTreeApplicationTests.java
```

## License

This project is open source and available under the MIT License.
