# Buổi 4 — Kotlin: bể cá và lập trình hướng đối tượng

- Sinh viên: Nguyễn Như Vũ
- Mã số sinh viên: 25610008
- Ngôn ngữ: Kotlin

[Về danh sách buổi học](../README.md) · [Buổi 6](../buoi6/README.md)

## Nội dung

Mã bài tập nằm trong `console/src/main/kotlin/example/myapp`:

- `Aquarium.kt`: constructor, thuộc tính, getter/setter, kế thừa và `TowerTank`.
- `AquariumFish.kt`: interface, singleton và ủy quyền bằng `by` cho `Shark`, `Plecostomus`.
- `Main.kt`: hàm `buildAquarium()`, `makeFish()` và điểm chạy chương trình.
- `decor/Decoration.kt`: data class, so sánh, destructuring và enum.

Đây là trạng thái mã cuối bài học: lớp trừu tượng AquariumFish đã được thay bằng các interface; setter volume được mở lại để lớp con ghi đè.

## Chạy trong Android Studio

1. Mở trực tiếp thư mục **buoi4** và đồng bộ Gradle. Máy cần Android SDK vì dự án vẫn giữ module Android `app` ban đầu.
2. Chọn cấu hình **Kotlin Console** để chạy bài cá hoặc **Kotlin Decorations** để chạy data class/enum.
3. Có thể chạy nút cạnh `main()` trong các tệp thuộc module **console**. Không chọn module `app` để chạy bài console.

Hoặc dùng Terminal PowerShell tại thư mục **buoi4**:

```powershell
.\gradlew.bat :console:run
.\gradlew.bat :console:runDecorations
```

Kết quả bài cá:

```text
Shark: grey
hunt and eat fish
Plecostomus: gold
eat algae
```

Để chạy bể cá, đổi lời gọi trong `Main.kt` từ `makeFish()` thành `buildAquarium()`.

Module `app` là mẫu Android ban đầu, còn toàn bộ bài Kotlin console nằm trong module `console`.
