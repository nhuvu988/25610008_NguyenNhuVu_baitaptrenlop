# Nguồn mẫu và phần điều chỉnh — Buổi 10

- `inventory`: [Google Inventory app](https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app), nhánh `main`, commit `e0773b718f2670e401c039ee965879c5e88ca424`.
- `busschedule`: [Google Bus Schedule app](https://github.com/google-developer-training/basic-android-kotlin-compose-training-bus-schedule-app), nhánh `main`, commit `1d6d81885438202187dc5c5109386c3b4ef238b7`.
- Mã và tài nguyên mẫu (bao gồm database lịch xe) dùng Apache 2.0. Giữ copyright nguồn trong file và giấy phép trong `LICENSE-inventory`, `LICENSE-busschedule`.

Đây là bản hoàn thiện của codelab dựa trên mã giải pháp chính thức, được tích hợp vào project buổi 10 của repo bài tập.

## Điều chỉnh so với mẫu

- Tổ chức hai module trong một project `Buoi10`; cấu hình Gradle/AGP/Kotlin phù hợp các buổi trước, KSP 2.3.6, Room 2.8.5 và application ID riêng theo MSSV.
- Inventory: dùng chung kiểm tra tên, giá hữu hạn không âm, số lượng nguyên không âm cho thêm/sửa; trim tên; DAO trả nullable khi không tìm thấy ID; bán bằng UPDATE nguyên tử để xử lý nhiều lần bán.
- Thu thập Flow trong UI bằng `collectAsStateWithLifecycle`; Bus Schedule giữ Flow theo ViewModel/trạm, lấy tiêu đề từ navigation back stack, mã hóa tên trạm khi tạo route.
- Inventory điều hướng trên `Dispatchers.Main.immediate` sau khi hoàn tất ghi Room để bảo đảm thao tác back stack chạy trên luồng chính.
- Dùng applicationContext cho singleton database, xuất schema v1 và bỏ chính sách xóa dữ liệu khi thiếu migration. Nếu đổi schema trong tương lai cần thêm migration.
- Thêm unit test về input/StateFlow, kiểm thử DAO/bán đồng thời/database trên đĩa, kiểm thử asset lịch xe và UI thêm/bán/sửa/xóa/điều hướng/tạo lại Activity.
