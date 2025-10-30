# 🔗 URL Shortener Application

A lightweight and efficient **URL Shortener** built with **Spring Boot**, featuring caching for high performance and a simple web interface powered by **Thymeleaf**.

---

## 🚀 Features

- Shorten long URLs into compact, shareable codes
- Redirect to the original URL via a short code
- RESTful API endpoints for programmatic access
- Caching layer for faster lookups and reduced database load
- Web interface for easy manual usage

---

## 🧰 Tech Stack

- **Spring Boot** — Core backend framework
- **PostgreSQL** — Persistent database
- **Caffeine Cache** — In-memory caching for performance
- **Thymeleaf** — Server-side templating engine for the web UI

---

## 📡 API Endpoints

### 🔹 Register a New URL
**POST** `/api/register`  
**Request:**
```json
{
  "url": "https://your-long-url.com"
}
```
**Response:**
```json
{
  "code": "abc123"
}
```


### 🔹 Retrieve Original URL by Code
**GET** `/api/{code}`  
**Response:**
```json
{
  "url": "https://your-long-url.com"
}
```


**GET** `/{code}`  
Response:
```
HTTP 302 → Redirects to the original URL
```

## 🌐 Web Interface

The application also provides a simple web interface (via Thymeleaf) where users can:

- Submit URLs to shorten
- Access shortened links directly



## 🐳 Docker Setup

You can easily run the entire stack (application + PostgreSQL) using Docker.



## 🧩 Build and Run with Docker Compose

### Build the application image:

```
docker-compose build
```

### Start all services:

```
docker-compose up
```

### The app will be available at:**
 👉 http://localhost:8080



## 📜 License

This project is licensed under the MIT License
