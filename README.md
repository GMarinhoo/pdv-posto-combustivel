# ⛽ API - PDV Posto de Combustível (Backend)

Este é o módulo de **Backend** do projeto de PDV. É uma API RESTful construída em Java 17 e Spring Boot 3 que gerencia toda a lógica de negócio do sistema.

![Status: Concluído](https://img.shields.io/badge/status-concluído-brightgreen)

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3** (Spring Web, Spring Data JPA, Spring Security)
* **Banco de Dados:** PostgreSQL
* **Autenticação:** JWT (via Spring Security) e BCrypt para senhas
* **Build:** Apache Maven

## API Endpoints

O sistema expõe 7 entidades principais via API REST:

* `.../api/v1/auth` (Para Login e Registro)
* `.../api/v1/acessos` (CRUD de Logins/Perfis)
* `.../api/v1/pessoas` (CRUD de Pessoas)
* `.../api/v1/produtos` (CRUD de Produtos/Combustíveis)
* `.../api/v1/estoques` (CRUD de Estoque)
* `.../api/v1/precos` (CRUD de Preços)
* `.../api/v1/custos` (CRUD de Custos/Regras de Negócio)
* `.../api/v1/contatos` (CRUD de Contatos)
* `.../api/v1/vendas` (Endpoint para registrar vendas)

## 🚀 Como Rodar o Backend

### 1. Pré-requisitos
* Java 17 (JDK)
* PostgreSQL (um servidor rodando)
* Maven

### 2. Configurar o Banco de Dados
Crie um banco de dados vazio no seu PostgreSQL (ex: `pdv_posto`).

Ajuste o arquivo `src/main/resources/application.properties` com suas credenciais:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pdv_posto
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres
spring.jpa.hibernate.ddl-auto=create