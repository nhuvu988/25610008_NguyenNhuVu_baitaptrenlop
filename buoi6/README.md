# Buổi 6 — Danh sách và lưới cuộn bằng Jetpack Compose

Nguyễn Như Vũ — 25610008.

Lộ trình: [Xây dựng một danh sách có thể cuộn](https://developer.android.com/courses/pathways/android-basics-compose-unit-3-pathway-2?hl=vi).

## Bài thực hành

| Phần | Module | Nội dung |
|---|---|---|
| Danh sách cuộn | `affirmations` | 10 lời động viên, mỗi thẻ có ảnh và văn bản; `LazyColumn`, `Card`, `Image`, `Text` |
| Đổi icon | `affirmations` | Icon Affirmations từ bộ tài nguyên chính thức; foreground/background thích ứng và icon legacy theo mật độ |
| Lưới cuộn | `courses` | 14 chủ đề theo codelab, 2 cột `LazyVerticalGrid`, ảnh 68dp, tên, biểu tượng và số khóa học |

Mỗi module là một ứng dụng Android riêng, cài được đồng thời. Không cần database, Internet trong ứng dụng hoặc đăng nhập.

## Mở và chạy

1. Android Studio → **File → Open** → chọn thư mục **buoi6** này (chứa `settings.gradle.kts`).
2. Chờ Gradle Sync. Lần đầu cần Internet để tải thư viện.
3. Chọn cấu hình/module **affirmations** hoặc **courses**.
4. Chọn máy ảo hoặc điện thoại Android API 24 trở lên, nhấn **Run ▶**.

Đây là ứng dụng Android Compose; không chạy kiểu `main()` Kotlin console như buổi 4.

Build bằng PowerShell tại thư mục này:

```powershell
.\gradlew.bat :affirmations:assembleDebug :courses:assembleDebug
```

APK nằm ở `<module>/build/outputs/apk/debug/`.

Máy khác cần cài Android SDK 37 và để Android Studio tạo `local.properties`. Gradle dùng cấu hình toolchain JDK 25 đã đi kèm dự án; mã ứng dụng nhắm bytecode Java 11. AGP 9.4.1 hỗ trợ Kotlin tích hợp, không thêm plugin `kotlin.android` cũ.

## Đọc mã

Trong `affirmations/src/main/java/com/example/affirmations/`:

- `model/Affirmation.kt`: data class với mã tài nguyên ảnh và chuỗi, có annotation kiểm tra loại tài nguyên.
- `data/Datasource.kt`: dữ liệu 10 lời động viên.
- `MainActivity.kt`: `AffirmationsApp`, `AffirmationList`, `AffirmationCard`, Preview.

Trong `courses/src/main/java/com/example/courses/`:

- `model/Topic.kt`: tên chủ đề, số khóa học, ảnh.
- `data/DataSource.kt`: đúng 14 mục của codelab (Gaming = 164).
- `MainActivity.kt`: `TopicGrid`, `TopicCard`, Preview cho thẻ và toàn màn hình.

`LazyColumn`/`LazyVerticalGrid` dựng các mục khi cần; không bọc chúng trong một vùng cuộn dọc khác. Modifier được truyền vào gốc của composable. `safeDrawingPadding()` bảo vệ nội dung khỏi thanh trạng thái, điều hướng và phần khuyết màn hình.

## Kiểm tra trên thiết bị

- Affirmations: vuốt đến câu thứ 10, vuốt về đầu; ảnh được cắt vừa chiều ngang, thẻ không nằm dưới thanh hệ thống.
- Courses: lưới 2 cột, 14 chủ đề, vuốt đến Tech rồi quay về Architecture; số khóa học hiển thị đúng.
- Xoay màn hình, bật chế độ tối, tăng cỡ chữ để kiểm tra khả năng đọc và cuộn.
- Về màn hình chính: xem icon Affirmations; kiểm tra icon trên launcher dùng mặt nạ tròn/vuông bo góc.

Đã thêm kiểm thử Compose cuộn tới mục cuối rồi quay về đầu tại `src/androidTest/` mỗi module. Chạy khi có thiết bị:

```powershell
.\gradlew.bat :affirmations:connectedDebugAndroidTest :courses:connectedDebugAndroidTest
```

Không coi việc biên dịch APK là đã kiểm thử giao diện. Xem `VALIDATION.txt` để biết kết quả thực tế của lần kiểm tra.

## Nguồn và thay đổi

Bài này dựa trên mã giải pháp và tài nguyên chính thức của Google, có giữ thông báo bản quyền và giấy phép Apache 2.0 trong `LICENSE`:

- [Affirmations](https://github.com/google-developer-training/basic-android-kotlin-compose-training-affirmations), commit `2343318d84613e13850d24e5fd1ac17f1815fcf9`.
- [Courses](https://github.com/google-developer-training/basic-android-kotlin-compose-training-courses), commit `71526b946d01f467eb10abbd475baabe227c60e0`.
- Ảnh của Romain Guy được upstream công bố theo CC0.

Điều chỉnh cho bài nộp: tách hai module trong một project buổi 6; cập nhật Gradle/SDK phù hợp máy; sửa vùng đệm hệ thống; truyền Modifier ở gốc Card; khóa ổn định cho các mục; thêm Preview và kiểm thử cuộn; sửa dữ liệu Courses về danh sách trong [đề bài lưới](https://developer.android.com/codelabs/basic-android-kotlin-compose-practice-grid?hl=vi).

Phần video và bài kiểm tra/huy hiệu trên tài khoản Google dành cho người học tự hoàn thành; repo này chứa các bài thực hành lập trình.
