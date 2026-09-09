# User Management System

A desktop-based **User Management System** built with **Java 21, Java Swing, Maven, and Microsoft SQL Server**.

این پروژه یک نرم‌افزار دسکتاپ برای **مدیریت کاربران، گروه‌ها، سمت‌ها و سطح دسترسی‌ها** است که برای سازمان‌ها، شرکت‌ها و نرم‌افزارهای داخلی طراحی شده است.

---

## فارسی

### معرفی پروژه

**User Management System** یک سیستم مدیریت کاربران مبتنی بر Java Desktop است که امکان مدیریت متمرکز کاربران و کنترل دقیق دسترسی آن‌ها به بخش‌های مختلف نرم‌افزار را فراهم می‌کند.

سیستم از ساختار **Form → Permission → User/Group** استفاده می‌کند؛ بنابراین می‌توان مشخص کرد هر کاربر یا گروه به کدام بخش سیستم دسترسی داشته باشد و چه عملیاتی را بتواند انجام دهد.

داده‌ها در **Microsoft SQL Server** ذخیره می‌شوند و پروژه دارای ساختار تفکیک‌شده برای رابط کاربری، مدل‌ها، دسترسی به داده‌ها، امنیت و مدیریت اتصال به دیتابیس است.

---

## امکانات اصلی

### مدیریت کاربران

امکانات بخش مدیریت کاربران شامل:

* ایجاد کاربر جدید
* ویرایش اطلاعات کاربر
* حذف کاربر
* فعال یا غیرفعال کردن کاربر
* تعیین نام کاربری
* تعیین و مدیریت رمز عبور
* تعیین نام و نام خانوادگی
* تعیین سمت کاربر
* اختصاص کاربر به یک یا چند گروه
* مشاهده وضعیت فعال/غیرفعال کاربران
* مشاهده لیست کاربران در قالب جدول
* جستجوی کاربران
* نمایش تعداد کل رکوردها
* Pagination برای مدیریت تعداد زیاد کاربران
* تعیین تعداد رکوردهای قابل نمایش در هر صفحه
* حرکت به صفحه اول، قبل، بعد و آخر

در فرم مدیریت کاربران، دسترسی‌های **ثبت، ویرایش و حذف** نیز مستقل از یکدیگر کنترل می‌شوند.

---

### مدیریت Groups

سیستم امکان ایجاد ساختار گروهی برای کاربران را فراهم می‌کند.

کاربر می‌تواند:

* گروه ایجاد کند
* گروه‌ها را مدیریت کند
* کاربران را عضو گروه کند
* یک کاربر را در چند گروه قرار دهد
* عضویت کاربران در گروه‌ها را تغییر دهد

این ساختار باعث می‌شود بتوان به‌جای تنظیم تک‌تک کاربران، دسترسی‌ها را در سطح گروه نیز مدیریت کرد.

---

### مدیریت Positions

سیستم دارای بخش جداگانه‌ای برای مدیریت **Position / سمت سازمانی** است.

از Position می‌توان برای مشخص کردن سمت کاربران در سازمان استفاده کرد.

برای مثال:

* مدیر سیستم
* مدیر واحد
* کارشناس
* اپراتور
* حسابدار
* کارمند

سمت‌ها در فرم مدیریت کاربران نیز قابل انتخاب هستند.

---

### مدیریت Permissions

یکی از مهم‌ترین قابلیت‌های پروژه، سیستم **Permission Management** است.

Permissionها به فرم‌ها و بخش‌های مختلف سیستم متصل هستند و می‌توان دسترسی‌های مختلف را برای کاربران تعیین کرد.

برای هر بخش می‌توان Permissionهای متفاوتی تعریف کرد؛ برای مثال:

* مشاهده
* ایجاد
* ویرایش
* حذف
* ثبت
* سایر عملیات اختصاصی

دسترسی‌ها به‌صورت Tree نمایش داده می‌شوند و مدیر می‌تواند Permissionهای موردنظر را برای کاربر انتخاب و ذخیره کند.

---

### دسترسی‌های اختصاصی کاربران

علاوه بر Group-based Access، پروژه از **Custom User Permissions** نیز پشتیبانی می‌کند.

یعنی می‌توان:

> یک Permission را مستقیماً به یک کاربر اختصاص داد، بدون اینکه لازم باشد آن Permission از طریق Group به کاربر داده شود.

این قابلیت برای شرایطی مناسب است که یک کاربر نیاز به دسترسی متفاوتی نسبت به سایر اعضای گروه خود داشته باشد.

---

### کنترل دسترسی در رابط کاربری

دسترسی‌ها فقط در دیتابیس ذخیره نمی‌شوند؛ رابط کاربری نیز بر اساس Permissionهای کاربر کنترل می‌شود.

برای مثال اگر کاربر Permission مربوط به حذف را نداشته باشد، گزینه Delete در فرم مدیریت کاربران نمایش داده نمی‌شود.

به همین شکل، بخش‌هایی مانند مدیریت کاربران و Positionها نیز قبل از باز شدن Permission کاربر را بررسی می‌کنند.

---

### Login و Authentication

سیستم دارای صفحه Login است و قبل از ورود به بخش‌های مدیریتی، کاربر احراز هویت می‌شود.

رمز عبور نیز در لایه امنیتی پروژه توسط `PasswordHasher` مدیریت می‌شود.

---

### Pagination و Search

در بخش User Management امکانات زیر وجود دارد:

* جستجو
* Pagination
* نمایش تعداد رکوردها
* نمایش تعداد صفحات
* انتخاب Page Size
* First Page
* Previous Page
* Next Page
* Last Page

Page Size کاربر نیز در دیتابیس ذخیره می‌شود تا تنظیمات نمایش لیست کاربران حفظ شود.

---

### Database Management

پروژه از **Microsoft SQL Server** استفاده می‌کند.

فایل زیر برای ایجاد دیتابیس ارائه شده است:

```text
Database/database-setup.sql
```

این فایل دیتابیس:

```text
UserManagementSystem
```

را ایجاد می‌کند و ساختار جداول، ارتباطات و داده‌های اولیه مورد نیاز سیستم را آماده می‌سازد.

> توجه: فایل `database-setup.sql` شامل دستور حذف دیتابیس قبلی در صورت وجود است؛ بنابراین قبل از اجرای آن روی دیتابیس دارای اطلاعات واقعی، باید این بخش بررسی شود.

---

## ساختار پروژه

```text
user-management-system/
│
├── Database/
│   └── database-setup.sql
│
├── lib/
│   ├── JavaDate
│   ├── ICU4J
│   ├── SwingX
│   └── SwingX BeanInfo
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/programsfuture/usermanagement/
│   │   │       │
│   │   │       ├── dao/
│   │   │       ├── data/
│   │   │       ├── model/
│   │   │       ├── security/
│   │   │       └── ui/
│   │   │
│   │   └── resources/
│   │
│   └── test/
│
├── pom.xml
└── nbactions.xml
```

ساختار Java پروژه بخش‌های جداگانه‌ای برای DAO، Data Access، Model، Security و UI دارد.

---

## تکنولوژی‌ها

| Technology           | Usage                         |
| -------------------- | ----------------------------- |
| Java 21              | Core application              |
| Java Swing           | Desktop User Interface        |
| Maven                | Build & Dependency Management |
| Microsoft SQL Server | Database                      |
| JDBC                 | Database Connectivity         |
| FlatLaf              | Modern Swing Look & Feel      |
| SwingX               | Extended Swing Components     |
| JUnit                | Testing                       |

نسخه Java مورد استفاده پروژه در `pom.xml` برابر **Java 21** تنظیم شده است و JDBC Driver رسمی Microsoft SQL Server نیز به‌عنوان dependency پروژه استفاده می‌شود.

---

## مناسب برای چه پروژه‌هایی است؟

این سیستم می‌تواند به‌عنوان User Management Module برای نرم‌افزارهای زیر استفاده شود:

* نرم‌افزارهای سازمانی
* سیستم‌های اداری
* ERP
* CRM
* سیستم‌های حسابداری
* سیستم‌های منابع انسانی
* نرم‌افزارهای داخلی شرکت‌ها
* سیستم‌های مدیریت سازمان
* نرم‌افزارهای دارای Role و Permission
* نرم‌افزارهای Desktop مبتنی بر Java

همچنین می‌توان ساختار آن را به‌عنوان پایه یک نرم‌افزار بزرگ‌تر استفاده کرد و فرم‌های جدید را به سیستم Permission متصل کرد.

---

## مدل دسترسی

ساختار کلی سیستم به شکل زیر است:

```text
User
 │
 ├── Position
 │
 ├── Groups
 │    └── Permissions
 │
 └── Custom Permissions
```

و Permissionها به Formهای سیستم متصل هستند:

```text
Form
 ├── Permission 1
 ├── Permission 2
 ├── Permission 3
 └── Permission 4
```

در نتیجه می‌توان یک سیستم دسترسی چندلایه ایجاد کرد:

```text
User
   ↓
Group
   ↓
Permissions
   ↓
Forms
```

و در صورت نیاز:

```text
User
   ↓
Custom Permissions
   ↓
Forms
```

---

## نصب و راه‌اندازی

### پیش‌نیازها

برای اجرای پروژه نیاز است:

* JDK 21
* Maven
* Microsoft SQL Server
* یک SQL Server Client مانند SQL Server Management Studio
* Git

---

### 1. Clone کردن پروژه

```bash
git clone https://github.com/programsfuture/user-management-system.git
cd user-management-system
```

---

### 2. ایجاد دیتابیس

فایل زیر را در SQL Server اجرا کنید:

```text
Database/database-setup.sql
```

این فایل دیتابیس `UserManagementSystem` را ایجاد می‌کند.

---

### 3. تنظیم اتصال دیتابیس

اطلاعات اتصال SQL Server را در تنظیمات Database پروژه وارد کنید.

سیستم دارای بخش اختصاصی برای تنظیمات اتصال دیتابیس است و منطق اتصال نیز در کلاس‌های مربوط به Database Configuration و Database Connection قرار گرفته است.

---

### 4. Build پروژه

```bash
mvn clean package
```

---

### 5. اجرای برنامه

```bash
mvn exec:java
```

کلاس اصلی پروژه:

```text
com.programsfuture.usermanagement.UserManagementSystem
```

---

## Security

سیستم دارای لایه امنیتی مجزا است:

```text
security/
├── PasswordHasher.java
└── PermissionManager.java
```

`PasswordHasher` برای مدیریت رمز عبور و `PermissionManager` برای بررسی دسترسی‌های کاربر استفاده می‌شوند.

---

## مزایای پروژه

* Desktop Application مستقل
* استفاده از Java 21
* اتصال مستقیم به SQL Server
* ساختار مناسب برای نرم‌افزارهای سازمانی
* مدیریت کامل کاربران
* مدیریت Group
* مدیریت Position
* Permission Management
* Custom User Permissions
* فعال/غیرفعال کردن کاربران
* Search
* Pagination
* کنترل Permission در UI
* Password Hashing
* مدیریت تنظیمات Database
* ساختار قابل توسعه برای اضافه کردن Formهای جدید

---

# English

## Overview

**User Management System** is a desktop application developed with **Java 21, Java Swing, Maven, and Microsoft SQL Server**.

It provides a centralized solution for managing users, organizational positions, groups, forms, and permissions.

The main purpose of the system is to provide a reusable **authentication and authorization foundation** for enterprise and internal desktop applications.

The project uses a structured permission model that allows administrators to control what each user can access and which operations they are allowed to perform.

---

## Features

### User Management

The User Management module provides:

* Create users
* Update users
* Delete users
* Activate/deactivate users
* Manage usernames
* Manage passwords
* Manage first and last names
* Assign positions
* Assign users to groups
* Search users
* Display users in a table
* Pagination
* Configurable page size
* First / Previous / Next / Last page navigation
* Display total records
* Display total pages

User create, update, and delete operations are controlled independently through permissions.

---

### Group Management

The system supports user groups.

Administrators can:

* Create groups
* Manage groups
* Assign users to groups
* Remove users from groups
* Assign permissions through groups

This makes it possible to manage permissions for multiple users without configuring every user individually.

---

### Position Management

The application provides a dedicated Position Management module.

Positions can represent organizational roles such as:

* System Administrator
* Manager
* Supervisor
* Operator
* Accountant
* Employee

Users can be assigned to a position from the User Management form.

---

### Permission Management

Permission management is one of the core features of the application.

Permissions are associated with application forms and can be assigned to users.

The application provides a tree-based permission interface, allowing administrators to select and save permissions for a specific user.

A typical structure is:

```text
Form
 ├── View
 ├── Create
 ├── Update
 └── Delete
```

The actual permissions can be customized according to the application's requirements.

---

### Custom User Permissions

The system supports direct permissions for individual users.

This allows an administrator to give a specific user additional permissions without changing the permissions of the user's entire group.

For example:

```text
Group A
 ├── View Users
 └── Update Users

User John
 ├── View Users
 ├── Update Users
 └── Delete Users   ← Custom Permission
```

---

### UI-Level Authorization

Permissions are also enforced at the user-interface level.

For example, if a user does not have the delete permission, the Delete action is hidden from the User Management interface.

Application modules such as User Management and Position Management also check permissions before opening.

---

### Authentication

The application provides a Login interface for user authentication.

Password handling is separated into the security layer through `PasswordHasher`, while `PermissionManager` handles permission checks.

---

### Search & Pagination

The User Management module includes:

* User search
* Pagination
* Configurable page size
* Total record count
* Total page count
* First page
* Previous page
* Next page
* Last page

The user's page-size preference can also be stored in the database.

---

## Database

The application uses **Microsoft SQL Server**.

Database setup is provided in:

```text
Database/database-setup.sql
```

The script creates:

```text
UserManagementSystem
```

and initializes the database structure required by the application.

---

## Project Architecture

```text
User Interface
      │
      ▼
   Security
      │
      ▼
     DAO
      │
      ▼
Database Layer
      │
      ▼
SQL Server
```

Main packages:

```text
com.programsfuture.usermanagement
│
├── dao
├── data
├── model
├── security
└── ui
```

The repository separates DAO, data access, models, security, and UI components.

---

## Technology Stack

| Technology           | Purpose                       |
| -------------------- | ----------------------------- |
| Java 21              | Application Runtime           |
| Java Swing           | Desktop UI                    |
| Maven                | Build & Dependency Management |
| Microsoft SQL Server | Database                      |
| JDBC                 | Database Connectivity         |
| FlatLaf              | Swing Look & Feel             |
| SwingX               | Extended Swing Components     |
| JUnit                | Testing                       |

The project is configured for Java 21 and uses Microsoft's SQL Server JDBC driver.

---

## Use Cases

This project can be used as a foundation for:

* Enterprise applications
* Internal company applications
* ERP systems
* CRM systems
* Accounting software
* HR systems
* Administrative applications
* Desktop management systems
* Java-based business applications
* Applications requiring role/permission management

It can also be extended by adding new application forms and registering their permissions in the permission system.

---

## Permission Architecture

The authorization model can be represented as:

```text
User
 │
 ├── Position
 │
 ├── Groups
 │    └── Permissions
 │
 └── Custom Permissions
```

Forms contain permissions:

```text
Form
 ├── Permission
 ├── Permission
 ├── Permission
 └── Permission
```

This provides both group-based and user-specific authorization.

---

## Requirements

Before running the application, make sure the following are installed:

* JDK 21
* Maven
* Microsoft SQL Server
* SQL Server Management Studio or another SQL Server client
* Git

---

## Installation

### Clone the Repository

```bash
git clone https://github.com/programsfuture/user-management-system.git
cd user-management-system
```

### Setup the Database

Run:

```text
Database/database-setup.sql
```

using SQL Server.

### Configure Database Connection

Configure the SQL Server connection through the application's database configuration.

### Build

```bash
mvn clean package
```

### Run

```bash
mvn exec:java
```

Main class:

```text
com.programsfuture.usermanagement.UserManagementSystem
```

---

## Project Structure

```text
user-management-system/
│
├── Database/
│   └── database-setup.sql
│
├── lib/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/programsfuture/usermanagement/
│   │   │       ├── dao/
│   │   │       ├── data/
│   │   │       ├── model/
│   │   │       ├── security/
│   │   │       └── ui/
│   │   └── resources/
│   │
│   └── test/
│
├── pom.xml
└── nbactions.xml
```

---

## Security Components

```text
security/
├── PasswordHasher.java
└── PermissionManager.java
```

* `PasswordHasher` handles password hashing.
* `PermissionManager` handles authorization and permission checks.

---

## Why This Project?

The project is designed as a reusable foundation rather than only a simple CRUD application.

Its main strength is the combination of:

```text
User Management
       +
Groups
       +
Positions
       +
Forms
       +
Permissions
       +
Custom Permissions
       +
Authentication
       +
SQL Server
```

This makes it suitable for integrating into larger Java desktop applications that require centralized user and access management.

---

## License

Add the project's license information here if a license is added to the repository.
