# Buổi 3 — Hàm trong Kotlin

Nguyễn Như Vũ · 25610008

[Lộ trình được giao](https://developer.android.com/courses/pathways/android-development-with-kotlin-2?hl=vi) · [Codelab Functions](https://developer.android.com/codelabs/android-development-kotlin-2.1)

Đây là chương trình Kotlin/JVM console, không phải ứng dụng Android. Mã chính: `console/src/main/kotlin/example/functions/Hello.kt`.

| Phần bài | Đã thực hiện |
|---|---|
| main và tham số | `main(args)`, truyền lời chào; chạy không có args vẫn an toàn |
| Biểu thức có giá trị | Kết quả `println` là Unit, if/else và string template |
| Hàm và when | `randomDay`, `fishFood`, `feedTheFish`, when/else trả về thức ăn |
| Giá trị mặc định | `swim()` / tham số vị trí / tham số có tên; `shouldChangeWater` |
| Hàm thu gọn | `isTooHot`, `isDirty`, `isSunday`; kiểm tra các ngưỡng 30 |
| Filter | Eager List so với lazy Sequence; map có log, first, toList, flatten |
| Lambda | Kiểu `(Int) -> Int`, higher-order `updateDirty`, `::increaseDirty`, trailing lambda |

`fishFood` dùng bản cuối của codelab: Tuesday và Saturday đi vào nhánh `else` trả `nothing`. Ngày được chọn ngẫu nhiên nên dòng cho cá ăn có thể khác giữa các lần chạy. Địa chỉ đối tượng Sequence được in ra cũng không cố định.

## Chạy bài

Mở `buoi3` trong Android Studio hoặc IntelliJ, chờ Sync. Chọn cấu hình **Buoi 3 - Functions**, nhấn Run; hoặc mở Hello.kt và chạy main. Không cần điện thoại/máy ảo.

```powershell
.\gradlew.bat :console:run --args="Kotlin!"
.\gradlew.bat :console:test
```

Kết quả gồm lời chào, `kotlin.Unit`, ngày/thức ăn, ba tốc độ bơi, log lazy sequence và kết quả lambda `10`, `15`, `16`, `42`. Bốn unit test kiểm tra ngưỡng thay nước, lựa chọn ngày/thức ăn, tính lazy và higher-order function.

Ví dụ tự viết theo yêu cầu codelab, giữ ngữ nghĩa và một số tên/dữ liệu mẫu của Google (mẫu code được cấp phép Apache 2.0). Phiên bản Gradle/Kotlin thống nhất với các buổi Android. Video/quiz và huy hiệu trên website do sinh viên thực hiện. Xem `VALIDATION.txt` để biết kết quả chạy thực tế.
