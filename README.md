# 🛡️ Fraud Rule Checker – Full Stack Banking Fraud Detection System

**Java 17 • Spring Boot • Drools Rule Engine • HTML • CSS • JavaScript • Bootstrap • H2 Database • MIT License**

---

# 📌 Overview

Fraud Rule Checker is a full-stack banking fraud detection system designed to identify suspicious financial transactions using the **Drools Business Rule Engine**.

The application analyzes customer and transaction data stored in an H2 database and evaluates transactions against predefined fraud rules. Each triggered rule contributes to a fraud score, allowing the system to classify transactions as:

* ✅ SAFE
* ⚠️ REVIEW
* 🚨 FRAUD

The system provides explainable fraud detection by returning the exact rules that were triggered during analysis.

In addition to powerful backend fraud analysis, the application includes an intuitive web-based dashboard that enables users to monitor transactions, analyze fraud risks, and visualize fraud detection results in real time.

---

# ✨ Features

✅ Rule-Based Fraud Detection using Drools

✅ Real-Time Fraud Risk Scoring

✅ Customer Profile Management

✅ Transaction Monitoring

✅ Fraud Explanation & Reason Tracking

✅ Interactive Fraud Analysis Dashboard

✅ Responsive Web Interface

✅ H2 In-Memory Database Support

✅ RESTful APIs

✅ Spring Boot Architecture

✅ Exception Handling & Validation

✅ Easily Extensible Rule Engine

---

# 🧠 Fraud Detection Rules

The system currently evaluates transactions using the following rules:

| Rule                 | Condition                           | Risk Points |
| -------------------- | ----------------------------------- | ----------- |
| High Amount          | Amount > ₹50,000                    | +30         |
| New Payee            | First transaction to payee          | +20         |
| Odd Hour Transaction | Between 1 AM – 4 AM                 | +15         |
| Unusual Location     | Different from customer's home city | +25         |
| Spending Baseline    | Amount > 3× average spending        | +20         |

### Risk Classification

| Score Range | Status |
| ----------- | ------ |
| 0 – 30      | SAFE   |
| 31 – 60     | REVIEW |
| 61+         | FRAUD  |

---

# 🖥️ User Interface

The application includes a responsive frontend built using **HTML5, CSS3, JavaScript, and Bootstrap 5**.

The UI communicates with Spring Boot REST APIs and provides a user-friendly experience for fraud monitoring and transaction analysis.

### 🏠 Home Dashboard

The landing page provides:

* Project overview
* Fraud detection insights
* Quick navigation options
* System summary

### 🚨 Fraud Analysis Dashboard

Users can:

* Analyze transactions
* View fraud scores
* Check risk classifications
* Review triggered fraud rules
* Understand fraud explanations

### 📊 Transaction Monitoring

Provides:

* Transaction history
* Customer transaction details
* Fraud indicators
* Risk status monitoring

### 📱 Responsive Design

Optimized for:

* Desktop Devices
* Tablets
* Mobile Devices

using Bootstrap's responsive framework.

---

# 🧰 Tech Stack

| Layer             | Technology                           |
| ----------------- | ------------------------------------ |
| Frontend          | HTML5, CSS3, JavaScript, Bootstrap 5 |
| Backend           | Java 17                              |
| Framework         | Spring Boot 3.3                      |
| Rule Engine       | Drools 7.74                          |
| Database          | H2 Database                          |
| ORM               | Spring Data JPA                      |
| Build Tool        | Maven                                |
| Validation        | Jakarta Validation                   |
| API Documentation | Swagger / OpenAPI                    |
| Testing           | JUnit 5                              |
| API Style         | REST                                 |

---

# 🔧 Prerequisites

✅ Java 17+

✅ Maven 3.8+

✅ Git

✅ IntelliJ IDEA / Eclipse / VS Code

---

# 🛠️ Setup Instructions

## 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/fraud-rule-checker.git

cd fraud-rule-checker
```

---

## 2️⃣ Build Project

```bash
mvn clean install
```

---

## 3️⃣ Run Application

```bash
mvn spring-boot:run
```

or

```bash
java -jar target/fraud-checker-1.0.0.jar
```

---

# 🚀 Running the Application

Application URL:

```text
http://localhost:8080
```

Open the application in your browser to access the Fraud Detection Dashboard.

---

# 🗄️ H2 Database Console

H2 Console:

```text
http://localhost:8080/h2-console
```

Example Configuration:

```text
JDBC URL : jdbc:h2:mem:frauddb
Username : sa
Password :
```

---

# 🌐 API Endpoints

## 👤 Customer APIs

### Get All Customers

```http
GET /api/customers
```

### Get Customer By ID

```http
GET /api/customers/{id}
```

---

## 💳 Transaction APIs

### Get All Transactions

```http
GET /api/transactions
```

### Get Transaction By ID

```http
GET /api/transactions/{id}
```

---

## 🚨 Fraud Analysis API

### Analyze Transaction

```http
GET /api/fraud/analyze/{transactionId}
```

### Sample Response

```json
{
  "transactionId": 101,
  "customerName": "John Doe",
  "score": 65,
  "status": "FRAUD",
  "reasons": [
    "Amount exceeds Rs.50,000 threshold (+30)",
    "Transaction made to a new payee (+20)",
    "Transaction location differs from customer's home city (+25)"
  ],
  "summary": "High fraud risk transaction detected."
}
```

---

# 🧭 Project Structure

```text
fraud-rule-checker/
│
├── src/
│   ├── main/
│   │
│   ├── java/com/fraudchecker/
│   │   ├── controller/
│   │   │   ├── CustomerController.java
│   │   │   ├── TransactionController.java
│   │   │   └── FraudController.java
│   │   │
│   │   ├── service/
│   │   │   ├── CustomerService.java
│   │   │   ├── TransactionService.java
│   │   │   └── FraudAnalysisService.java
│   │   │
│   │   ├── repository/
│   │   │   ├── CustomerRepository.java
│   │   │   └── TransactionRepository.java
│   │   │
│   │   ├── model/
│   │   │   ├── Customer.java
│   │   │   ├── Transaction.java
│   │   │   ├── TransactionFact.java
│   │   │   └── FraudAnalysisResponse.java
│   │   │
│   │   ├── rules/
│   │   │   └── FraudRuleConfig.java
│   │   │
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── CustomerNotFoundException.java
│   │   │   └── TransactionNotFoundException.java
│   │   │
│   │   └── FraudCheckerApplication.java
│   │
│   └── resources/
│       ├── static/
│       │   ├── css/
│       │   │   └── styles.css
│       │   ├── js/
│       │   │   └── app.js
│       │   ├── index.html
│       │   └── fraud-analysis.html
│       │
│       ├── rules/
│       │   └── fraud-rules.drl
│       │
│       ├── application.properties
│       └── data.sql
│
├── pom.xml
└── README.md
```

---

# 🔍 How Fraud Analysis Works

### Step 1

Transaction is fetched from the database.

### Step 2

Customer profile is loaded.

### Step 3

A TransactionFact object is created.

### Step 4

Drools Rule Engine evaluates all fraud rules.

### Step 5

Risk score is calculated.

### Step 6

Transaction is classified as:

* SAFE
* REVIEW
* FRAUD

### Step 7

Triggered rules are returned as explanations and displayed on the dashboard.

---

# 📸 Screenshots

## 🏠 Home Dashboard

Displays project overview and fraud monitoring summary.

```text
screenshots/home-dashboard.png
```

## 🚨 Fraud Analysis Dashboard

Displays fraud score, status, and triggered rules.

```text
screenshots/fraud-analysis-dashboard.png
```

## 📊 Transaction Monitoring

Displays transaction history and fraud indicators.

```text
screenshots/transaction-monitoring.png
```

---

# 🚀 Future Enhancements

🤖 Machine Learning Based Fraud Detection

📊 Advanced Fraud Analytics Dashboard

📧 Email Alert Notifications

📱 SMS Fraud Alerts

🔐 JWT Authentication & Authorization

☁️ AWS / Azure Cloud Deployment

📈 Real-Time Streaming Fraud Detection

🌍 Multi-Bank Integration

📋 Audit Logs & Reporting

---

# 🎯 Business Impact

* Reduces fraudulent financial transactions
* Improves customer trust
* Enables explainable fraud detection
* Supports scalable banking operations
* Provides fast rule updates without code changes
* Improves fraud investigation efficiency
* Enhances banking security and compliance

---

# 📝 License

This project is licensed under the MIT License.

---

# 👨‍💻 Authors

### Team Members

* BODDU SRAVANTHI
* JAKKAMSETTI VENKATA SIRI
* SADHU VENKATA SAI NIKITH
* BOOBATHY R
* MYLA MANIKANTA

---

⭐ If you found this project useful, consider giving it a star on GitHub!
