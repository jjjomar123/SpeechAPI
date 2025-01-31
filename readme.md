### 📌 **API Documentation - Speech API**

## **🗣️ Overview**

The **Speech API** provides endpoints to manage speech records, allowing users to create, retrieve,
update, delete, and search speeches.

📌 **Base URL**:

```
http://localhost:8080/api/v1/speeches
```

📌 **Swagger UI**:

```
http://localhost:8080/swagger-ui.html
```

📌 **OpenAPI Docs**:

```
http://localhost:8080/v3/api-docs
```

---

## **🛠️ Endpoints**

### **1️⃣ Create a New Speech**

- **`POST /api/v1/speeches`**
- **Description**: Adds a new speech to the system.
- **Request Body**:
  ```json
  {
    "author": "John Doe",
    "subject": "Law and Order",
    "speechText": "This is a test speech.",
    "speechDate": "2024-01-31"
  }
  ```
- **Response**:
  ```json
  {
    "author": "John Doe",
    "subject": "Law and Order",
    "speechText": "This is a test speech.",
    "speechDate": "2024-01-31"
  }
  ```
- **Response Codes**:
    - `201 Created`: Speech created successfully.
    - `400 Bad Request`: Invalid input.

---

### **2️⃣ Get All Speeches (Paginated)**

- **`GET /api/v1/speeches`**
- **Description**: Retrieves a paginated list of all speeches.
- **Query Parameters**:
    - `page` (default: `0`) → **Pagination page index**
    - `size` (default: `50`) → **Number of items per page**
- **Example Request**:
  ```
  GET /api/v1/speeches?page=0&size=10
  ```
- **Response**:
  ```json
  {
    "content": [
      {
        "author": "Jane Doe",
        "subject": "Legal Ethics"
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "size": 10
  }
  ```
- **Response Codes**:
    - `200 OK`: Speeches retrieved successfully.
    - `404 Not Found`: No speeches found.

---

### **3️⃣ Get Speech by ID**

- **`GET /api/v1/speeches/{id}`**
- **Description**: Retrieves a specific speech by its ID.
- **Example Request**:
  ```
  GET /api/v1/speeches/1
  ```
- **Response**:
  ```json
  {
    "author": "Jane Doe",
    "subject": "Legal Ethics"
  }
  ```
- **Response Codes**:
    - `200 OK`: Speech retrieved successfully.
    - `404 Not Found`: Speech not found.

---

### **4️⃣ Update a Speech**

- **`PUT /api/v1/speeches/{id}`**
- **Description**: Updates an existing speech by its ID.
- **Request Body**:
  ```json
  {
    "author": "Alice Updated",
    "subject": "Updated Corporate Law",
    "speechText": "Updated speech text.",
    "speechDate": "2024-01-31"
  }
  ```
- **Response**:
  ```json
  {
    "author": "Alice Updated",
    "subject": "Updated Corporate Law",
    "speechText": "Updated speech text.",
    "speechDate": "2024-01-31"
  }
  ```
- **Response Codes**:
    - `200 OK`: Speech updated successfully.
    - `400 Bad Request`: Invalid input.
    - `404 Not Found`: Speech not found.

---

### **5️⃣ Delete a Speech**

- **`DELETE /api/v1/speeches/{id}`**
- **Description**: Deletes a specific speech by its ID.
- **Example Request**:
  ```
  DELETE /api/v1/speeches/1
  ```
- **Response**:
  ```json
  "Speech deleted"
  ```
- **Response Codes**:
    - `200 OK`: Speech deleted successfully.
    - `404 Not Found`: Speech not found.

---

### **6️⃣ Search Speeches**

- **`GET /api/v1/speeches/search`**
- **Description**: Searches speeches based on filters.
- **Query Parameters**:
    - `author` (optional)
    - `subject` (optional)
    - `dateFrom` (optional, format: `yyyy-MM-dd`)
    - `dateTo` (optional, format: `yyyy-MM-dd`)
    - `speechText` (optional)
    - `page` (default: `0`)
    - `size` (default: `50`)
- **Example Request**:
  ```
  GET /api/v1/speeches/search?author=John&page=0&size=10
  ```
- **Response**:
  ```json
  {
    "content": [
      {
        "author": "John Doe",
        "subject": "Law and Order"
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "size": 10
  }
  ```
- **Response Codes**:
    - `200 OK`: Speeches retrieved successfully.
    - `404 Not Found`: No speeches found.

---

## **🛠️ Tools & Technologies**

- **Spring Boot 3**
- **Java 21**
- **Spring Data JPA**
- **PostgreSQL**
- **Liquibase**
- **Swagger (OpenAPI 3)**
- **Docker**
- **JUnit & MockMvc (Testing)**

---

## **🔗 API Documentation**

The API uses **Swagger** for interactive documentation.

📌 **Swagger UI**:

```
http://localhost:8080/swagger-ui.html
```

📌 **OpenAPI Docs (JSON format)**:

```
http://localhost:8080/v3/api-docs
```

📌 **Docker command**:

```
Starting the docker..
docker compose up --build

```

## **🚀 Author**

👤 **Jomar Rhey Ababa**

---