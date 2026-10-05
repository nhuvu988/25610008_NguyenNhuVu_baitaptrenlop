# Buổi 5 — Xây dựng bố cục cơ bản với Jetpack Compose

**Nguyễn Như Vũ — 25610008**

Bài thực hành theo [Android Basics with Compose, Unit 1, Pathway 3](https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-3?hl=vi).

| Module | Nội dung thực hành |
|---|---|
| `birthday` | Thiệp Happy Birthday: Text, Column, Box, ảnh nền Crop với alpha 0.5. `BirthdayTextPreview` giữ phiên bản chỉ có chữ. |
| `article` | Compose Article: ảnh đầu trang, tiêu đề 24sp, đoạn văn căn đều và padding 16dp. |
| `taskmanager` | Task Completed: ảnh và thông báo căn giữa, tiêu đề đậm, lời khen 16sp. |
| `quadrant` | Compose Quadrant: bốn phần bằng nhau dùng Row/Column và weight, bốn màu theo đề. |
| `businesscard` | Danh thiếp cá nhân: logo Android, tên, chức danh và ba hàng biểu tượng liên hệ. |

## Chạy trong Android Studio

1. Chọn **File → Open**, mở chính thư mục `buoi5` chứa `settings.gradle.kts`.
2. Chờ Gradle Sync hoàn tất. Dự án dùng Gradle 9.6.0, Android Gradle Plugin 9.4.1 và Gradle daemon JDK 25; cần Android SDK 37. Máy chạy ứng dụng cần Android 7.0/API 24 trở lên.
3. Chọn module muốn chạy (`birthday`, `article`, `taskmanager`, `quadrant` hoặc `businesscard`) trong thanh cấu hình Run.
4. Chọn máy ảo trong Device Manager hoặc điện thoại đã bật USB debugging, rồi nhấn **▶ Run**.
5. Để xem Preview, mở `MainActivity.kt` của module, chọn **Split/Design**, rồi Build & Refresh.

Các ứng dụng Android chạy bằng Activity, không dùng nút chạy hàm `main()` của bài Kotlin console.

Build cả năm APK trong Terminal tại thư mục `buoi5`:

```powershell
.\gradlew.bat assembleDebug
```

APK nằm tại `<module>/build/outputs/apk/debug/<module>-debug.apk`.

## Phác thảo danh thiếp

```text
+----------------------------------+
|                                  |
|          [Logo Android]          |
|          Nguyễn Như Vũ           |
| Sinh viên lập trình di động · ID |
|                                  |
| [Phone]  Số điện thoại            |
| [Share]  github.com/nhuvu988       |
| [Email]  Địa chỉ email            |
+----------------------------------+
```

Khối thông tin cá nhân nằm giữa phần không gian phía trên; khối liên hệ nằm phía dưới. Sửa dữ liệu trong `businesscard/src/main/res/values/strings.xml`. Số điện thoại đang để “Chưa cập nhật số điện thoại”. Đây là bài bố cục tĩnh: các hàng liên hệ chưa mở ứng dụng khác; Task Manager chỉ là màn hình thông báo hoàn thành, chưa có lưu trữ nhiệm vụ.

## Kiểm tra trực quan khi chạy

- Birthday: ảnh phủ nền, chữ dễ đọc, xem cả Preview chỉ có chữ.
- Article: ảnh đầy chiều rộng, đọc được toàn bộ bài khi cuộn.
- Task Manager: ảnh, tiêu đề và lời khen ở giữa màn hình.
- Quadrant: bốn ô bằng nhau, đúng màu và nội dung.
- Business Card: tên đúng, logo và ba hàng liên hệ hiển thị đầy đủ.
- Thử xoay màn hình và tăng cỡ chữ hệ thống; các vùng văn bản dài có hỗ trợ cuộn. Các bài dùng giao diện sáng để khớp mẫu học tập.

## Nguồn tài nguyên

- [Birthday Card của Google](https://github.com/google-developer-training/basic-android-kotlin-compose-birthday-card-app): ảnh `androidparty.png`.
- [Compose practice problems của Google](https://github.com/google-developer-training/basic-android-kotlin-compose-training-practice-problems/tree/main/Unit%201/Pathway%203): ảnh Article, Task Completed và chuỗi nội dung các bài luyện tập.
- [Business card assets của Google](https://github.com/google-developer-training/basic-android-compose-training-assets): logo Android.
- Giữ bản giấy phép Apache 2.0 trong `LICENSE` và thông tin bản quyền có sẵn trong tài nguyên nguồn. Logo Android thuộc chủ sở hữu tương ứng.

Kết quả kiểm tra build được ghi trong `VALIDATION.txt`. Việc hoàn thành video/quiz trên tài khoản khóa học do sinh viên tự thực hiện.
