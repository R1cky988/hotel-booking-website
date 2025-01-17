# TravelBooking

## Tổng quan
Dự án này là một hệ thống đặt phòng khách sạn trực tuyến được thiết kế nhằm đơn giản hóa quá trình đặt phòng ở các khách sạn. Mục đích chính là tiết kiệm thời gian cho người dung, giúp xem và đặt trước những phòng còn trống khi du lịch.

## Tính năng
- Đăng ký và xác nhận người dung
- Xem thông tin chi tiết về khách sạn và phòng còn trống
- Đánh giá khách sạn
- Đặt phòng khách sạn

## Người đóng góp
- **Nguyễn Kiến Quốc** - Chức năng hiển thị thông tin khách sạn, hiển thị đánh giá, tìm kiếm
- **Hà Ngọc Huy** - Chức năng ặt phòng, ánh giá khách sạn

## Thiết kế
- **Frontend**: Thymeleaf và Bootstrap để tạo giao diện người dùng thân thiện
- **Backend**: Java Spring boot 
- **Cơ sở dữ liệu**: MySQL

## Các điểm cuối API
### Người dung
- `POST /users/register` - Đăng ký người dùng mới
- `POST /users/login` - Đăng nhập người dung
- `GET /users/logout` - Đăng xuất người dung
### Khách sạn
- `GET /hotel` - Lấy tất cả khách sạn
- `GET /hotel/{hotelId}` - Lấy thông tin khách sạn có ID là hotelId
### Phòng
- `GET /room` - Lấy tất cả phòng của khách sạn
- `GET /room/available` - Kiểm tra phòng trống
### Đặt phòng
- `POST /booking/room/{roomId}` - Đặt phòng có ID là roomId
- `GET /booking/{userId}` - Hiển thị lịch sử đặt phòng của người dung có ID là userId
- `DELETE /booking/{formId}` - Hủy phòng đã đặt	
### Đánh giá
- `POST /feedback` - Tạo đánh giá mới
### Thống kê đánh giá
- `GET /summary/hotel/{hotelId}` - Hiển thị tất cả đánh giá của khách sạn có ID là hotelId
## Cài đặt
1. Clone repository
    ```bash
    https://github.com/R1cky988/hotel-booking-website.git
    ```
2. Backend
- Cài đặt XAMPP
- Mở XAMPP mục Apache và MySQL (MySQL ở cổng 3306)
- Trong XAMPP, mục MySQL nhấn admin để vào phpMyAdmin
- tạo database
- Trong Intelliji IDEA, mở dự án ..\booking-website\Backend
- Chạy dự án
3. Frontend
- Template của frontend đã để ở trong thư mục ..\booking-website\Backend\booking\src\main\resources\templates
- Những file Js và CSS được để trong thư mục ..\booking-website\Backend\booking\src\main\resources\static
4. Truy cập ứng dung
- Backend và frontend sẽ chạy trên `http://localhost:8088`




