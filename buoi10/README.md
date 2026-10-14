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
