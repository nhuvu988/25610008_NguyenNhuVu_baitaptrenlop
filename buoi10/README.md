# Buổi 10 — Dùng Room để lưu trữ cố định dữ liệu

**Sinh viên:** Nguyễn Như Vũ — **MSSV:** 25610008.

Thực hiện [Unit 6, Pathway 2 — Use Room for data persistence](https://developer.android.com/courses/pathways/android-basics-compose-unit-6-pathway-2?hl=vi).

| Bài trong lộ trình | Module | Phần triển khai |
|---|---|---|
| [Lưu trữ cố định dữ liệu bằng Room](https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?hl=vi) | `inventory` | Entity `Item`, DAO, singleton `InventoryDatabase`, repository, form thêm mặt hàng và coroutine lưu dữ liệu |
| [Đọc và cập nhật dữ liệu bằng Room](https://developer.android.com/codelabs/basic-android-kotlin-compose-update-data-room?hl=vi) | `inventory` | Danh sách từ Flow/StateFlow, chi tiết, bán một đơn vị, sửa, xác nhận xóa và kiểm thử DAO |
| [Thực hành Bus Schedule](https://developer.android.com/codelabs/basic-android-kotlin-compose-practice-bus-schedule-app) | `busschedule` | Entity/DAO/database, `createFromAsset`, lịch xe theo giờ đến và lọc theo tên trạm |
| [Kiểm thử Flow](https://developer.android.com/kotlin/flow/test) (đọc thêm) | `inventory` | Repository giả và subscriber để kiểm thử `HomeViewModel.homeUiState` tạo bằng `stateIn` |

Hai bài Inventory được hoàn thành trong **một app cuối cùng**; Bus Schedule là app riêng. Video và quiz của website học tập không phải module ứng dụng, không tự đánh dấu hoàn thành trên tài khoản Google.

## Mở và chạy

1. Android Studio → Open → chọn thư mục `buoi10` trong repo.
2. Đồng bộ Gradle; dùng JDK đi kèm Android Studio. Project dùng Gradle 9.6.0, AGP 9.4.1, Kotlin tích hợp/Compose compiler 2.2.10, KSP 2.3.6 và Room 2.8.5.
3. Chọn module **inventory** hoặc **busschedule**, chọn emulator/điện thoại API 24 trở lên, rồi Run. Database Inspector cần API 26 trở lên.
4. Nếu máy chưa nhận Android SDK, chọn đường dẫn SDK trong Android Studio; `local.properties` là file riêng của máy và không được commit.

Application ID: `com.nhuvu25610008.buoi10.inventory` và `com.nhuvu25610008.buoi10.busschedule`.

## Inventory

- Bấm **+**, nhập tên, giá (ví dụ `10.5`) và số lượng, rồi **Save**. Danh sách được sắp xếp theo tên và cập nhật từ Room Flow.
- Chỉ cho lưu tên không rỗng, giá hữu hạn không âm và số lượng nguyên không âm; không chấp nhận chữ, NaN, Infinity, số âm hoặc số lượng thập phân.
- Mở mặt hàng → **Sell** để giảm một đơn vị. Hết hàng thì nút bị vô hiệu hóa. DAO giảm số lượng bằng một câu UPDATE có điều kiện, nên nhiều thao tác bán không làm âm kho hoặc mất lần cập nhật.
- Bấm biểu tượng sửa → đổi tên/giá/số lượng → **Save**.
- **Delete** mở hộp thoại xác nhận; **No** giữ dữ liệu, **Yes** xóa mặt hàng và trở về danh sách.
- Dữ liệu lưu vào SQLite trên đĩa, còn sau khi đóng/mở app. Không dùng `allowMainThreadQueries` trong app; chỉ có trong kiểm thử database in-memory theo bài học.
- Để xem dữ liệu: App Inspection → Database Inspector → `item_database` → bảng `items`.

## Bus Schedule

- Dùng nguyên database mẫu Google `assets/database/bus_schedule.db`: **31 lịch đến, 10 trạm**. Đây là dữ liệu bài học, không phải lịch xe thời gian thực.
- Room tạo database từ asset ở lần chạy đầu tiên; DAO trả Flow và sắp xếp `arrival_time ASC`.
- Chọn một dòng lịch để xem các giờ đến của trạm đó. Back/Up trở về toàn bộ lịch; tiêu đề và trạm được giữ sau khi tạo lại Activity.
- `arrival_time` trong database mẫu là Unix timestamp theo **giây**. Khi hiển thị được nhân 1.000 để chuyển sang mili giây; giờ hiển thị theo múi giờ của thiết bị.

## Mã nguồn và kiểm tra

- Inventory: `inventory/src/main/java/com/example/inventory/` (`data`, `ui/home`, `ui/item`, `ui/navigation`).
- Bus Schedule: `busschedule/src/main/java/com/example/busschedule/` (`data`, `ui`) và database trong `src/main/assets/database/`.
- Schema phiên bản 1 được xuất vào `inventory/schemas/` và `busschedule/schemas/` để đối chiếu Entity với cấu trúc SQLite.
- [SOURCES.md](SOURCES.md) ghi nguồn mẫu, commit gốc và các điều chỉnh. Giữ copyright và giấy phép Apache 2.0.
- [VALIDATION.txt](VALIDATION.txt) ghi lệnh và kết quả kiểm tra thực tế.

```powershell
.\gradlew.bat assembleDebug testDebugUnitTest assembleDebugAndroidTest --console=plain --max-workers=2
# Kết nối emulator/điện thoại để chạy DAO và UI test:
.\gradlew.bat connectedDebugAndroidTest --console=plain --max-workers=2
```

Kiểm thử bao gồm xác thực form, Fake Repository/StateFlow, CRUD/sắp xếp DAO, bán đồng thời, mở lại database trên đĩa, đọc đủ asset lịch xe, lọc trạm, thao tác thêm/bán/sửa/xóa qua UI và tạo lại Activity.
