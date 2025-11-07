

---

# 💳 Payment Service (eCommerce Backend)

The **Payment Service** is a core module of the **eCommerce Backend System**, responsible for **processing payments**, **updating payment statuses**, and **handling refunds** associated with customer orders.

---

## 🚀 Tech Stack

* **Java 21**
* **Spring Boot 3.x**
* **Spring Data JPA**
* **Jakarta Validation**
* **MySQL**
* **Lombok**
* **ModelMapper**

---

## 📦 Overview

The **Payment Service** handles:

* Storing payment details
* Making new payments for orders
* Refunding customers after order cancellations
* Updating payment statuses (e.g., PENDING → PAID → REFUNDED)

---

## ⚙️ Setup Instructions

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/payment-service.git
   cd payment-service
   ```

2. **Configure Database**

   Add the following in your `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Run the Application**

   ```bash
   mvn spring-boot:run
   ```

4. **Base URL**

   ```
   http://localhost:8080/api/payment
   ```

---

## 🧩 API Documentation

### 1️⃣ Save Payment

**POST** `/api/payment/save`

> Stores payment details when an order is initially created.

#### Request Body

```json
{
  "amountPaid": 0.0,
  "totalAmount": 2500.0,
  "orderId": 101
}
```

#### Response (200 OK)

```json
{
  "paymentId": 501,
  "orderId": 101,
  "amountPaid": 0.0,
  "totalAmount": 2500.0,
  "paymentStatus": "PENDING",
  "createdAt": "2025-11-07T15:30:22.414"
}
```

---

### 2️⃣ Make Payment

**PATCH** `/api/payment/make-payment`

> Processes a payment for a given order and updates the payment status.

#### Request Body

```json
{
  "amountPaid": 2500.0,
  "totalAmount": 2500.0,
  "orderId": 101
}
```

#### Response (200 OK)

```json
{
  "paymentId": 501,
  "orderId": 101,
  "amountPaid": 2500.0,
  "totalAmount": 2500.0,
  "paymentStatus": "PAID",
  "paymentDate": "2025-11-07T15:35:42.915"
}
```

#### Error Example

```json
{
  "message": "Order not found with ID 101",
  "httpStatus": "NOT_FOUND",
  "statusCode": 404,
  "timeStamp": "2025-11-07T15:36:12.327"
}
```

---

### 3️⃣ Refund Payment

**PUT** `/api/payment/refund/{orderId}`

> Initiates a refund for an existing order and updates payment status to `REFUNDED`.

#### Example

```
PUT /api/payment/refund/101
```

#### Response (200 OK)

```json
{
  "paymentId": 501,
  "orderId": 101,
  "amountPaid": 2500.0,
  "totalAmount": 2500.0,
  "paymentStatus": "REFUNDED",
  "refundDate": "2025-11-07T15:42:18.663"
}
```

#### Error Example

```json
{
  "message": "Payment record not found for order ID 101",
  "httpStatus": "NOT_FOUND",
  "statusCode": 404,
  "timeStamp": "2025-11-07T15:43:02.110"
}
```

---

## ⚠️ Exception Handling

All exceptions return a consistent **JSON error format** using your global `ErrorDto` class.

### 🧾 Error Response Structure

```json
{
  "message": "Detailed error message",
  "httpStatus": "BAD_REQUEST",
  "statusCode": 400,
  "timeStamp": "2025-11-07T15:40:15.331"
}
```

### Common Exceptions

| Exception                         | HTTP Status | Description                      |
| --------------------------------- | ----------- | -------------------------------- |
| `ResourceNotFoundException`       | 404         | Order or Payment not found       |
| `MethodArgumentNotValidException` | 400         | Invalid request payload          |
| `ConstraintViolationException`    | 400         | Invalid path or parameter        |
| `PaymentFailedException`          | 400         | Payment gateway or process error |

---

## 📘 DTOs Overview

### `PaymentRequestDto`

```json
{
  "amountPaid": 2500.0,
  "totalAmount": 2500.0,
  "orderId": 101
}
```

### `PaymentResponseDto`

```json
{
  "paymentId": 501,
  "orderId": 101,
  "amountPaid": 2500.0,
  "totalAmount": 2500.0,
  "paymentStatus": "PAID",
  "createdAt": "2025-11-07T15:30:22.414",
  "updatedAt": "2025-11-07T15:35:42.915"
}
```

---

## 🔗 Service Workflow

| Action           | Description                                                     |
| ---------------- | --------------------------------------------------------------- |
| **Save Payment** | Initializes a payment with status `PENDING`.                    |
| **Make Payment** | Marks the payment as `PAID` once funds are received.            |
| **Refund**       | Updates status to `REFUNDED` and adjusts balances if necessary. |

---





