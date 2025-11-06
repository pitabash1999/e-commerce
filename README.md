

#  E-Commerce Microservices System

A modular e-commerce backend built with **Java 21 + Spring Boot 3** using a **Microservices Architecture**.  
Each service exposes REST APIs and communicates through **Eureka Service Discovery** and **Spring Cloud Gateway**.

---

## 📦 Microservices Overview

| Service | Port | Description |
|----------|------|-------------|
| **Eureka Server** | 1918 | Service registry for discovery |
| **API Gateway** | 1922 | Routes external requests to internal services |
| **Product Service** | 1919 | Handles product CRUD & stock management |
| **Order Service** | 1920 | Manages order creation and cancellation |
| **Payment Service** | 1921 | Manages payments and refunds |

---

## ⚙️ Local Setup

### ✅ Prerequisites
- JDK 21  
- Maven 3.8+  
- MySQL 8.0+  
- Ports 1918-1922 available  

### 🗄️ Database Setup
Create three databases in MySQL:
```sql
CREATE DATABASE inventory_db;
CREATE DATABASE order_db;
CREATE DATABASE payment_db;
````

Update each service’s `application.yml` with its database credentials.

### ▶️ Steps to Run Locally

1. **Start MySQL** and verify the databases exist.
2. **Run Eureka Server**

   ```bash
   mvn -pl eureka-server spring-boot:run
   ```

   Visit [http://localhost:1918](http://localhost:1918) to confirm it’s running.
3. **Run Each Microservice**

   ```bash
   mvn -pl product-service spring-boot:run
   mvn -pl order-service spring-boot:run
   mvn -pl payment-service spring-boot:run
   ```
4. **Run API Gateway**

   ```bash
   mvn -pl api-gateway spring-boot:run
   ```
5. **Verify Eureka Dashboard**

   * All services should appear as *UP*.
6. **Test APIs via Gateway**

   ```
   http://localhost:1919/api/products/...
   http://localhost:1919/api/orders/...
   http://localhost:1919/api/payments/...
   ```

---

## 🧭 API Reference

### 🧩 Product Service — `/api/products`

| Method   | Endpoint                         | Description                        |
| -------- | -------------------------------- | ---------------------------------- |
| `POST`   | `/save`                          | Add a new product                  |
| `GET`    | `/{id}`                          | Get product by ID                  |
| `POST`   | `/payload`                       | Get payload for multiple products  |
| `GET`    | `/`                              | List all products                  |
| `PUT`    | `/updateCancelledProduct`        | Restore stock for cancelled orders |
| `GET`    | `/category/{category}`           | List products by category          |
| `GET`    | `/search?name=`                  | Search products by name            |
| `GET`    | `/low-stock?threshold=`          | Get low-stock products             |
| `PUT`    | `/{id}`                          | Update full product record         |
| `PATCH`  | `/{id}/quantity?quantity=`       | Update quantity only               |
| `PATCH`  | `/{id}/price?price=`             | Update price only                  |
| `DELETE` | `/{id}`                          | Delete a product                   |
| `GET`    | `/{id}/availability?quantity=`   | Check stock availability           |
| `PATCH`  | `/{id}/reduce-stock?quantity=`   | Reduce stock                       |
| `PATCH`  | `/{id}/increase-stock?quantity=` | Increase stock                     |

---

### 📦 Order Service — `/api/orders`

| Method | Endpoint       | Description              |
| ------ | -------------- | ------------------------ |
| `POST` | `/save`        | Create a new order       |
| `GET`  | `/{id}`        | Retrieve order details   |
| `PUT`  | `/cancel/{id}` | Cancel an existing order |

---

### 💳 Payment Service — `/api/payments`

| Method  | Endpoint            | Description                   |
| ------- | ------------------- | ----------------------------- |
| `POST`  | `/save`             | Save a payment record         |
| `PATCH` | `/make-payment`     | Execute a payment transaction |
| `PUT`   | `/refund/{orderId}` | Refund a payment by order ID  |

---

## 🧱 Gateway Routing Summary

| Route              | Target Service  |
| ------------------ | --------------- |
| `/api/products/**` | PRODUCT-SERVICE |
| `/api/orders/**`   | ORDER-SERVICE   |
| `/api/payments/**` | PAYMENT-SERVICE |

Requests made through the gateway are automatically routed using Eureka discovery.

---

## 🧩 Example Workflow

1. **Add Product** → `POST /api/products/save`
2. **Create Order** → `POST /api/orders/save`
3. **Make Payment** → `PATCH /api/payments/make-payment`
4. **Cancel Order** → `PUT /api/orders/cancel/{id}` → stock restored via product-service

---

## 🧰 Technologies Used

* Java 21
* Spring Boot 3
* Spring Data JPA (MySQL)
* Spring Cloud Gateway
* Spring Cloud Netflix Eureka
* Spring Validation 
* Maven

---


## 🏁 Notes

* Each service maintains its own database schema.
* All inter-service communication uses Feign Clients with Eureka discovery.
* Validation errors return proper HTTP 400 responses with descriptive messages.




