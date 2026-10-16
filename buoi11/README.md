# Buổi 11 — Kết nối Internet (Android Basics with Compose, Unit 5)

**Sinh viên:** Nguyễn Như Vũ · **MSSV:** 25610008

Hoàn thành phần thực hành của cả hai lộ trình trong [Unit 5](https://developer.android.com/courses/android-basics-compose/unit-5?hl=vi). Video và quiz trên website là nội dung học, không phải chức năng trong ứng dụng.

| Module | Nội dung | Chạy |
|---|---|---|
| `coroutines` | Kotlin Playground: suspend, delay, launch/join, async/await, tuần tự/đồng thời, hủy và xử lý lỗi | `./gradlew :coroutines:run` |
| `racetracker` | Hai vận động viên chạy đồng thời; Start/Pause, tiếp tục, Reset; kiểm thử coroutine bằng thời gian ảo | Chọn cấu hình `racetracker` |
| `marsphotos` | Retrofit + kotlinx.serialization; repository, manual DI, ViewModel; lưới ảnh Mars bằng Coil; Loading/Error/Retry | Chọn `marsphotos` |
| `amphibians` | API trả tên, loài, mô tả và ảnh; danh sách thẻ; repository và manual DI | Chọn `amphibians` |
| `bookshelf` | Bài tổng hợp tự triển khai: Google Books, Retrofit + Gson, tìm ID rồi tải chi tiết; lưới bìa/tên sách, tìm kiếm, rỗng/lỗi/thử lại | Chọn `bookshelf` |

## Mở và chạy

Mở **thư mục `buoi11`** bằng Android Studio, chờ Gradle Sync, chọn một trong bốn module Android rồi Run. Cần Android API 24 trở lên. Dự án dùng compileSdk/targetSdk 37, AGP 9.4.1, Gradle 9.6.0, Compose compiler 2.2.10 và JDK 17 trở lên tương thích Gradle (đã kiểm tra với JDK 25). Android SDK được Android Studio ghi vào `local.properties`, không commit file này.

Application ID của bốn app có tiền tố `com.nhuvu25610008.buoi11`, nên cài cạnh các bài trước. Các app mạng cần Internet; Race Tracker và ví dụ coroutine chạy offline.

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat testDebugUnitTest :coroutines:test
.\gradlew.bat :coroutines:run
.\gradlew.bat lintDebug
.\gradlew.bat :bookshelf:connectedDebugAndroidTest
```

APK sau build: `<module>/build/outputs/apk/debug/<module>-debug.apk`. Kiểm thử Bookshelf trên máy ảo dùng dữ liệu giả, không cần API key và không phụ thuộc quota mạng.

## Các điểm cần hiểu khi trình bày

- `delay` tạm ngưng coroutine; không dùng `Thread.sleep` hoặc `runBlocking` trên UI Android.
- `launch` thực hiện tác vụ không trả kết quả; `async/await` thu kết quả. `coroutineScope` giữ quan hệ cha/con và truyền hủy tác vụ.
- Retrofit định nghĩa HTTP GET và ánh xạ JSON qua converter. `INTERNET` được khai báo trong manifest của ba app mạng.
- `AppContainer` tạo service/repository; Application giữ container; ViewModel nhận repository thay vì tự tạo Retrofit. Có thể thay bằng fake khi kiểm thử.
- Coil tải/cache ảnh bất đồng bộ; giao diện có placeholder/error. Bookshelf đổi URL bìa HTTP thành HTTPS và xử lý trường JSON bị thiếu.
- Bookshelf dùng hai API: `GET volumes?q=...` và `GET volumes/{id}`. Tải tối đa 4 chi tiết đồng thời, giữ thứ tự kết quả và hủy truy vấn cũ khi tìm mới.
- Race Tracker giữ tiến độ trong composition như codelab; xoay màn hình sẽ bắt đầu lại. Hai người chạy đến đích với tốc độ khác nhau, có thể tạm dừng/tiếp tục mà không chặn UI.

## Google Books và hạn mức dịch vụ

API được gọi tại `https://www.googleapis.com/books/v1/`. Không đưa dữ liệu giả vào luồng chạy thật để giả vờ tải thành công. Dịch vụ có thể trả HTTP 403/429 theo quota; UI giải thích lỗi và có nút Thử lại. Khi kiểm tra ngày 16/10/2026, endpoint công khai không có key trả HTTP 429 từ mạng phát triển; Mars/Amphibians và ảnh đầu tiên trả HTTP 200.

Nếu cần quota của Google Cloud project riêng, bật Books API và đặt biến môi trường **trên máy của bạn** trước khi build:

```powershell
$env:GOOGLE_BOOKS_API_KEY = 'API_KEY_CUA_BAN'
.\gradlew.bat :bookshelf:assembleDebug
```

Hoặc dùng thuộc tính Gradle `googleBooksApiKey` trong file Gradle của người dùng, ngoài repository. Không commit API key. APK là client công khai, nên cần cấu hình giới hạn và quota cho key theo Google Cloud; không dùng key này như một bí mật phía máy chủ.

## Nguồn và kiểm thử

Xem [SOURCES.md](SOURCES.md) để biết nguồn codelab, commit upstream và giấy phép. Các phần Race Tracker/Mars/Amphibians được tích hợp từ mã mẫu chính thức có ghi nguồn, điều chỉnh để chạy cùng dự án; Bookshelf, ví dụ coroutine và các kiểm thử bổ sung được triển khai cho Buổi 11. Kết quả thực tế ghi trong [VALIDATION.txt](VALIDATION.txt).

## Ảnh chạy trên máy ảo

- [Mars Photos — ảnh thật từ NASA](screenshots/marsphotos.png)
- [Amphibians — dữ liệu và ảnh thật](screenshots/amphibians.png)
- [Race Tracker](screenshots/racetracker.png)
- [Bookshelf — thông báo quota thực tế của Google Books](screenshots/bookshelf.png)
