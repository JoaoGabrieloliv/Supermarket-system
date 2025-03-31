# 🛒 Supermarket System

[![Java](https://img.shields.io/badge/Java-17-blue?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green?logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/)
[![MIT License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> **Sistema completo de gerenciamento de supermercado**, com autenticação JWT, API RESTful e persistência em banco de dados PostgreSQL. Desenvolvido em Java com Spring Boot.

---

## 📌 Sobre o projeto

O **Supermarket System** é uma aplicação backend desenvolvida em **Java 17** com **Spring Boot**, voltada para o gerenciamento de supermercados. O sistema permite o **cadastro de usuários**, **login seguro com autenticação JWT**, e manipulação de produtos. Ideal para pequenos e médios mercados que buscam automatizar processos internos.

---

## 🚀 Tecnologias utilizadas

- ✔️ Java 17  
- ✔️ Spring Boot 3  
- ✔️ Spring Data JPA  
- ✔️ Spring Security (com autenticação JWT)  
- ✔️ PostgreSQL  
- ✔️ Maven  
- ✔️ Lombok  
- ✔️ JPA / Hibernate

---

## 🔐 Funcionalidades

- ✅ Cadastro de usuários com e-mail único
- ✅ Autenticação com senha criptografada usando BCrypt
- ✅ Login com token JWT
- ✅ CRUD completo de produtos (em breve)
- ✅ Integração com banco PostgreSQL
- ✅ Tratamento de erros e validações

---

## 📁 Estrutura do projeto

```bash
src/
├── controller       # Camada de entrada (API)
├── model            # Entidades JPA
├── repository       # Interface com o banco de dados
├── service          # Lógica de negócio
└── config           # Configurações de segurança e CORS
