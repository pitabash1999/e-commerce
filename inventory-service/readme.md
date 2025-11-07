
---

# 🏷️ Inventory Service (Product Management API)

This service is part of the **eCommerce Backend System** and manages all **product-related operations** such as adding, updating, retrieving, searching, and managing stock quantities.

---

## 🚀 Tech Stack

* **Java 21**
* **Spring Boot 3.x**
* **Spring Data JPA**
* **Jakarta Validation**
* **MySQL**
* **Lombok**

---

## 📦 Overview

The **Inventory Service** exposes REST APIs for:

* Managing products (Create, Read, Update, Delete)
* Managing stock levels (Increase, Decrease, Check availability)
* Searching and filtering by category or name
* Handling low-stock alerts and product availability

---

## ⚙️ Setup Instructions

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/inventory-service.git
   cd inventory-service
   ```

2. **Configure the database**

   Add the following configuration in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

4. **Base API URL**

   ```
   http://localhost:8080/api/products
   ```

---

## 🧩 API Documentation

### 1️⃣ Create Product

**POST** `/api/products/save`

#### Request Body

```json
{
  "name": "Samsung Galaxy S24",
  "category": "Electronics",
  "quantity": 100,
  "price": 79999.99
}
```

#### Response (201 Created)

```json
{
  "id": 1,
  "name": "Samsung Galaxy S24",
  "category": "Electronics",
  "quantity": 100,
  "price": 79999.99
}
```

---

### 2️⃣ Get Product by ID

**GET** `/api/products/{id}`

#### Example

```
GET /api/products/1
```

#### Response

```json
{
  "id": 1,
  "name": "Samsung Galaxy S24",
  "category": "Electronics",
  "quantity": 100,
  "price": 79999.99
}
```

---

### 3️⃣ Get All Products

**GET** `/api/products`

#### Response

```json
[
  {
    "id": 1,
    "name": "Samsung Galaxy S24",
    "category": "Electronics",
    "quantity": 100,
    "price": 79999.99
  },
  {
    "id": 2,
    "name": "Dell Laptop",
    "category": "Electronics",
    "quantity": 50,
    "price": 54999.99
  }
]
```

---

### 4️⃣ Update Product

**PUT** `/api/products/{id}`

#### Request

```json
{
  "name": "Samsung Galaxy S24 Ultra",
  "category": "Electronics",
  "quantity": 120,
  "price": 89999.99
}
```

#### Response

```json
{
  "id": 1,
  "name": "Samsung Galaxy S24 Ultra",
  "category": "Electronics",
  "quantity": 120,
  "price": 89999.99
}
```

---

### 5️⃣ Delete Product

**DELETE** `/api/products/{id}`

#### Example

```
DELETE /api/products/1
```

#### Response

```
204 No Content
```

---

### 6️⃣ Search Products by Name

**GET** `/api/products/search?name=samsung`

#### Response

```json
[
  {
    "id": 1,
    "name": "Samsung Galaxy S24",
    "category": "Electronics",
    "quantity": 100,
    "price": 79999.99
  }
]
```

---

### 7️⃣ Filter by Category

**GET** `/api/products/category/{category}`

#### Example

```
GET /api/products/category/Electronics
```

#### Response

```json
[
  {
    "id": 1,
    "name": "Samsung Galaxy S24",
    "category": "Electronics",
    "quantity": 100,
    "price": 79999.99
  }
]
```

---

### 8️⃣ Check Product Availability

**GET** `/api/products/{id}/availability?quantity=10`

#### Response

```json
true
```

---

### 9️⃣ Reduce Stock

**PATCH** `/api/products/{id}/reduce-stock?quantity=5`

#### Response

```json
{
  "id": 1,
  "name": "Samsung Galaxy S24",
  "category": "Electronics",
  "quantity": 95,
  "price": 79999.99
}
```

#### Error Example (`InsufficientStockException`)

```json
{
  "message": "Not enough stock available for product ID 1",
  "httpStatus": "BAD_REQUEST",
  "statusCode": 400,
  "timeStamp": "2025-11-07T15:30:10.457"
}
```

---

### 🔟 Increase Stock

**PATCH** `/api/products/{id}/increase-stock?quantity=10`

#### Response

```json
{
  "id": 1,
  "name": "Samsung Galaxy S24",
  "category": "Electronics",
  "quantity": 105,
  "price": 79999.99
}
```

---

### 11️⃣ Get Low Stock Products

**GET** `/api/products/low-stock?threshold=5`

#### Response

```json
[
  {
    "id": 3,
    "name": "USB Cable",
    "category": "Accessories",
    "quantity": 2,
    "price": 199.00
  }
]
```

---

### 12️⃣ Update Product Quantity or Price (Partial Update)

#### Update Quantity

**PATCH** `/api/products/{id}/quantity?quantity=50`

#### Update Price

**PATCH** `/api/products/{id}/price?price=999.99`

#### Response

```json
{
  "id": 2,
  "name": "Dell Laptop",
  "category": "Electronics",
  "quantity": 50,
  "price": 999.99
}
```

---

### 13️⃣ Update Cancelled Product Quantities

**PUT** `/api/products/updateCancelledProduct`

#### Request Body

```json
[
  {
    "productId": 1,
    "orderedQuantity": 2
  },
  {
    "productId": 2,
    "orderedQuantity": 1
  }
]
```

#### Response

```json
[
  {
    "productId": 1,
    "quantityUpdated": true
  },
  {
    "productId": 2,
    "quantityUpdated": true
  }
]
```

---

## ⚠️ Exception Handling

All exceptions return a unified JSON structure defined by the `ErrorDto` class:

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

| Exception                         | HTTP Status | Description                            |
| --------------------------------- | ----------- | -------------------------------------- |
| `ProductNotFoundException`        | 404         | Product not found                      |
| `InsufficientStockException`      | 400         | Not enough stock available             |
| `MethodArgumentNotValidException` | 400         | Invalid request fields                 |
| `ConstraintViolationException`    | 400         | Validation failed for query parameters |

---

## 📘 DTOs Overview

### `ProductRequestDto`

```json
{
  "name": "string",
  "category": "string",
  "quantity": 0,
  "price": 0.0
}
```

### `ItemDto`

```json
{
  "productId": 1,
  "orderedQuantity": 5
}
```

### `ProductResponseDto`

```json
{
  "id": 1,
  "name": "string",
  "category": "string",
  "quantity": 0,
  "price": 0.0
}
```




