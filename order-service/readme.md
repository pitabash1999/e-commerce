
---

# 🧾 Order Service (eCommerce Backend)

The **Order Service** is a core part of the **eCommerce Backend System**, responsible for managing the **lifecycle of customer orders** — from creation to cancellation.
It interacts with the **Inventory Service** to validate stock and update product quantities.

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

This microservice provides REST APIs for:

* Creating new orders
* Fetching orders by ID
* Cancelling orders and restocking products
* Validating stock availability via the Inventory Service

Each order contains product items, amounts, address, payment status, and shipping details.

---

## ⚙️ Setup Instructions

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/order-service.git
   cd order-service
   ```

2. **Configure Database**

   Add your database credentials in `application.properties`:

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
   http://localhost:8080/api/orders
   ```

---

## 🧩 API Documentation

### 1️⃣ Create Order

**POST** `/api/orders/save`

#### Request Body

```json
{
  "orderStatus": "PLACED",
  "items": [
    {
      "productId": 1,
      "orderedQuantity": 2
    },
    {
      "productId": 2,
      "orderedQuantity": 1
    }
  ],
  "shippingAmount": 100.0,
  "totalAmount": 2500.0,
  "amountPaid": 2500.0,
  "address": {
    "street": "123 Main Street",
    "city": "Bhubaneswar",
    "state": "Odisha",
    "pincode": "751024"
  }
}
```

#### Response (201 Created)

```json
{
  "id": 101,
  "orderStatus": "PLACED",
  "paymentStatus": "PENDING",
  "shippingAmount": 100.0,
  "totalAmount": 2500.0,
  "amountPaid": 2500.0,
  "items": [
    {
      "productId": 1,
      "orderedQuantity": 2
    },
    {
      "productId": 2,
      "orderedQuantity": 1
    }
  ],
  "address": {
    "street": "123 Main Street",
    "city": "Bhubaneswar",
    "state": "Odisha",
    "pincode": "751024"
  },
  "createdAt": "2025-11-07T15:25:45.781"
}
```

#### Possible Errors

```json
{
  "message": "Insufficient stock for product ID 1",
  "httpStatus": "BAD_REQUEST",
  "statusCode": 400,
  "timeStamp": "2025-11-07T15:25:45.781"
}
```

---

### 2️⃣ Get Order by ID

**GET** `/api/orders/{id}`

#### Example

```
GET /api/orders/101
```

#### Response

```json
{
  "id": 101,
  "orderStatus": "PLACED",
  "paymentStatus": "PENDING",
  "shippingAmount": 100.0,
  "totalAmount": 2500.0,
  "amountPaid": 2500.0,
  "items": [
    {
      "productId": 1,
      "orderedQuantity": 2
    },
    {
      "productId": 2,
      "orderedQuantity": 1
    }
  ],
  "address": {
    "street": "123 Main Street",
    "city": "Bhubaneswar",
    "state": "Odisha",
    "pincode": "751024"
  },
  "createdAt": "2025-11-07T15:25:45.781"
}
```

#### Error (Not Found)

```json
{
  "message": "Order not found with ID 999",
  "httpStatus": "NOT_FOUND",
  "statusCode": 404,
  "timeStamp": "2025-11-07T15:25:45.781"
}
```

---

### 3️⃣ Cancel Order

**PUT** `/api/orders/cancel/{id}`

Cancels an order and automatically updates product stock in the **Inventory Service**.

#### Example

```
PUT /api/orders/cancel/101
```

#### Response

```json
{
  "id": 101,
  "orderStatus": "CANCELLED",
  "paymentStatus": "REFUNDED",
  "totalAmount": 2500.0,
  "amountPaid": 2500.0,
  "shippingAmount": 100.0,
  "updatedAt": "2025-11-07T16:05:21.118"
}
```

#### Error Example

```json
{
  "message": "Order not found with ID 101",
  "httpStatus": "NOT_FOUND",
  "statusCode": 404,
  "timeStamp": "2025-11-07T16:05:21.118"
}
```

---

## ⚠️ Exception Handling

All exceptions return a unified response using the `ErrorDto` structure.

### 🧾 Error Response Format

```json
{
  "message": "Detailed error message",
  "httpStatus": "BAD_REQUEST",
  "statusCode": 400,
  "timeStamp": "2025-11-07T15:30:10.457"
}
```

### Common Exceptions

| Exception                         | HTTP Status | Description                                |
| --------------------------------- | ----------- | ------------------------------------------ |
| `InsufficientStockException`      | 400         | Product stock not sufficient for the order |
| `ResourceNotFoundException`       | 404         | Order or product not found                 |
| `MethodArgumentNotValidException` | 400         | Validation error in request body           |
| `ConstraintViolationException`    | 400         | Invalid path or query parameter            |

---

## 📘 DTO Overview

### `OrderRequestDto`

```json
{
  "orderStatus": "PLACED",
  "items": [
    {
      "productId": 1,
      "orderedQuantity": 2
    }
  ],
  "shippingAmount": 100.0,
  "totalAmount": 2500.0,
  "amountPaid": 2500.0,
  "address": {
    "street": "string",
    "city": "string",
    "state": "string",
    "pincode": "string"
  }
}
```

### `OrderResponseDto`

```json
{
  "id": 1,
  "orderStatus": "PLACED",
  "paymentStatus": "PENDING",
  "shippingAmount": 0.0,
  "totalAmount": 0.0,
  "amountPaid": 0.0,
  "items": [],
  "address": {},
  "createdAt": "2025-11-07T15:25:45.781"
}
```

### `ItemDto`

```json
{
  "productId": 1,
  "orderedQuantity": 5
}
```

### `PaymentRequestDto`

```json
{
  "amountPaid": 2500.0,
  "totalAmount": 2500.0,
  "orderId": 1
}
```

---

## 🔗 Service Integration

| Service               | Purpose                                                        |
| --------------------- | -------------------------------------------------------------- |
| **Inventory Service** | Validates product stock and updates quantity (reduce/increase) |
| **Order Repository**  | Persists order information                                     |
| **ModelMapper**       | Converts between Entity and DTO classes                        |

---





