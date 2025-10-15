/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home/bin/java -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:51435,suspend=y,server=n -javaagent:/Users/lizhaowen/Library/Caches/JetBrains/IdeaIC2025.2/captureAgent/debugger-agent.jar=file:///var/folders/jq/1xjy2kl974s10pp28y249sw80000gn/T/capture4342510357234325533.props -agentpath:/private/var/folders/jq/1xjy2kl974s10pp28y249sw80000gn/T/idea_libasyncProfiler_dylib_temp_folder/libasyncProfiler.dylib=version,jfr,event=wall,interval=10ms,cstack=no,file=/Users/lizhaowen/IdeaSnapshots/EventBridgeApplication_2025_10_15_095624.jfr,log=/private/var/folders/jq/1xjy2kl974s10pp28y249sw80000gn/T/EventBridgeApplication_2025_10_15_095624.jfr.log.txt,logLevel=DEBUG -Dkotlinx.coroutines.debug.enable.creation.stack.trace=false -Ddebugger.agent.enable.coroutines=true -Dkotlinx.coroutines.debug.enable.flows.stack.trace=true -Dkotlinx.coroutines.debug.enable.mutable.state.flows.stack.trace=true -Dfile.encoding=UTF-8 -classpath /Users/lizhaowen/ideaProjects/eventbridge/target/classes:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-starter-web/3.2.0/spring-boot-starter-web-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-starter/3.2.0/spring-boot-starter-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot/3.2.0/spring-boot-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-autoconfigure/3.2.0/spring-boot-autoconfigure-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-starter-logging/3.2.0/spring-boot-starter-logging-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/ch/qos/logback/logback-classic/1.4.11/logback-classic-1.4.11.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/ch/qos/logback/logback-core/1.4.11/logback-core-1.4.11.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/apache/logging/log4j/log4j-to-slf4j/2.21.1/log4j-to-slf4j-2.21.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/apache/logging/log4j/log4j-api/2.21.1/log4j-api-2.21.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/slf4j/jul-to-slf4j/2.0.9/jul-to-slf4j-2.0.9.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/yaml/snakeyaml/2.2/snakeyaml-2.2.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-starter-json/3.2.0/spring-boot-starter-json-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/fasterxml/jackson/core/jackson-databind/2.15.3/jackson-databind-2.15.3.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/fasterxml/jackson/core/jackson-annotations/2.15.3/jackson-annotations-2.15.3.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/fasterxml/jackson/core/jackson-core/2.15.3/jackson-core-2.15.3.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.15.3/jackson-datatype-jdk8-2.15.3.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.15.3/jackson-datatype-jsr310-2.15.3.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.15.3/jackson-module-parameter-names-2.15.3.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-starter-tomcat/3.2.0/spring-boot-starter-tomcat-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/apache/tomcat/embed/tomcat-embed-core/10.1.16/tomcat-embed-core-10.1.16.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/apache/tomcat/embed/tomcat-embed-el/10.1.16/tomcat-embed-el-10.1.16.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/apache/tomcat/embed/tomcat-embed-websocket/10.1.16/tomcat-embed-websocket-10.1.16.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-web/6.1.1/spring-web-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-beans/6.1.1/spring-beans-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/io/micrometer/micrometer-observation/1.12.0/micrometer-observation-1.12.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/io/micrometer/micrometer-commons/1.12.0/micrometer-commons-1.12.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-webmvc/6.1.1/spring-webmvc-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-aop/6.1.1/spring-aop-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-context/6.1.1/spring-context-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-expression/6.1.1/spring-expression-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-starter-data-jpa/3.2.0/spring-boot-starter-data-jpa-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-starter-aop/3.2.0/spring-boot-starter-aop-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/aspectj/aspectjweaver/1.9.20.1/aspectjweaver-1.9.20.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-starter-jdbc/3.2.0/spring-boot-starter-jdbc-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/zaxxer/HikariCP/5.0.1/HikariCP-5.0.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-jdbc/6.1.1/spring-jdbc-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/hibernate/orm/hibernate-core/6.3.1.Final/hibernate-core-6.3.1.Final.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/jakarta/persistence/jakarta.persistence-api/3.1.0/jakarta.persistence-api-3.1.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/jakarta/transaction/jakarta.transaction-api/2.0.1/jakarta.transaction-api-2.0.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/jboss/logging/jboss-logging/3.5.3.Final/jboss-logging-3.5.3.Final.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/hibernate/common/hibernate-commons-annotations/6.0.6.Final/hibernate-commons-annotations-6.0.6.Final.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/io/smallrye/jandex/3.1.2/jandex-3.1.2.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/fasterxml/classmate/1.6.0/classmate-1.6.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/net/bytebuddy/byte-buddy/1.14.10/byte-buddy-1.14.10.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/glassfish/jaxb/jaxb-runtime/4.0.4/jaxb-runtime-4.0.4.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/glassfish/jaxb/jaxb-core/4.0.4/jaxb-core-4.0.4.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/eclipse/angus/angus-activation/2.0.1/angus-activation-2.0.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/glassfish/jaxb/txw2/4.0.4/txw2-4.0.4.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/sun/istack/istack-commons-runtime/4.1.2/istack-commons-runtime-4.1.2.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/antlr/antlr4-runtime/4.10.1/antlr4-runtime-4.10.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/data/spring-data-jpa/3.2.0/spring-data-jpa-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/data/spring-data-commons/3.2.0/spring-data-commons-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-orm/6.1.1/spring-orm-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-tx/6.1.1/spring-tx-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-aspects/6.1.1/spring-aspects-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/boot/spring-boot-starter-amqp/3.2.0/spring-boot-starter-amqp-3.2.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-messaging/6.1.1/spring-messaging-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/amqp/spring-rabbit/3.1.0/spring-rabbit-3.1.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/amqp/spring-amqp/3.1.0/spring-amqp-3.1.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/retry/spring-retry/2.0.4/spring-retry-2.0.4.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/rabbitmq/amqp-client/5.19.0/amqp-client-5.19.0.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/com/h2database/h2/2.2.224/h2-2.2.224.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/jakarta/xml/bind/jakarta.xml.bind-api/4.0.1/jakarta.xml.bind-api-4.0.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/jakarta/activation/jakarta.activation-api/2.1.2/jakarta.activation-api-2.1.2.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-core/6.1.1/spring-core-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/springframework/spring-jcl/6.1.1/spring-jcl-6.1.1.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/org/projectlombok/lombok/1.18.30/lombok-1.18.30.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar:/Users/lizhaowen/Tools/apache-maven-3.9.11/repository/jakarta/annotation/jakarta.annotation-api/2.1.1/jakarta.annotation-api-2.1.1.jar:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar com.eventbridge.EventBridgeApplication
Connected to the target VM, address: '127.0.0.1:51435', transport: 'socket'

.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::                (v3.2.0)

2025-10-15T09:56:24.772+08:00  INFO 23874 --- [eventbridge] [           main] com.eventbridge.EventBridgeApplication   : Starting EventBridgeApplication using Java 17.0.16 with PID 23874 (/Users/lizhaowen/ideaProjects/eventbridge/target/classes started by lizhaowen in /Users/lizhaowen/ideaProjects/eventbridge)
2025-10-15T09:56:24.773+08:00 DEBUG 23874 --- [eventbridge] [           main] com.eventbridge.EventBridgeApplication   : Running with Spring Boot v3.2.0, Spring v6.1.1
2025-10-15T09:56:24.773+08:00  INFO 23874 --- [eventbridge] [           main] com.eventbridge.EventBridgeApplication   : No active profile set, falling back to 1 default profile: "default"
2025-10-15T09:56:25.012+08:00  INFO 23874 --- [eventbridge] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-10-15T09:56:25.036+08:00  INFO 23874 --- [eventbridge] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 20 ms. Found 2 JPA repository interfaces.
2025-10-15T09:56:25.247+08:00  INFO 23874 --- [eventbridge] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2025-10-15T09:56:25.250+08:00  INFO 23874 --- [eventbridge] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-10-15T09:56:25.251+08:00  INFO 23874 --- [eventbridge] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.16]
2025-10-15T09:56:25.277+08:00  INFO 23874 --- [eventbridge] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-10-15T09:56:25.278+08:00  INFO 23874 --- [eventbridge] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 483 ms
2025-10-15T09:56:25.291+08:00  INFO 23874 --- [eventbridge] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2025-10-15T09:56:25.354+08:00  INFO 23874 --- [eventbridge] [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:eventbridgedb user=SA
2025-10-15T09:56:25.355+08:00  INFO 23874 --- [eventbridge] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2025-10-15T09:56:25.359+08:00  INFO 23874 --- [eventbridge] [           main] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:eventbridgedb'
2025-10-15T09:56:25.408+08:00  INFO 23874 --- [eventbridge] [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2025-10-15T09:56:25.424+08:00  INFO 23874 --- [eventbridge] [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.3.1.Final
2025-10-15T09:56:25.435+08:00  INFO 23874 --- [eventbridge] [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2025-10-15T09:56:25.510+08:00  INFO 23874 --- [eventbridge] [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2025-10-15T09:56:25.793+08:00  INFO 23874 --- [eventbridge] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2025-10-15T09:56:25.798+08:00 DEBUG 23874 --- [eventbridge] [           main] org.hibernate.SQL                        :
drop table if exists user_views cascade
Hibernate:
drop table if exists user_views cascade
2025-10-15T09:56:25.799+08:00 DEBUG 23874 --- [eventbridge] [           main] org.hibernate.SQL                        :
drop table if exists users cascade
Hibernate:
drop table if exists users cascade
2025-10-15T09:56:25.801+08:00 DEBUG 23874 --- [eventbridge] [           main] org.hibernate.SQL                        :
create table user_views (
created_at timestamp(6),
last_updated timestamp(6),
email varchar(255) not null,
status varchar(255) check (status in ('ACTIVE','INACTIVE')),
user_id varchar(255) not null,
username varchar(255) not null,
primary key (user_id)
)
Hibernate:
create table user_views (
created_at timestamp(6),
last_updated timestamp(6),
email varchar(255) not null,
status varchar(255) check (status in ('ACTIVE','INACTIVE')),
user_id varchar(255) not null,
username varchar(255) not null,
primary key (user_id)
)
2025-10-15T09:56:25.804+08:00 DEBUG 23874 --- [eventbridge] [           main] org.hibernate.SQL                        :
create table users (
created_at timestamp(6),
email varchar(255) not null,
id varchar(255) not null,
status varchar(255) check (status in ('ACTIVE','INACTIVE')),
username varchar(255) not null,
primary key (id)
)
Hibernate:
create table users (
created_at timestamp(6),
email varchar(255) not null,
id varchar(255) not null,
status varchar(255) check (status in ('ACTIVE','INACTIVE')),
username varchar(255) not null,
primary key (id)
)
2025-10-15T09:56:25.806+08:00  INFO 23874 --- [eventbridge] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
✅ RabbitMQ ObjectMapper 配置完成 - 多态类型处理已禁用
✅ RabbitMQ Jackson2JsonMessageConverter 配置完成
✅ RabbitTemplate 配置完成
✅ HTTP ObjectMapper 配置完成
=== 🚀 开始注册用户事件处理器 ===
📝 EventProcessorRegistry - 注册处理器: UserCreated
✅ 注册 UserCreated 事件处理器
📝 EventProcessorRegistry - 注册处理器: UserEmailUpdated
✅ 注册 UserEmailUpdated 事件处理器
📝 EventProcessorRegistry - 注册处理器: UserDeactivated
✅ 注册 UserDeactivated 事件处理器
--- 📋 事件处理器注册验证 ---
UserCreated 处理器: ✅ 已注册
UserEmailUpdated 处理器: ✅ 已注册
UserDeactivated 处理器: ✅ 已注册
总注册处理器数量: 3
🎯 所有用户事件处理器注册成功！
=== 🎉 用户事件处理器注册完成 ===
2025-10-15T09:56:26.179+08:00 DEBUG 23874 --- [eventbridge] [           main] .a.r.l.a.MessagingMessageListenerAdapter : Inferred argument type for public void com.eventbridge.query.infrastructure.events.RabbitMQEventsListener.handleUserEvent(java.lang.Object,java.lang.String) is class java.lang.Object
2025-10-15T09:56:26.183+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.a.r.l.SimpleMessageListenerContainer : No global properties bean
2025-10-15T09:56:26.183+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.a.r.l.SimpleMessageListenerContainer : No global properties bean
2025-10-15T09:56:26.197+08:00  INFO 23874 --- [eventbridge] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
2025-10-15T09:56:26.198+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.a.r.l.SimpleMessageListenerContainer : Starting Rabbit listener container.
2025-10-15T09:56:26.199+08:00  INFO 23874 --- [eventbridge] [           main] o.s.a.r.c.CachingConnectionFactory       : Attempting to connect to: [localhost:5672]
2025-10-15T09:56:26.219+08:00  INFO 23874 --- [eventbridge] [           main] o.s.a.r.c.CachingConnectionFactory       : Created new connection: rabbitConnectionFactory#3eb641a8:0/SimpleConnection@c10f7a8 [delegate=amqp://guest@127.0.0.1:5672/, localPort=51440]
2025-10-15T09:56:26.220+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.amqp.rabbit.core.RabbitAdmin         : Initializing declarations
2025-10-15T09:56:26.224+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.a.r.c.CachingConnectionFactory       : Creating cached Rabbit Channel from AMQChannel(amqp://guest@127.0.0.1:5672/,1)
2025-10-15T09:56:26.227+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.amqp.rabbit.core.RabbitTemplate      : Executing callback RabbitAdmin$$Lambda$1478/0x0000007001984070 on RabbitMQ Channel: Cached Rabbit Channel: AMQChannel(amqp://guest@127.0.0.1:5672/,1), conn: Proxy@19439ec4 Shared Rabbit Connection: SimpleConnection@c10f7a8 [delegate=amqp://guest@127.0.0.1:5672/, localPort=51440]
2025-10-15T09:56:26.228+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.amqp.rabbit.core.RabbitAdmin         : declaring Exchange 'domain-events-exchange'
2025-10-15T09:56:26.228+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.amqp.rabbit.core.RabbitAdmin         : declaring Queue 'user-events-queue'
2025-10-15T09:56:26.229+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.amqp.rabbit.core.RabbitAdmin         : declaring Queue 'order-events-queue'
2025-10-15T09:56:26.229+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.amqp.rabbit.core.RabbitAdmin         : Binding destination [user-events-queue (QUEUE)] to exchange [domain-events-exchange] with routing key [user.*]
2025-10-15T09:56:26.230+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.amqp.rabbit.core.RabbitAdmin         : Binding destination [order-events-queue (QUEUE)] to exchange [domain-events-exchange] with routing key [order.*]
2025-10-15T09:56:26.231+08:00 DEBUG 23874 --- [eventbridge] [           main] o.s.amqp.rabbit.core.RabbitAdmin         : Declarations finished
2025-10-15T09:56:26.233+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] o.s.amqp.rabbit.core.RabbitTemplate      : Executing callback RabbitAdmin$$Lambda$1486/0x000000700198a6d0 on RabbitMQ Channel: Cached Rabbit Channel: AMQChannel(amqp://guest@127.0.0.1:5672/,1), conn: Proxy@19439ec4 Shared Rabbit Connection: SimpleConnection@c10f7a8 [delegate=amqp://guest@127.0.0.1:5672/, localPort=51440]
2025-10-15T09:56:26.235+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] o.s.a.r.listener.BlockingQueueConsumer   : Starting consumer Consumer@4e00723b: tags=[[]], channel=null, acknowledgeMode=AUTO local queue size=0
2025-10-15T09:56:26.238+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] o.s.a.r.listener.BlockingQueueConsumer   : Started on queue 'user-events-queue' with tag amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ: Consumer@4e00723b: tags=[[amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ]], channel=Cached Rabbit Channel: AMQChannel(amqp://guest@127.0.0.1:5672/,1), conn: Proxy@19439ec4 Shared Rabbit Connection: SimpleConnection@c10f7a8 [delegate=amqp://guest@127.0.0.1:5672/, localPort=51440], acknowledgeMode=AUTO local queue size=0
2025-10-15T09:56:26.238+08:00 DEBUG 23874 --- [eventbridge] [pool-2-thread-3] o.s.a.r.listener.BlockingQueueConsumer   : ConsumeOK: Consumer@4e00723b: tags=[[amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ]], channel=Cached Rabbit Channel: AMQChannel(amqp://guest@127.0.0.1:5672/,1), conn: Proxy@19439ec4 Shared Rabbit Connection: SimpleConnection@c10f7a8 [delegate=amqp://guest@127.0.0.1:5672/, localPort=51440], acknowledgeMode=AUTO local queue size=0
2025-10-15T09:56:26.242+08:00  INFO 23874 --- [eventbridge] [           main] com.eventbridge.EventBridgeApplication   : Started EventBridgeApplication in 1.623 seconds (process running for 1.893)
🎯 EventProcessorRegistry 初始化完成
当前注册的处理器数量: 3
2025-10-15T10:00:16.960+08:00  INFO 23874 --- [eventbridge] [nio-8080-exec-8] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2025-10-15T10:00:16.961+08:00  INFO 23874 --- [eventbridge] [nio-8080-exec-8] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2025-10-15T10:00:16.963+08:00  INFO 23874 --- [eventbridge] [nio-8080-exec-8] o.s.web.servlet.DispatcherServlet        : Completed initialization in 2 ms
👤 [COMMAND] 开始处理创建用户命令: john5_doe
2025-10-15T10:00:17.016+08:00 DEBUG 23874 --- [eventbridge] [nio-8080-exec-8] org.hibernate.SQL                        :
select
u1_0.id,
u1_0.created_at,
u1_0.email,
u1_0.status,
u1_0.username
from
users u1_0
where
u1_0.id=?
Hibernate:
select
u1_0.id,
u1_0.created_at,
u1_0.email,
u1_0.status,
u1_0.username
from
users u1_0
where
u1_0.id=?
💾 [COMMAND] 用户保存到数据库: abe5be3a-83d6-437e-abed-ce8ccfecf3d4
📦 [COMMAND] 发布用户领域事件，数量: 1
2025-10-15T10:00:17.024+08:00 DEBUG 23874 --- [eventbridge] [nio-8080-exec-8] actionalApplicationListenerMethodAdapter : Registered transaction synchronization for org.springframework.context.PayloadApplicationEvent[source=org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@73194df, started on Wed Oct 15 09:56:24 CST 2025]
2025-10-15T10:00:17.031+08:00 DEBUG 23874 --- [eventbridge] [nio-8080-exec-8] o.s.a.r.c.CachingConnectionFactory       : Creating cached Rabbit Channel from AMQChannel(amqp://guest@127.0.0.1:5672/,2)
2025-10-15T10:00:17.032+08:00 DEBUG 23874 --- [eventbridge] [nio-8080-exec-8] o.s.amqp.rabbit.core.RabbitTemplate      : Executing callback RabbitTemplate$$Lambda$1553/0x0000007001a14b48 on RabbitMQ Channel: Cached Rabbit Channel: AMQChannel(amqp://guest@127.0.0.1:5672/,2), conn: Proxy@19439ec4 Shared Rabbit Connection: SimpleConnection@c10f7a8 [delegate=amqp://guest@127.0.0.1:5672/, localPort=51440]
2025-10-15T10:00:17.036+08:00 DEBUG 23874 --- [eventbridge] [nio-8080-exec-8] o.s.amqp.rabbit.core.RabbitTemplate      : Publishing message [(Body:'[B@71dfbac0(byte[312])' MessageProperties [headers={}, contentType=application/json, contentLength=0, deliveryMode=PERSISTENT, priority=0, deliveryTag=0])] on exchange [domain-events-exchange], routingKey = [user.usercreated]
📤 [COMMAND] 发布到 RabbitMQ: UserCreated - abe5be3a-83d6-437e-abed-ce8ccfecf3d4
🎯 [COMMAND] 创建用户完成，用户ID: abe5be3a-83d6-437e-abed-ce8ccfecf3d4
2025-10-15T10:00:17.040+08:00 DEBUG 23874 --- [eventbridge] [pool-2-thread-4] o.s.a.r.listener.BlockingQueueConsumer   : Storing delivery for consumerTag: 'amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ' with deliveryTag: '1' in Consumer@4e00723b: tags=[[amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ]], channel=Cached Rabbit Channel: AMQChannel(amqp://guest@127.0.0.1:5672/,1), conn: Proxy@19439ec4 Shared Rabbit Connection: SimpleConnection@c10f7a8 [delegate=amqp://guest@127.0.0.1:5672/, localPort=51440], acknowledgeMode=AUTO local queue size=0
2025-10-15T10:00:17.040+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] o.s.a.r.listener.BlockingQueueConsumer   : Received message: (Body:'[B@1524d57e(byte[312])' MessageProperties [headers={}, contentType=application/json, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=domain-events-exchange, receivedRoutingKey=user.usercreated, deliveryTag=1, consumerTag=amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ, consumerQueue=user-events-queue])
2025-10-15T10:00:17.044+08:00 DEBUG 23874 --- [eventbridge] [nio-8080-exec-8] org.hibernate.SQL                        :
insert
into
users
(created_at, email, status, username, id)
values
(?, ?, ?, ?, ?)
Hibernate:
insert
into
users
(created_at, email, status, username, id)
values
(?, ?, ?, ?, ?)
2025-10-15T10:00:17.046+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] .a.r.l.a.MessagingMessageListenerAdapter : Processing [GenericMessage [payload={eventId=9f922e3b-9678-4c7d-a06c-f041e0a5bdcf, userId=abe5be3a-83d6-437e-abed-ce8ccfecf3d4, username=john5_doe, email=john5@example.com, createdAt=2025-10-15T10:00:17.003605, occurredOn=2025-10-15T02:00:17.003664Z, eventType=UserCreated, aggregateId=abe5be3a-83d6-437e-abed-ce8ccfecf3d4}, headers={amqp_receivedDeliveryMode=PERSISTENT, amqp_receivedRoutingKey=user.usercreated, amqp_receivedExchange=domain-events-exchange, amqp_deliveryTag=1, amqp_consumerQueue=user-events-queue, amqp_redelivered=false, id=ca4857bf-71ac-7c34-060e-812ae4d614b4, amqp_consumerTag=amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ, amqp_lastInBatch=false, contentType=application/json, timestamp=1760493617046}]]
📥 [QUERY-RABBITMQ] 接收到 RabbitMQ 消息，路由键: user.usercreated
🔍 [QUERY-RABBITMQ] 消息类型: org.springframework.amqp.core.Message
🔍 [QUERY-RABBITMQ] AMQP 消息详情:
Content-Type: application/json
Body length: 312 bytes
🔍 [QUERY-RABBITMQ] 消息内容: {"eventId":"9f922e3b-9678-4c7d-a06c-f041e0a5bdcf","userId":"abe5be3a-83d6-437e-abed-ce8ccfecf3d4","username":"john5_doe","email":"john5@example.com","createdAt":"2025-10-15T10:00:17.003605","occurredOn":"2025-10-15T02:00:17.003664Z","eventType":"UserCreated","aggregateId":"abe5be3a-83d6-437e-abed-ce8ccfecf3d4"}
🔄 [QUERY-LOCAL] 开始处理 UserCreatedEvent: abe5be3a-83d6-437e-abed-ce8ccfecf3d4
📝 事件详情 - 用户名: john5_doe, 邮箱: john5@example.com
🔍 [QUERY-RABBITMQ] 事件类型: UserCreated
🔄 [QUERY-RABBITMQ] 成功转换事件: UserCreated - abe5be3a-83d6-437e-abed-ce8ccfecf3d4
🚀 EventProcessorRegistry - 执行处理器: UserCreated for abe5be3a-83d6-437e-abed-ce8ccfecf3d4
🔄 [EVENT-REGISTRY] 处理 UserCreatedEvent: abe5be3a-83d6-437e-abed-ce8ccfecf3d4
📝 事件详情 - 用户名: john5_doe, 邮箱: john5@example.com
2025-10-15T10:00:17.162+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] org.hibernate.SQL                        :
select
count(*)
from
user_views uv1_0
where
uv1_0.user_id=?
Hibernate:
select
count(*)
from
user_views uv1_0
where
uv1_0.user_id=?
2025-10-15T10:00:17.162+08:00 DEBUG 23874 --- [eventbridge] [        Async-1] org.hibernate.SQL                        :
select
count(*)
from
user_views uv1_0
where
uv1_0.user_id=?
Hibernate:
select
count(*)
from
user_views uv1_0
where
uv1_0.user_id=?
2025-10-15T10:00:17.166+08:00 DEBUG 23874 --- [eventbridge] [        Async-1] org.hibernate.SQL                        :
select
uv1_0.user_id,
uv1_0.created_at,
uv1_0.email,
uv1_0.last_updated,
uv1_0.status,
uv1_0.username
from
user_views uv1_0
where
uv1_0.user_id=?
Hibernate:
select
uv1_0.user_id,
uv1_0.created_at,
uv1_0.email,
uv1_0.last_updated,
uv1_0.status,
uv1_0.username
from
user_views uv1_0
where
uv1_0.user_id=?
2025-10-15T10:00:17.166+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] org.hibernate.SQL                        :
select
uv1_0.user_id,
uv1_0.created_at,
uv1_0.email,
uv1_0.last_updated,
uv1_0.status,
uv1_0.username
from
user_views uv1_0
where
uv1_0.user_id=?
Hibernate:
select
uv1_0.user_id,
uv1_0.created_at,
uv1_0.email,
uv1_0.last_updated,
uv1_0.status,
uv1_0.username
from
user_views uv1_0
where
uv1_0.user_id=?
✅ [QUERY-LOCAL] 用户视图创建成功: john5_doe (ID: abe5be3a-83d6-437e-abed-ce8ccfecf3d4)
✅ [EVENT-REGISTRY] 用户视图创建成功: john5_doe (ID: abe5be3a-83d6-437e-abed-ce8ccfecf3d4)
✅ EventProcessorRegistry - 处理器执行成功: UserCreated
✅ [QUERY-RABBITMQ] 事件处理成功: UserCreated
2025-10-15T10:00:17.166+08:00 DEBUG 23874 --- [eventbridge] [        Async-1] org.hibernate.SQL                        :
insert
into
user_views
(created_at, email, last_updated, status, username, user_id)
values
(?, ?, ?, ?, ?, ?)
Hibernate:
insert
into
user_views
(created_at, email, last_updated, status, username, user_id)
values
(?, ?, ?, ?, ?, ?)
2025-10-15T10:00:17.166+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] org.hibernate.SQL                        :
insert
into
user_views
(created_at, email, last_updated, status, username, user_id)
values
(?, ?, ?, ?, ?, ?)
Hibernate:
insert
into
user_views
(created_at, email, last_updated, status, username, user_id)
values
(?, ?, ?, ?, ?, ?)
2025-10-15T10:00:17.168+08:00  WARN 23874 --- [eventbridge] [ntContainer#0-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 23505, SQLState: 23505
2025-10-15T10:00:17.168+08:00 ERROR 23874 --- [eventbridge] [ntContainer#0-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : Unique index or primary key violation: "PUBLIC.PRIMARY_KEY_C ON PUBLIC.USER_VIEWS(USER_ID) VALUES ( /* 1 */ 'abe5be3a-83d6-437e-abed-ce8ccfecf3d4' )"; SQL statement:
insert into user_views (created_at,email,last_updated,status,username,user_id) values (?,?,?,?,?,?) [23505-224]
2025-10-15T10:00:17.170+08:00  WARN 23874 --- [eventbridge] [ntContainer#0-1] s.a.r.l.ConditionalRejectingErrorHandler : Execution of Rabbit message listener failed.

org.springframework.amqp.rabbit.support.ListenerExecutionFailedException: Listener method 'public void com.eventbridge.query.infrastructure.events.RabbitMQEventsListener.handleUserEvent(java.lang.Object,java.lang.String)' threw exception
at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:286) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.invokeHandlerAndProcessResult(MessagingMessageListenerAdapter.java:224) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.onMessage(MessagingMessageListenerAdapter.java:149) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.doInvokeListener(AbstractMessageListenerContainer.java:1662) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.actualInvokeListener(AbstractMessageListenerContainer.java:1581) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.invokeListener(AbstractMessageListenerContainer.java:1569) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.doExecuteListener(AbstractMessageListenerContainer.java:1560) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.executeListenerAndHandleException(AbstractMessageListenerContainer.java:1505) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.lambda$executeListener$8(AbstractMessageListenerContainer.java:1483) ~[spring-rabbit-3.1.0.jar:3.1.0]
at io.micrometer.observation.Observation.observe(Observation.java:499) ~[micrometer-observation-1.12.0.jar:1.12.0]
at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.executeListener(AbstractMessageListenerContainer.java:1483) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.doReceiveAndExecute(SimpleMessageListenerContainer.java:994) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.receiveAndExecute(SimpleMessageListenerContainer.java:941) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$AsyncMessageProcessingConsumer.mainLoop(SimpleMessageListenerContainer.java:1325) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$AsyncMessageProcessingConsumer.run(SimpleMessageListenerContainer.java:1227) ~[spring-rabbit-3.1.0.jar:3.1.0]
at java.base/java.lang.Thread.run(Thread.java:840) ~[na:na]
Caused by: org.springframework.dao.DataIntegrityViolationException: could not execute statement [Unique index or primary key violation: "PUBLIC.PRIMARY_KEY_C ON PUBLIC.USER_VIEWS(USER_ID) VALUES ( /* 1 */ 'abe5be3a-83d6-437e-abed-ce8ccfecf3d4' )"; SQL statement:
insert into user_views (created_at,email,last_updated,status,username,user_id) values (?,?,?,?,?,?) [23505-224]] [insert into user_views (created_at,email,last_updated,status,username,user_id) values (?,?,?,?,?,?)]; SQL [insert into user_views (created_at,email,last_updated,status,username,user_id) values (?,?,?,?,?,?)]; constraint ["PUBLIC.PRIMARY_KEY_C ON PUBLIC.USER_VIEWS(USER_ID) VALUES ( /* 1 */ 'abe5be3a-83d6-437e-abed-ce8ccfecf3d4' )"; SQL statement:
insert into user_views (created_at,email,last_updated,status,username,user_id) values (?,?,?,?,?,?) [23505-224]]
at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:290) ~[spring-orm-6.1.1.jar:6.1.1]
at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:241) ~[spring-orm-6.1.1.jar:6.1.1]
at org.springframework.orm.jpa.JpaTransactionManager.doCommit(JpaTransactionManager.java:565) ~[spring-orm-6.1.1.jar:6.1.1]
at org.springframework.transaction.support.AbstractPlatformTransactionManager.processCommit(AbstractPlatformTransactionManager.java:794) ~[spring-tx-6.1.1.jar:6.1.1]
at org.springframework.transaction.support.AbstractPlatformTransactionManager.commit(AbstractPlatformTransactionManager.java:757) ~[spring-tx-6.1.1.jar:6.1.1]
at org.springframework.transaction.interceptor.TransactionAspectSupport.commitTransactionAfterReturning(TransactionAspectSupport.java:669) ~[spring-tx-6.1.1.jar:6.1.1]
at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:419) ~[spring-tx-6.1.1.jar:6.1.1]
at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-6.1.1.jar:6.1.1]
at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.1.1.jar:6.1.1]
at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:765) ~[spring-aop-6.1.1.jar:6.1.1]
at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:717) ~[spring-aop-6.1.1.jar:6.1.1]
at com.eventbridge.query.infrastructure.events.RabbitMQEventsListener$$SpringCGLIB$$0.handleUserEvent(<generated>) ~[classes/:na]
at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
at java.base/java.lang.reflect.Method.invoke(Method.java:569) ~[na:na]
at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:169) ~[spring-messaging-6.1.1.jar:6.1.1]
at org.springframework.amqp.rabbit.listener.adapter.KotlinAwareInvocableHandlerMethod.doInvoke(KotlinAwareInvocableHandlerMethod.java:45) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:119) ~[spring-messaging-6.1.1.jar:6.1.1]
at org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter.invoke(HandlerAdapter.java:75) ~[spring-rabbit-3.1.0.jar:3.1.0]
at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:277) ~[spring-rabbit-3.1.0.jar:3.1.0]
... 15 common frames omitted
Caused by: org.hibernate.exception.ConstraintViolationException: could not execute statement [Unique index or primary key violation: "PUBLIC.PRIMARY_KEY_C ON PUBLIC.USER_VIEWS(USER_ID) VALUES ( /* 1 */ 'abe5be3a-83d6-437e-abed-ce8ccfecf3d4' )"; SQL statement:
insert into user_views (created_at,email,last_updated,status,username,user_id) values (?,?,?,?,?,?) [23505-224]] [insert into user_views (created_at,email,last_updated,status,username,user_id) values (?,?,?,?,?,?)]
at org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:62) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:58) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:108) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:283) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.performNonBatchedMutation(AbstractMutationExecutor.java:107) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.engine.jdbc.mutation.internal.MutationExecutorSingleNonBatched.performNonBatchedOperations(MutationExecutorSingleNonBatched.java:40) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.engine.jdbc.mutation.internal.AbstractMutationExecutor.execute(AbstractMutationExecutor.java:52) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.persister.entity.mutation.InsertCoordinator.doStaticInserts(InsertCoordinator.java:171) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.persister.entity.mutation.InsertCoordinator.coordinateInsert(InsertCoordinator.java:112) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2865) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.action.internal.EntityInsertAction.execute(EntityInsertAction.java:102) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:631) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.engine.spi.ActionQueue.executeActions(ActionQueue.java:498) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.event.internal.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:363) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:39) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1415) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.internal.SessionImpl.managedFlush(SessionImpl.java:496) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.internal.SessionImpl.flushBeforeTransactionCompletion(SessionImpl.java:2325) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.internal.SessionImpl.beforeTransactionCompletion(SessionImpl.java:1988) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl.beforeTransactionCompletion(JdbcCoordinatorImpl.java:439) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl.beforeCompletionCallback(JdbcResourceLocalTransactionCoordinatorImpl.java:169) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl$TransactionDriverControlImpl.commit(JdbcResourceLocalTransactionCoordinatorImpl.java:267) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.hibernate.engine.transaction.internal.TransactionImpl.commit(TransactionImpl.java:101) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
at org.springframework.orm.jpa.JpaTransactionManager.doCommit(JpaTransactionManager.java:561) ~[spring-orm-6.1.1.jar:6.1.1]
... 33 common frames omitted
Caused by: org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Unique index or primary key violation: "PUBLIC.PRIMARY_KEY_C ON PUBLIC.USER_VIEWS(USER_ID) VALUES ( /* 1 */ 'abe5be3a-83d6-437e-abed-ce8ccfecf3d4' )"; SQL statement:
insert into user_views (created_at,email,last_updated,status,username,user_id) values (?,?,?,?,?,?) [23505-224]
at org.h2.message.DbException.getJdbcSQLException(DbException.java:520) ~[h2-2.2.224.jar:2.2.224]
at org.h2.message.DbException.getJdbcSQLException(DbException.java:489) ~[h2-2.2.224.jar:2.2.224]
at org.h2.message.DbException.get(DbException.java:223) ~[h2-2.2.224.jar:2.2.224]
at org.h2.message.DbException.get(DbException.java:199) ~[h2-2.2.224.jar:2.2.224]
at org.h2.index.Index.getDuplicateKeyException(Index.java:527) ~[h2-2.2.224.jar:2.2.224]
at org.h2.mvstore.db.MVSecondaryIndex.checkUnique(MVSecondaryIndex.java:223) ~[h2-2.2.224.jar:2.2.224]
at org.h2.mvstore.db.MVSecondaryIndex.add(MVSecondaryIndex.java:184) ~[h2-2.2.224.jar:2.2.224]
at org.h2.mvstore.db.MVTable.addRow(MVTable.java:519) ~[h2-2.2.224.jar:2.2.224]
at org.h2.command.dml.Insert.insertRows(Insert.java:174) ~[h2-2.2.224.jar:2.2.224]
at org.h2.command.dml.Insert.update(Insert.java:135) ~[h2-2.2.224.jar:2.2.224]
at org.h2.command.dml.DataChangeStatement.update(DataChangeStatement.java:74) ~[h2-2.2.224.jar:2.2.224]
at org.h2.command.CommandContainer.update(CommandContainer.java:169) ~[h2-2.2.224.jar:2.2.224]
at org.h2.command.Command.executeUpdate(Command.java:256) ~[h2-2.2.224.jar:2.2.224]
at org.h2.jdbc.JdbcPreparedStatement.executeUpdateInternal(JdbcPreparedStatement.java:216) ~[h2-2.2.224.jar:2.2.224]
at org.h2.jdbc.JdbcPreparedStatement.executeUpdate(JdbcPreparedStatement.java:174) ~[h2-2.2.224.jar:2.2.224]
at com.zaxxer.hikari.pool.ProxyPreparedStatement.executeUpdate(ProxyPreparedStatement.java:61) ~[HikariCP-5.0.1.jar:na]
at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.executeUpdate(HikariProxyPreparedStatement.java) ~[HikariCP-5.0.1.jar:na]
at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:280) ~[hibernate-core-6.3.1.Final.jar:6.3.1.Final]
... 54 common frames omitted

2025-10-15T10:00:17.172+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] o.s.a.r.listener.BlockingQueueConsumer   : Rejecting messages (requeue=true)
2025-10-15T10:00:17.173+08:00 DEBUG 23874 --- [eventbridge] [pool-2-thread-5] o.s.a.r.listener.BlockingQueueConsumer   : Storing delivery for consumerTag: 'amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ' with deliveryTag: '2' in Consumer@4e00723b: tags=[[amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ]], channel=Cached Rabbit Channel: AMQChannel(amqp://guest@127.0.0.1:5672/,1), conn: Proxy@19439ec4 Shared Rabbit Connection: SimpleConnection@c10f7a8 [delegate=amqp://guest@127.0.0.1:5672/, localPort=51440], acknowledgeMode=AUTO local queue size=0
2025-10-15T10:00:17.173+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] o.s.a.r.listener.BlockingQueueConsumer   : Received message: (Body:'[B@6acfc781(byte[312])' MessageProperties [headers={}, contentType=application/json, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=true, receivedExchange=domain-events-exchange, receivedRoutingKey=user.usercreated, deliveryTag=2, consumerTag=amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ, consumerQueue=user-events-queue])
2025-10-15T10:00:17.173+08:00 DEBUG 23874 --- [eventbridge] [ntContainer#0-1] .a.r.l.a.MessagingMessageListenerAdapter : Processing [GenericMessage [payload={eventId=9f922e3b-9678-4c7d-a06c-f041e0a5bdcf, userId=abe5be3a-83d6-437e-abed-ce8ccfecf3d4, username=john5_doe, email=john5@example.com, createdAt=2025-10-15T10:00:17.003605, occurredOn=2025-10-15T02:00:17.003664Z, eventType=UserCreated, aggregateId=abe5be3a-83d6-437e-abed-ce8ccfecf3d4}, headers={amqp_receivedDeliveryMode=PERSISTENT, amqp_receivedRoutingKey=user.usercreated, amqp_receivedExchange=domain-events-exchange, amqp_deliveryTag=2, amqp_consumerQueue=user-events-queue, amqp_redelivered=true, id=49c943b7-f942-ae78-8e45-c3c7765e5c7b, amqp_consumerTag=amq.ctag-4FIXGFLzrsxd9vdAk2YPqQ, amqp_lastInBatch=false, contentType=application/json, timestamp=1760493617173}]]
📥 [QUERY-RABBITMQ] 接收到 RabbitMQ 消息，路由键: user.usercreated
🔍 [QUERY-RABBITMQ] 消息类型: org.springframework.amqp.core.Message
🔍 [QUERY-RABBITMQ] AMQP 消息详情:
Content-Type: application/json
Body length: 312 bytes
🔍 [QUERY-RABBITMQ] 消息内容: {"eventId":"9f922e3b-9678-4c7d-a06c-f041e0a5bdcf","userId":"abe5be3a-83d6-437e-abed-ce8ccfecf3d4","username":"john5_doe","email":"john5@example.com","createdAt":"2025-10-15T10:00:17.003605","occurredOn":"2025-10-15T02:00:17.003664Z","eventType":"UserCreated","aggregateId":"abe5be3a-83d6-437e-abed-ce8ccfecf3d4"}
🔍 [QUERY-RABBITMQ] 事件类型: UserCreated
🔄 [QUERY-RABBITMQ] 成功转换事件: UserCreated - abe5be3a-83d6-437e-abed-ce8ccfecf3d4
⏭️ EventProcessorRegistry - 跳过已处理的事件: 9f922e3b-9678-4c7d-a06c-f041e0a5bdcf:UserCreated
✅ [QUERY-RABBITMQ] 事件处理成功: UserCreated
