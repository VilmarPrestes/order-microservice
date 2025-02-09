📌 CRUD Simples com Java Spring Boot, PostgreSQL e Docker

Este projeto é um CRUD simples desenvolvido com Spring Boot e integrado ao PostgreSQL rodando em um container Docker. Ele segue boas práticas de desenvolvimento e inclui testes unitários para garantir a qualidade do código.

🚀 Tecnologias Utilizadas

Java 17

Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Boot Starter Test)

PostgreSQL como banco de dados relacional

Docker e Docker Compose para containerização do banco de dados

JUnit & Mockito para testes unitários

Postman para testar as requisições da API

📂 Estrutura do Projeto

src/
├── main/
│   ├── java/com/exemplo/crud/
│   │   ├── controller/  # Controladores da API (Endpoints)
│   │   ├── service/     # Regras de negócio
│   │   ├── repository/  # Acesso ao banco de dados (JPA Repositories)
│   │   ├── model/       # Modelos/Entidades JPA
│   │   ├── dto/         # Objetos de transferência de dados
│   │   ├── exception/   # Tratamento de erros
│   ├── resources/
│       ├── application.properties  # Configurações da aplicação
├── test/  # Testes unitários e de integração

🐳 Configuração do Banco com Docker

Para rodar o PostgreSQL no Docker, utilize o arquivo docker-compose.yml:

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

volumes:
  postgres_data:

📌 Rodando o Banco de Dados

docker-compose up -d

Para verificar se o container está rodando:

docker ps

Se precisar parar o banco:

docker-compose down

🔧 Configuração do Spring Boot

No arquivo application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/crud
spring.datasource.username=admin
spring.datasource.password=admin
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

📡 Testando a API no Postman

Criar um novo usuário (POST)

POST http://localhost:8080/usuarios
Content-Type: application/json

{
    "nome": "João Silva",
    "email": "joao@email.com"
}

Buscar todos os usuários (GET)

GET http://localhost:8080/usuarios

Buscar um usuário por ID (GET)

GET http://localhost:8080/usuarios/{id}

Atualizar um usuário (PUT)

PUT http://localhost:8080/usuarios/{id}
Content-Type: application/json

{
    "nome": "João Silva Atualizado",
    "email": "joao@email.com"
}

Deletar um usuário (DELETE)

DELETE http://localhost:8080/usuarios/{id}

🧪 Testes Unitários

Os testes estão localizados no diretório src/test/java/com/exemplo/crud/. Para rodá-los, use:

mvn test

📜 Boas Práticas Seguidas

Camadas separadas (Controller, Service, Repository) para organização do código.

Uso de DTOs para evitar exposição direta das entidades.

Tratamento de erros personalizado para melhor experiência de API.

Testes unitários com JUnit e Mockito.

Configuração via application.properties para facilitar deploy em diferentes ambientes.




docker-compose up -d

docker ps

docker-compose down

docker exec -it meu_postgres psql -U admin -d meu_banco

SELECT * FROM users;


