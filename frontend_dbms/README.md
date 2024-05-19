# Better-Read Frontend

Giao diện người dùng cho ứng dụng đọc sách trực tuyến Better-Read, được xây dựng bằng Angular.

## Công nghệ sử dụng

- **Angular 17.3.1** - Framework frontend
- **TypeScript** - Ngôn ngữ lập trình
- **TailwindCSS** - CSS framework
- **Angular Material** - UI components
- **RxJS** - Reactive programming
- **JWT** - Xác thực người dùng
- **Toastr** - Thông báo người dùng

## Tính năng chính

- Tìm kiếm và duyệt sách
- Giỏ hàng và thanh toán
- Theo dõi tiến độ đọc
- Đánh giá và nhận xét sách
- Quản lý tài khoản người dùng
- Gợi ý sách cá nhân hóa

## Cài đặt và chạy ứng dụng

### Yêu cầu hệ thống
- Node.js (phiên bản 14 trở lên)
- Angular CLI
- npm hoặc yarn

### Khởi chạy server phát triển

```bash
ng serve
```

Truy cập `http://localhost:4200/` để xem ứng dụng. Ứng dụng sẽ tự động tải lại khi bạn thay đổi mã nguồn.

### Tạo thành phần mới

```bash
ng generate component ten-component
```

Bạn cũng có thể tạo các thành phần khác như directive, pipe, service, class, guard, interface, enum, module.

### Build ứng dụng

```bash
ng build
```

Các file build sẽ được lưu trong thư mục `dist/`.

### Chạy kiểm thử

```bash
ng test
```

Chạy unit test thông qua [Karma](https://karma-runner.github.io).

## Cấu trúc dự án

```
src/
├── app/
│   ├── core/          # Các service và guard chính
│   ├── features/      # Các tính năng chính (sách, giỏ hàng, đánh giá...)
│   ├── pages/         # Các trang chính
│   └── shared/        # Các component và model dùng chung
└── assets/            # Hình ảnh và tài nguyên tĩnh
```

## Hỗ trợ

Để biết thêm thông tin về Angular CLI, sử dụng `ng help` hoặc tham khảo [Angular CLI Documentation](https://angular.io/cli).
