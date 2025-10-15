# EventBridge - CQRS & Event Sourcing 用户管理系统

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.12-orange)
![H2 Database](https://img.shields.io/badge/H2-Database-lightgrey)

一个基于 CQRS（命令查询职责分离）和事件溯源（Event Sourcing）架构的用户管理系统，使用 Spring Boot 和 RabbitMQ 实现。

## 🎯 项目概述

EventBridge 展示了现代微服务架构中的核心模式：
- **CQRS** - 分离命令（写）和查询（读）操作
- **事件驱动架构** - 通过领域事件实现服务间通信
- **事件溯源** - 通过事件序列重建聚合状态
- **最终一致性** - 查询端异步更新读模型

## 🏗️ 架构设计

### 核心架构图

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Command Side  │    │   Event Bridge   │    │   Query Side    │
│                 │    │                  │    │                 │
│  HTTP Requests  │───▶│  Domain Events   │───▶│  Query Models   │
│   User Commands │    │   RabbitMQ       │    │   User Views    │
│   Write Model   │    │   Event Bus      │    │   Read Model    │
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

### 包结构说明

```
src/main/java/com/eventbridge/
├── command/                    # 命令端（写操作）
│   ├── application/           # 应用服务层
│   │   ├── commands/         # 命令对象
│   │   └── UserCommandService.java
│   ├── domain/               # 领域层
│   │   ├── events/          # 领域事件
│   │   ├── model/           # 领域模型
│   │   └── User.java
│   ├── infrastructure/       # 基础设施层
│   │   ├── events/          # 事件发布
│   │   └── persistence/     # 持久化
│   └── web/                 # Web 层
│       └── UserCommandController.java
├── query/                    # 查询端（读操作）
│   ├── application/          # 应用服务层
│   │   ├── eventhandlers/   # 事件处理器
│   │   └── UserQueryService.java
│   ├── infrastructure/      # 基础设施层
│   │   ├── events/         # 事件监听
│   │   ├── model/          # 读模型
│   │   └── persistence/    # 读模型持久化
│   └── web/                # Web 层
│       └── UserQueryController.java
├── common/                  # 通用组件
│   └── event/              # 事件处理注册表
└── config/                 # 配置类
    ├── RabbitMQConfig.java
    ├── AsyncConfig.java
    └── DomainEventMixin.java
```

## 🚀 快速开始

### 前置要求

- Java 17+
- Maven 3.6+
- RabbitMQ 3.12+

### 环境配置

1. **启动 RabbitMQ**
```bash
# 使用 Docker
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

# 或者使用本地安装的 RabbitMQ
rabbitmq-server
```

2. **克隆项目**
```bash
git clone <repository-url>
cd eventbridge
```

3. **配置应用**
```properties
# application.properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

4. **构建和运行**
```bash
mvn clean install
mvn spring-boot:run
```

## 📚 API 文档

### 命令端 API（写操作）

#### 创建用户
```http
POST /api/command/users
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com"
}
```

**响应：**
```json
{
  "userId": "abe5be3a-83d6-437e-abed-ce8ccfecf3d4",
  "message": "User created successfully"
}
```

#### 更新用户邮箱
```http
PUT /api/command/users/{userId}/email
Content-Type: application/json

{
  "newEmail": "new_email@example.com"
}
```

#### 停用用户
```http
POST /api/command/users/{userId}/deactivate
```

### 查询端 API（读操作）

#### 获取所有用户
```http
GET /api/users
```

#### 获取特定用户
```http
GET /api/users/{userId}
```

## 🔧 核心特性

### 1. 事件驱动架构

**领域事件定义：**
```java
public interface DomainEvent {
    String getEventId();
    String getAggregateId();
    Instant getOccurredOn();
    String getEventType();
}
```

**事件发布流程：**
1. 命令处理产生领域事件
2. 事件通过 `DomainEventPublisher` 发布
3. 同时发布到本地事件和 RabbitMQ
4. 查询端监听并处理事件

### 2. CQRS 实现

**命令端**：
- 处理业务逻辑和状态变更
- 发布领域事件
- 使用写模型（`User` 聚合）

**查询端**：
- 监听和处理领域事件
- 维护读模型（`UserView`）
- 提供查询接口

### 3. 幂等性处理

所有事件处理器都实现了幂等性检查：

```java
// 幂等性检查
if (userViewRepository.existsById(userId)) {
    System.out.println("⏭️ 用户视图已存在，跳过创建: " + userId);
    return;
}
```

### 4. RabbitMQ 配置

- **交换机**: `domain-events-exchange` (Topic 类型)
- **队列**:
    - `user-events-queue` (路由键: `user.*`)
    - `order-events-queue` (路由键: `order.*`)
- **消息持久化**: 启用
- **确认机制**: 自动确认

## 🎪 事件流示例

### 用户创建事件流

```
1. HTTP POST /api/command/users
   ↓
2. UserCommandService 处理 CreateUserCommand
   ↓
3. User 聚合创建并发布 UserCreatedEvent
   ↓
4. DomainEventPublisher 发布事件到:
   - 本地 ApplicationEventPublisher
   - RabbitMQ (路由键: user.usercreated)
   ↓
5. 查询端处理:
   - RabbitMQEventsListener 接收消息
   - EventProcessorRegistry 调用处理器
   - UserEventRegistrar 创建 UserView
   ↓
6. 用户视图可用于查询
```

## 🔍 监控和调试

### H2 数据库控制台

应用启动后访问：http://localhost:8080/h2-console

- **JDBC URL**: `jdbc:h2:mem:eventbridgedb`
- **用户名**: `SA`
- **密码**: (空)

### RabbitMQ 管理界面

访问：http://localhost:15672

- **用户名**: `guest`
- **密码**: `guest`

### 应用日志

应用提供了详细的日志输出，包括：
- 事件处理状态
- 数据库操作
- RabbitMQ 消息流
- 错误和异常信息

## 🛠️ 开发指南

### 添加新事件类型

1. **在命令端定义事件**：
```java
public class UserActivatedEvent implements DomainEvent {
    // 实现接口方法
}
```

2. **在 User 聚合中发布事件**：
```java
public void activate() {
    this.status = UserStatus.ACTIVE;
    registerEvent(new UserActivatedEvent(this.id));
}
```

3. **在查询端注册处理器**：
```java
// 在 UserEventRegistrar 中
eventProcessorRegistry.registerProcessor("UserActivated", this::handleUserActivated);
```

4. **实现事件处理器**：
```java
private void handleUserActivated(DomainEvent event) {
    UserActivatedEvent activatedEvent = (UserActivatedEvent) event;
    // 处理逻辑
}
```

### 配置说明

#### RabbitMQ 配置
```java
@Configuration
@EnableRabbit
public class RabbitMQConfig {
    // 配置交换机、队列、绑定、消息转换器等
}
```

#### 异步配置
```java
@Configuration
@EnableAsync
public class AsyncConfig {
    // 配置异步任务执行器
}
```

## 🧪 测试

### 运行测试
```bash
mvn test
```

### 手动测试流程

1. **创建用户**：
```bash
curl -X POST http://localhost:8080/api/command/users \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com"}'
```

2. **查询用户**：
```bash
curl http://localhost:8080/api/users
```

3. **更新邮箱**：
```bash
curl -X PUT http://localhost:8080/api/command/users/{userId}/email \
  -H "Content-Type: application/json" \
  -d '{"newEmail":"updated@example.com"}'
```

## 🐛 故障排除

### 常见问题

1. **RabbitMQ 连接失败**
    - 检查 RabbitMQ 服务是否运行
    - 验证连接配置

2. **事件重复处理**
    - 检查幂等性逻辑
    - 验证事件处理器注册

3. **数据不一致**
    - 检查事件处理日志
    - 验证读模型更新

### 日志分析

应用提供了详细的日志标记：
- `[COMMAND]` - 命令端操作
- `[QUERY-RABBITMQ]` - RabbitMQ 事件处理
- `[QUERY-LOCAL]` - 本地事件处理
- `[EVENT-REGISTRY]` - 事件注册表处理

## 🙏 致谢

- Spring Boot 团队
- RabbitMQ 团队
- 所有贡献者

---
