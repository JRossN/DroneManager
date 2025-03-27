# Drone Management System

A Spring Boot application for managing drone deliveries of medications. The system handles drone registration, medication loading, delivery state management, and battery monitoring.

## Features

- Drone registration with unique serial numbers
- Medication loading with weight limit validation
- Battery level monitoring
- State management (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING)
- Automatic state transitions
- H2 database with in-memory storage
- RESTful API endpoints
- Comprehensive test coverage

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Postman (for API testing)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/drone/delivery/
│   │       ├── config/
│   │       ├── controllers/
│   │       ├── models/
│   │       ├── repositories/
│   │       ├── services/
│   │       └── exceptions/
│   └── resources/
│       ├── application.properties
│       └── data.sql
└── test/
    └── java/
        └── com/drone/delivery/
            ├── controllers/
            └── services/
```

## API Endpoints

### Drone Management
- `POST /api/drones` - Register a new drone
- `GET /api/drones/available` - Get available drones for loading
- `GET /api/drones/{id}/battery` - Check drone battery level

### Medication Management
- `POST /api/drones/{id}/load` - Load medications onto a drone
- `GET /api/drones/{id}/medications` - Get medications loaded on a drone

## Building the Project

1. Clone the repository:
```bash
git clone <repository-url>
cd drone-management
```

2. Build the project using Maven:
```bash
mvn clean install
```

## Running the Application

1. Start the application:
```bash
mvn spring:boot run
```

2. The application will start on `http://localhost:8080`

3. Access the H2 Console:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: `` (empty)

## Testing

### Running Unit Tests
```bash
mvn test
```

### API Testing with Postman

1. Import the Postman collection:
   - Open Postman
   - Click "Import"
   - Select the `DroneManagement.postman_collection.json` file

2. Set up environment variables:
   - Create a new environment
   - Add variable `baseUrl` with value `http://localhost:8080`

3. Test the APIs:
   - Register a new drone
   - Load medications
   - Check drone status
   - Monitor battery levels

## Sample API Requests

### Register a Drone
```json
POST /api/drones
{
    "serialNumber": "DRONE011",
    "model": "LIGHTWEIGHT",
    "weightLimit": 300,
    "batteryCapacity": 100
}
```

### Load Medications
```json
POST /api/drones/1/load
[
    {
        "name": "PAINKILLER_1",
        "weight": 100,
        "code": "PKL_001",
        "imageUrl": "http://example.com/painkiller.jpg"
    },
    {
        "name": "ANTIBIOTIC_2",
        "weight": 150,
        "code": "ATB_002",
        "imageUrl": "http://example.com/antibiotic.jpg"
    }
]
```

## Database

The application uses H2 in-memory database:
- Database URL: `jdbc:h2:mem:testdb`
- Initial data is loaded from `src/main/resources/data.sql`
- Data is reset on application restart

## Configuration

Key configuration properties in `application.properties`:
- H2 Database Configuration
  - In-memory database
  - Console enabled at `/h2-console`
- JPA/Hibernate Configuration
  - Hibernate dialect: H2
  - DDL auto: update
  - SQL logging enabled
- Data Initialization
  - SQL init mode: always
  - Defer datasource initialization: true
- Drone Configuration
  - Battery reduction per delivery: 10%

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 