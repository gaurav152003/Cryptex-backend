# Cryptex Backend - Cryptocurrency Trading Platform API

Cryptex Backend is a secure and scalable backend application for a **Cryptocurrency Trading Platform** built with **Spring Boot**. It provides authentication, wallet management, crypto trading, wallet-to-wallet transfers, chatbot support, cryptocurrency news updates, and payment integration.

---

## 🚀 Project Overview

Cryptex Backend handles the server-side operations of a cryptocurrency trading platform. It allows users to securely register/login, manage wallets, transfer funds, buy or sell cryptocurrencies, track transaction history, read the latest crypto news, and interact with an AI chatbot for assistance.

The project is built following **clean architecture principles** with proper separation of concerns for scalability, maintainability, and security.

---

## 🏗️ Architecture

The application follows a **3-Layer Architecture**:

### 1. Controller Layer
Handles incoming HTTP requests and sends responses.

### 2. Service Layer
Contains all business logic and processing.

### 3. Repository Layer
Handles database operations using **Spring Data JPA / Hibernate**.

### Architectural Highlights

- JWT-based authentication and authorization  
- Role-based access control (**USER / ADMIN**)  
- DTO-based request and response structure  
- Exception handling with custom error responses  
- Service-layer business logic implementation  
- Third-party API integrations  

---

## 🛠️ Technology Stack

- **Framework:** Spring Boot  
- **Language:** Java 17  
- **Database:** MySQL  
- **ORM:** Spring Data JPA / Hibernate  
- **Security:** Spring Security + JWT  
- **Validation:** Jakarta Bean Validation  
- **Build Tool:** Maven  
- **API Testing:** Postman  

---

## ✨ Features

### Authentication & Authorization
- User Registration  
- User Login  
- JWT Token Authentication  
- Role-based Authorization  

### Wallet Management
- Create Wallet  
- View Wallet Balance  
- Update Wallet Information  
- Deposit Funds  
- Withdraw Funds  
- Wallet-to-Wallet Transfer  

### Crypto Trading
- Buy Cryptocurrency  
- Sell Cryptocurrency  
- View Available Coins  
- Track Live Coin Prices  
- View Coin Details & Market Stats  

### Transaction Management
- View Transaction History  
- Track Transaction Status  

### Portfolio Management
- View Owned Assets  
- Track Total Portfolio Value  

### Crypto News
- Get Latest Cryptocurrency News  
- Read Trending Crypto Articles  

### AI Chatbot
- Answer crypto-related queries  
- Help users navigate platform features  

### Payment Integration
- Razorpay Payment Gateway  
- Stripe Payment Gateway  

### Admin Features
- Manage Users  
- Monitor Transactions  
- Manage Coin Listings  
- Handle suspicious activities  

---

## 📂 Project Structure

    Crypto-Backend/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com.gaurav/
    │   │   │       ├── config/
    │   │   │       ├── controller/
    │   │   │       ├── domain/
    │   │   │       ├── exception/
    │   │   │       ├── model/
    │   │   │       ├── repository/
    │   │   │       ├── request/
    │   │   │       ├── response/
    │   │   │       ├── service/
    │   │   │       ├── utils/
    │   │   │       └── CryptoTradingPlatform.java
    │   │   │
    │   │   └── resources/
    │   │       ├── static/
    │   │       ├── templates/
    │   │       └── application.properties
    │
    ├── .gitignore
    └── pom.xml

---

## ⚙️ Getting Started

### Prerequisites

Make sure you have installed:

- Java 17+  
- Maven 3.6+  
- MySQL 8+  

---

## 🔧 Installation & Setup

### Clone the Repository

    git clone https://github.com/yourusername/cryptex-backend.git
    cd cryptex-backend

### Configure Database

Create a MySQL database:

    CREATE DATABASE cryptex_db;

Update your `application.properties` file:

    spring.datasource.url=jdbc:mysql://localhost:3306/cryptex_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

### Configure JWT Secret

    jwt.secret=your_secret_key
    jwt.expiration=86400000

### Run the Application

    mvn spring-boot:run

The backend will start on:

    http://localhost:5454

---

## 📬 API Testing

Use **Postman** to test APIs.

Example modules:

- Authentication APIs  
- Wallet APIs  
- Crypto APIs  
- Transfer APIs  
- News APIs  
- Chatbot APIs  
- Payment APIs  
- Admin APIs  

---

## 👨‍💻 Developer

**Built by Gaurav Yadav**
