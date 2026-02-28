# Credit Processing Microservice System

## Описание
Система из двух микросервисов для обработки заявок на кредиты с обменом данными через Kafka и RabbitMQ.

## Архитектура
- **credit-application-service** (порт 8080) - API для оформления заявок
- **credit-decision-service** (порт 8081) - обработка заявок и принятие решений
- **PostgreSQL** - хранение заявок
- **Kafka** - отправка заявок из первого сервиса во второй
- **RabbitMQ** - отправка решений из второго сервиса в первый

## Технологии
- Java 17
- Spring Boot 3.5.11
- Spring Data JPA
- Spring Kafka
- Spring RabbitMQ
- PostgreSQL
- Flyway
- Lombok
- Docker / Docker Compose

## Функциональность
- Подача заявки на кредит (сумма, срок, доход, нагрузка, рейтинг)
- Сохранение заявки в БД со статусом "PENDING"
- Асинхронная обработка через Kafka
- Принятие решения (платёж не более 50% от дохода)
- Отправка результата через RabbitMQ
- Обновление статуса заявки
- Получение статуса по ID

## Структура проекта

- credit-processing-microservice-system — **корневой модуль**
- credit-application-service — **первый сервис (API)**
- credit-decision-service — **второй сервис (обработка)**
- docker-compose.yml — **инфраструктура**
- requests.http — **тестовые запросы**