# Nguồn Buổi 11

Các dự án Google Developer Training bên dưới dùng Apache License 2.0. Giữ nguyên thông báo bản quyền trong source và các file LICENSE. Đây là bài tích hợp/thực hành theo khóa học, không nhận mã mẫu Google là mã tự viết.

| Module | Repository chính thức | Branch / commit đã lấy | Giấy phép |
|---|---|---|---|
| `racetracker` | https://github.com/google-developer-training/basic-android-kotlin-compose-training-race-tracker | `main` / `2639dc31cf7f80d453c1678b1e75b54311eac365` | `LICENSE-racetracker` |
| `marsphotos` | https://github.com/google-developer-training/basic-android-kotlin-compose-training-mars-photos | `main` / `8399c839ce5f4be66e0ae1103ed0e04121c97fe4` | `LICENSE-marsphotos` |
| `amphibians` | https://github.com/google-developer-training/basic-android-kotlin-compose-training-amphibians | `main` / `c5c8be5bce43dcfa4292c3ef2b108e5fefabcc16` | `LICENSE-amphibians` |

Thay đổi so với mẫu: gộp thành một Gradle project nhiều module; cập nhật cấu hình AGP/SDK/Kotlin; applicationId riêng theo MSSV/Buổi 11; dùng converter kotlinx.serialization của Retrofit; bỏ qua trường JSON mới và xử lý lỗi JSON; giới hạn tiến độ Race Tracker không vượt 100%; sửa độ tương phản icon status bar của Mars Photos khi edge-to-edge; bổ sung kiểm thử. Các file Kotlin upstream đã sửa có chú thích Modified for Buoi 11.

Bookshelf được triển khai riêng theo yêu cầu codelab, dùng Gson như đề bài. Các kiểm thử JVM dùng fake repository/service hoặc MockWebServer, không gọi Google Books thật.

## Tài liệu khóa học

- [Unit 5](https://developer.android.com/courses/android-basics-compose/unit-5?hl=vi)
- [Coroutine Kotlin Playground](https://developer.android.com/codelabs/basic-android-kotlin-compose-coroutines-kotlin-playground)
- [Coroutine Android Studio / Race Tracker](https://developer.android.com/codelabs/basic-android-kotlin-compose-coroutines-android-studio)
- [Lấy dữ liệu Internet](https://developer.android.com/codelabs/basic-android-kotlin-compose-getting-data-internet)
- [Repository và manual DI](https://developer.android.com/codelabs/basic-android-kotlin-compose-add-repository)
- [Tải ảnh bằng Coil](https://developer.android.com/codelabs/basic-android-kotlin-compose-load-images)
- [Amphibians](https://developer.android.com/codelabs/basic-android-kotlin-compose-practice-amphibians-app)
- [Bookshelf](https://developer.android.com/codelabs/basic-android-kotlin-compose-bookshelf)
- [Google Books API](https://developers.google.com/books/docs/v1/using)

Không có API key, `local.properties`, cache/build hoặc thư mục `.git` của upstream trong bài nộp.
