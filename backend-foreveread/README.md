# Better-Read Backend

Backend API cho ứng dụng đọc sách trực tuyến Better-Read, được xây dựng bằng Spring Boot.

## Công nghệ sử dụng

- **Java 17** - Ngôn ngữ lập trình chính
- **Spring Boot 3.2.3** - Framework backend
- **Spring Security** - Xác thực và phân quyền
- **Spring Data JPA** - Tương tác với cơ sở dữ liệu
- **MySQL** - Cơ sở dữ liệu chính
- **MongoDB** - Cơ sở dữ liệu NoSQL
- **Neo4j** - Cơ sở dữ liệu đồ thị
- **Elasticsearch** - Công cụ tìm kiếm
- **Redis** - Cache và session storage
- **Apache Kafka** - Message broker
- **JWT** - JSON Web Token cho xác thực

## Tính năng chính

- Quản lý người dùng và xác thực
- Quản lý sách và danh mục
- Hệ thống giỏ hàng và đơn hàng
- Theo dõi tiến độ đọc
- Hệ thống đánh giá và nhận xét
- Tìm kiếm sách với Elasticsearch
- API rate limiting

## Yêu cầu hệ thống

- Java 17+
- MySQL 8.0+
- MongoDB 4.4+
- Redis 6.0+
- Elasticsearch 7.x+
- Neo4j 4.x+
- Apache Kafka 2.8+

## Cài đặt và chạy

### 1. Cấu hình cơ sở dữ liệu

Tạo database MySQL:
```sql
CREATE DATABASE better_read;
```

### 2. Cấu hình biến môi trường

Tạo file `.env` trong thư mục root:
```env
MYSQL_PASSWORD=your_mysql_password
AURA_URI=your_neo4j_uri
AURA_USER=your_neo4j_username
AURA_PASSWORD=your_neo4j_password
```

### 3. Chạy ứng dụng

```bash
./mvnw spring-boot:run
```

Hoặc trên Windows:
```cmd
mvnw.cmd spring-boot:run
```

### 4. Build JAR file

```bash
./mvnw clean package
```

## API Endpoints

- **Authentication**: `/api/v1/auth/*`
- **Users**: `/api/v1/users/*`
- **Books**: `/api/v1/books/*`
- **Cart**: `/api/v1/cart/*`
- **Orders**: `/api/v1/orders/*`
- **Reviews**: `/api/v1/reviews/*`
- **Reading Progress**: `/api/v1/reading-progress/*`

## Cấu hình

Các cấu hình chính trong `application.yml`:
- Database connections
- JWT settings
- Cache configuration
- Elasticsearch settings
- Kafka configuration

Server chạy trên port `8080` mặc định.
