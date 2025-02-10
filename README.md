# 📌 CRUD com Java Spring Boot, PostgreSQL, Docker e RabbitMQ

Este projeto é um CRUD simples desenvolvido com **Spring Boot** e integrado ao **PostgreSQL** rodando em um container Docker. Além disso, utiliza **RabbitMQ** para comunicação assíncrona, permitindo a implementação de eventos ao cadastrar usuários. Ele segue boas práticas de desenvolvimento e inclui testes unitários para garantir a qualidade do código. O projeto também utiliza **Swagger** para documentação e visualização das APIs.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x** (Spring Web, Spring Data JPA, Spring Boot Starter Test, Spring AMQP)
- **PostgreSQL** como banco de dados relacional
- **Docker e Docker Compose** para containerização do banco de dados e RabbitMQ
- **RabbitMQ** para mensageria
- **Swagger** para documentação da API
- **JUnit & Mockito** para testes unitários
- **Postman** para testar as requisições da API

## 📚 Estrutura do Projeto

```plaintext
src/
├── main/
│   ├── java/com/exemplo/crud/
│   │   ├── controller/  # Controladores da API (Endpoints)
│   │   ├── service/     # Regras de negócio
│   │   ├── repository/  # Acesso ao banco de dados (JPA Repositories)
│   │   ├── model/       # Modelos/Entidades JPA
│   │   ├── dto/         # Objetos de transferência de dados
│   │   ├── exception/   # Tratamento de erros
│   │   ├── messaging/   # Comunicação assíncrona com RabbitMQ
│   ├── resources/
│       ├── application.properties  # Configurações da aplicação
├── test/  # Testes unitários e de integração
```

## 🐙 Configuração do Banco e RabbitMQ com Docker

Para rodar o PostgreSQL e o RabbitMQ no Docker, utilize o arquivo `docker-compose.yml`:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:latest
    container_name: meu_postgres
    restart: always
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
      POSTGRES_DB: crud
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  rabbitmq:
    image: rabbitmq:3-management
    container_name: meu_rabbitmq
    restart: always
    environment:
      RABBITMQ_DEFAULT_USER: admin
      RABBITMQ_DEFAULT_PASS: admin
    ports:
      - "5672:5672"  # Porta para comunicação com a aplicação
      - "15672:15672" # Painel de controle web

volumes:
  postgres_data:
```

### 📌 Rodando os Serviços

```sh
docker-compose up -d
```

Para verificar se os containers estão rodando:

```sh
docker ps
```

Para parar os serviços:

```sh
docker-compose down
```

## 🔧 Configuração do Spring Boot

No arquivo `application.properties`:

```properties
# Configuração do PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/crud
spring.datasource.username=admin
spring.datasource.password=admin
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configuração do RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin
```

## 💽 Testando a API no Postman

### Criar um novo usuário (POST)

```http
POST http://localhost:8080/usuarios
Content-Type: application/json

{
    "nome": "João Silva",
    "email": "joao@email.com"
}
```

### Buscar todos os usuários (GET)

```http
GET http://localhost:8080/usuarios
```

### Buscar um usuário por ID (GET)

```http
GET http://localhost:8080/usuarios/{id}
```

### Atualizar um usuário (PUT)

```http
PUT http://localhost:8080/usuarios/{id}
Content-Type: application/json

{
    "nome": "João Silva Atualizado",
    "email": "joao@email.com"
}
```

### Deletar um usuário (DELETE)

```http
DELETE http://localhost:8080/usuarios/{id}
```

## 📨 Comunicação Assíncrona com RabbitMQ

O sistema envia uma mensagem ao RabbitMQ sempre que um usuário é cadastrado. Essa mensagem pode ser consumida posteriormente para notificações ou outros serviços.

### Exemplo de Mensagem Enviada

```
Novo usuário cadastrado: João Silva
```

## 🧪 Testes Unitários

Os testes estão localizados no diretório `src/test/java/com/exemplo/crud/`. Para rodá-los, use:

```sh
mvn test
```

## 📝 Boas Práticas Seguidas

- **Camadas separadas (Controller, Service, Repository, Messaging)** para organização do código.
- **Uso de DTOs** para evitar exposição direta das entidades.
- **Tratamento de erros personalizado** para melhor experiência de API.
- **Testes unitários** com JUnit e Mockito.
- **Uso de RabbitMQ** para processamento assíncrono.
- **Configuração via `application.properties`** para facilitar deploy em diferentes ambientes.
- **Aplicação dos princípios SOLID, DRY e Design Patterns** para um código mais modular e reutilizável.

---

