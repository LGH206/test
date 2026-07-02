# 🏇 Hệ Thống Quản Lý Giải Đua Ngựa

## Horse Racing Tournament Management System

---

## Giới thiệu
```text
Hệ thống quản lý giải đua ngựa được xây dựng nhằm hỗ trợ quản lý toàn bộ quá trình tổ chức giải đấu, bao gồm:

* Quản lý người dùng
* Quản lý ngựa đua
* Quản lý kỵ sĩ (Jockey)
* Quản lý giải đấu (Tournament)
* Quản lý cuộc đua (Race)
* Quản lý đăng ký tham gia
* Quản lý thư mời
* Quản lý kết quả thi đấu
```
---

## Công nghệ sử dụng (Technology Stack)
```text
Ngôn ngữ lập trình - Java 17                          
Framework          - Spring Boot 3                    
ORM Framework      - Hibernate                        
Persistence API    - JPA                              
Database           - Microsoft SQL Server             
Build Tool         - Maven                            
Front-end          - Thymeleaf, HTML, CSS, JavaScript 
```

## Kiến trúc hệ thống (System Architecture)

```text
Client
   │
   ▼
Controller Layer
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
SQL Server Database
```

### Nguyên tắc thiết kế

```text
MVC Architecture
SOLID Principles
JPA / Hibernate ORM
Layered Architecture
```

---

## Cấu trúc dự án

```text
src/main/java/com/fe/horseracing

├── config
├── controller
├── dto
├── entity
├── exception
├── factory
├── repository
├── service
│   ├── impl
│   └── interfaces
└── strategy
```

```text
src/main/resources

├── static
│   ├── css
│   ├── js
│   ├── images
│   └── uploads
│
├── templates
│   ├── fragments
│   ├── horse
│   ├── jockey
│   ├── race
│   ├── tournament
│   ├── registration
│   ├── invitation
│   └── result
│
└── application.properties
```

---

## Mô hình nghiệp vụ (Domain Model)

```text
User // lớp cha
├── Admin
├── HorseOwner
├── Jockey
├── RaceReferee
└── Spectator

Horse
Tournament
Race
Registration
Invitation
Result
```

---

## Cơ sở dữ liệu

```text
Database      : SQL Server
ORM           : Hibernate
Persistence   : JPA
```

---

## Tiến độ phát triển (Development Roadmap)

##TODO
### Phase 1 – Khởi tạo dự án

* [x] Tạo Maven Project
* [x] Cấu hình Spring Boot
* [x] Cấu hình JPA / Hibernate
* [x] Cấu hình SQL Server Driver
* [x] Xây dựng cấu trúc thư mục

### Phase 2 – Thiết kế Entity

* [x] User
* [ ] Admin
* [ ] HorseOwner
* [ ] Jockey
* [ ] RaceReferee
* [ ] Spectator
* [ ] Horse
* [ ] Tournament
* [ ] Race
* [ ] Registration
* [ ] Invitation
* [ ] Result

### Phase 3 – Persistence Layer

* [ ] Repository
* [ ] JPA Mapping
* [ ] Kết nối cơ sở dữ liệu

### Phase 4 – Business Layer

* [ ] Service Interface
* [ ] Service Implementation
* [ ] Strategy Pattern
* [ ] Factory Pattern

### Phase 5 – Presentation Layer

* [ ] Controller
* [ ] Thymeleaf UI
* [ ] Giao diện người dùng

### Phase 6 – Testing & Deployment

* [ ] Unit Test
* [ ] Integration Test
* [ ] Hoàn thiện và triển khai

---
##Link docs : Nhiệm vụ
https://docs.google.com/document/d/1pLJoth28cUhWxcb7bnysh1s9cDHteWxVvWAUS4RGBB4/edit?usp=sharing


