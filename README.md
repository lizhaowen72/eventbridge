# EventBridge - CQRS & Event Sourcing User Management System

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.12-orange)
![H2 Database](https://img.shields.io/badge/H2-Database-lightgrey)

A user management system based on CQRS (Command Query Responsibility Segregation) and Event Sourcing architecture, implemented using Spring Boot and RabbitMQ.

## 🎯 Project Overview

EventBridge demonstrates core patterns in modern microservices architecture:
- **CQRS** - Separates command (write) and query (read) operations
- **Event-Driven Architecture** - Implements service communication through domain events
- **Event Sourcing** - Rebuilds aggregate state through event sequences
- **Eventual Consistency** - Asynchronously updates read models on query side

## 🏗️ Architecture Design

### Core Architecture Diagram

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Command Side  │    │   Event Bridge   │    │   Query Side    │
│                 │    │                  │    │                 │
│  HTTP Requests  │───▶│  Domain Events   │───▶│  Query Models   │
│   User Commands │    │   RabbitMQ       │    │   User Views    │
│   Write Model   │    │   Event Bus      │    │   Read Model    │
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

### Package Structure

```
src/main/java/com/eventbridge/
├── command/                    # Command Side (Write Operations)
│   ├── application/           # Application Service Layer
│   │   ├── commands/         # Command Objects
│   │   └── UserCommandService.java
│   ├── domain/               # Domain Layer
│   │   ├── events/          # Domain Events
│   │   ├── model/           # Domain Models
│   │   └── User.java
│   ├── infrastructure/       # Infrastructure Layer
│   │   ├── events/          # Event Publishing
│   │   └── persistence/     # Persistence
│   └── web/                 # Web Layer
│       └── UserCommandController.java
├── query/                    # Query Side (Read Operations)
│   ├── application/          # Application Service Layer
│   │   ├── eventhandlers/   # Event Handlers
│   │   └── UserQueryService.java
│   ├── infrastructure/      # Infrastructure Layer
│   │   ├── events/         # Event Listening
│   │   ├── model/          # Read Models
│   │   └── persistence/    # Read Model Persistence
│   └── web/                # Web Layer
│       └── UserQueryController.java
├── common/                  # Common Components
│   └── event/              # Event Processing Registry
└── config/                 # Configuration Classes
    ├── RabbitMQConfig.java
    ├── AsyncConfig.java
    └── DomainEventMixin.java
```

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Maven 3.6+
- RabbitMQ 3.12+

### Environment Setup

1. **Start RabbitMQ**
```bash
# Using Docker
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

# Or using locally installed RabbitMQ
rabbitmq-server
```

2. **Clone the Project**
```bash
git clone <repository-url>
cd eventbridge
```

3. **Application Configuration**
```properties
# application.properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

4. **Build and Run**
```bash
mvn clean install
mvn spring-boot:run
```

## 📚 API Documentation

### Command Side API (Write Operations)

#### Create User
```http
POST /api/command/users
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Response:**
```json
{
  "userId": "abe5be3a-83d6-437e-abed-ce8ccfecf3d4",
  "message": "User created successfully"
}
```

#### Update User Email
```http
PUT /api/command/users/{userId}/email
Content-Type: application/json

{
  "newEmail": "new_email@example.com"
}
```

#### Deactivate User
```http
POST /api/command/users/{userId}/deactivate
```

### Query Side API (Read Operations)

#### Get All Users
```http
GET /api/users
```

#### Get Specific User
```http
GET /api/users/{userId}
```

## 🔧 Core Features

### 1. Event-Driven Architecture

**Domain Event Definition:**
```java
public interface DomainEvent {
    String getEventId();
    String getAggregateId();
    Instant getOccurredOn();
    String getEventType();
}
```

**Event Publishing Flow:**
1. Command processing generates domain events
2. Events published via `DomainEventPublisher`
3. Published to both local events and RabbitMQ
4. Query side listens and processes events

### 2. CQRS Implementation

**Command Side:**
- Handles business logic and state changes
- Publishes domain events
- Uses write model (`User` aggregate)

**Query Side:**
- Listens to and processes domain events
- Maintains read model (`UserView`)
- Provides query interfaces

### 3. Idempotency Handling

All event handlers implement idempotency checks:

```java
// Idempotency check
if (userViewRepository.existsById(userId)) {
    System.out.println("⏭️ User view already exists, skipping creation: " + userId);
    return;
}
```

### 4. RabbitMQ Configuration

- **Exchange**: `domain-events-exchange` (Topic type)
- **Queues**: 
  - `user-events-queue` (routing key: `user.*`)
  - `order-events-queue` (routing key: `order.*`)
- **Message Persistence**: Enabled
- **Acknowledgment**: Auto acknowledgment

## 🎪 Event Flow Example

### User Creation Event Flow

```
1. HTTP POST /api/command/users
   ↓
2. UserCommandService processes CreateUserCommand
   ↓
3. User aggregate created and publishes UserCreatedEvent
   ↓
4. DomainEventPublisher publishes event to:
   - Local ApplicationEventPublisher
   - RabbitMQ (routing key: user.usercreated)
   ↓
5. Query side processing:
   - RabbitMQEventsListener receives message
   - EventProcessorRegistry invokes handler
   - UserEventRegistrar creates UserView
   ↓
6. User view available for querying
```

## 🔍 Monitoring and Debugging

### H2 Database Console

After application starts, visit: http://localhost:8080/h2-console

- **JDBC URL**: `jdbc:h2:mem:eventbridgedb`
- **Username**: `SA`
- **Password**: (empty)

### RabbitMQ Management Interface

Visit: http://localhost:15672

- **Username**: `guest`
- **Password**: `guest`

### Application Logs

The application provides detailed log output including:
- Event processing status
- Database operations
- RabbitMQ message flow
- Error and exception information

## 🛠️ Development Guide

### Adding New Event Types

1. **Define Event in Command Side:**
```java
public class UserActivatedEvent implements DomainEvent {
    // Implement interface methods
}
```

2. **Publish Event in User Aggregate:**
```java
public void activate() {
    this.status = UserStatus.ACTIVE;
    registerEvent(new UserActivatedEvent(this.id));
}
```

3. **Register Handler in Query Side:**
```java
// In UserEventRegistrar
eventProcessorRegistry.registerProcessor("UserActivated", this::handleUserActivated);
```

4. **Implement Event Handler:**
```java
private void handleUserActivated(DomainEvent event) {
    UserActivatedEvent activatedEvent = (UserActivatedEvent) event;
    // Processing logic
}
```

### Configuration Details

#### RabbitMQ Configuration
```java
@Configuration
@EnableRabbit
public class RabbitMQConfig {
    // Configure exchange, queues, bindings, message converters, etc.
}
```

#### Async Configuration
```java
@Configuration
@EnableAsync
public class AsyncConfig {
    // Configure async task executor
}
```

## 🧪 Testing

### Running Tests
```bash
mvn test
```

### Manual Testing Process

1. **Create User:**
```bash
curl -X POST http://localhost:8080/api/command/users \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com"}'
```

2. **Query Users:**
```bash
curl http://localhost:8080/api/users
```

3. **Update Email:**
```bash
curl -X PUT http://localhost:8080/api/command/users/{userId}/email \
  -H "Content-Type: application/json" \
  -d '{"newEmail":"updated@example.com"}'
```

## 🐛 Troubleshooting

### Common Issues

1. **RabbitMQ Connection Failed**
   - Check if RabbitMQ service is running
   - Verify connection configuration

2. **Event Duplicate Processing**
   - Check idempotency logic
   - Verify event handler registration

3. **Data Inconsistency**
   - Check event processing logs
   - Verify read model updates

### Log Analysis

Application provides detailed log markers:
- `[COMMAND]` - Command side operations
- `[QUERY-RABBITMQ]` - RabbitMQ event processing
- `[QUERY-LOCAL]` - Local event processing
- `[EVENT-REGISTRY]` - Event registry processing

## 📈 Performance Considerations

- **Async Processing**: Event processing uses async mechanisms, doesn't block command side
- **Batch Operations**: Supports batch event processing
- **Connection Pooling**: Uses HikariCP database connection pool
- **Message Persistence**: RabbitMQ message persistence ensures data durability
