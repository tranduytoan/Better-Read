# Better-Read - Ứng dụng đọc sách trực tuyến

Better-Read là một nền tảng đọc sách trực tuyến hoàn chỉnh với giao diện Angular và backend Spring Boot.

## Tổng quan dự án

### 📚 Tính năng chính
- **Quản lý sách**: Tìm kiếm, duyệt và xem chi tiết sách
- **Giỏ hàng**: Thêm sách vào giỏ và thanh toán
- **Tiến độ đọc**: Theo dõi và cập nhật tiến độ đọc sách
- **Đánh giá**: Viết và xem đánh giá sách
- **Gợi ý**: Hệ thống đề xuất sách cá nhân hóa
- **Xác thực**: Đăng ký, đăng nhập với JWT

### 🏗️ Kiến trúc hệ thống
```
Better-Read/
├── frontend_dbms/          # Angular Frontend
├── backend-foreveread/     # Spring Boot Backend
└── db_schema.sql          # Database schema
```

## Công nghệ sử dụng

### Frontend
- Angular 17.3.1
- TypeScript
- TailwindCSS
- Angular Material

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Security + JWT
- Multiple Databases: MySQL, MongoDB, Neo4j
- Elasticsearch (tìm kiếm)
- Redis (cache)
- Apache Kafka (messaging)

## Cài đặt và chạy

### Yêu cầu hệ thống
- Node.js 14+ (cho Frontend)
- Java 17+ (cho Backend)
- MySQL 8.0+
- MongoDB 4.4+
- Redis 6.0+
- Elasticsearch 7.x+
- Neo4j 4.x+

### 1. Cài đặt Backend
```bash
cd backend-foreveread
# Cấu hình file .env với database credentials
./mvnw spring-boot:run
```
Backend chạy trên: http://localhost:8080

### 2. Cài đặt Frontend
```bash
cd frontend_dbms
npm install
ng serve
```
Frontend chạy trên: http://localhost:4200

### 3. Cấu hình Elasticsearch và Logstash (Tùy chọn)

#### Setup Elasticsearch
- Download: [Elasticsearch](https://www.elastic.co/fr/downloads/elasticsearch)
- Cài đặt theo hướng dẫn: [Setup Video](https://www.youtube.com/watch?v=kYXx0sq74Tc)

#### Setup Logstash
- Download: [Logstash](https://www.elastic.co/fr/downloads/logstash)  
- Tạo file `config/product_config.conf` với nội dung cấu hình kết nối MySQL và Elasticsearch
- Chạy: `logstash -f ./config/product_config.conf`

## Cấu trúc Database

- **MySQL**: Dữ liệu chính (users, books, orders)
- **MongoDB**: Log và metadata
- **Neo4j**: Quan hệ và gợi ý
- **Elasticsearch**: Index tìm kiếm
- **Redis**: Cache và session

## API Documentation

Backend cung cấp REST API với các endpoint chính:
- `/api/v1/auth/*` - Xác thực
- `/api/v1/books/*` - Quản lý sách
- `/api/v1/cart/*` - Giỏ hàng
- `/api/v1/users/*` - Người dùng
- `/api/v1/reviews/*` - Đánh giá

## Đóng góp

1. Fork dự án
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## License

Dự án này được phát triển cho mục đích học tập.