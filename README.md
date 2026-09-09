# User Management System

سیستم مدیریت کاربران دسکتاپ با Java Swing و Microsoft SQL Server، با پشتیبانی از مدیریت کاربران، گروه‌ها، سمت‌ها، مجوزها و تنظیمات دسترسی.

A desktop User Management System built with Java Swing and Microsoft SQL Server, supporting user, group, position, permission, and access-control management.

نظام سطح المكتب لإدارة المستخدمين، تم تطويره باستخدام Java Swing وMicrosoft SQL Server، ويدعم إدارة المستخدمين والمجموعات والمناصب والصلاحيات والتحكم في الوصول.

---

## زبان‌ها | Languages | اللغات

* [فارسی](#فارسی)
* [English](#english)
* [العربية](#العربية)

---

# فارسی

## معرفی

**User Management System** یک نرم‌افزار دسکتاپ برای مدیریت کاربران و کنترل دسترسی است که با **Java 21**، **Java Swing** و **Microsoft SQL Server** توسعه داده شده است.

این پروژه امکاناتی برای مدیریت کاربران، گروه‌ها، سمت‌ها و مجوزها فراهم می‌کند و از سیستم‌های مختلف برای کنترل دسترسی کاربران پشتیبانی می‌کند.

## امکانات

* ورود کاربران به سیستم
* مدیریت کاربران
* ایجاد، ویرایش و حذف کاربران
* فعال یا غیرفعال کردن کاربران
* جستجوی کاربران
* صفحه‌بندی لیست کاربران
* انتخاب تعداد رکورد در هر صفحه
* مدیریت Groups
* مدیریت Positions
* مدیریت Permissions
* تخصیص کاربران به گروه‌ها
* تخصیص مجوزهای سفارشی به کاربران
* مدیریت مجوزهای گروه‌ها
* مدیریت تنظیمات فرم‌های کاربران
* ثبت تاریخچه سمت کاربران
* مدیریت رمز عبور با Password Hashing
* کنترل دسترسی بر اساس Permission
* تنظیم اتصال به SQL Server از داخل برنامه
* تست اتصال به دیتابیس
* پشتیبانی از تغییر Theme رابط کاربری

## تکنولوژی‌ها

* Java 21
* Java Swing
* Maven
* Microsoft SQL Server
* JDBC
* FlatLaf
* SwingX
* JUnit

## ساختار پروژه

```text
UserManagementSystem/
│
├── Database/
│   └── database-setup.sql
│
├── lib/
│   ├── JavaDate 1.3.jar
│   ├── icu4j-57_1.jar
│   ├── swingx-all-1.6.5-1.jar
│   └── swingx-beaninfo-1.6.5-1.jar
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/programsfuture/usermanagement/
│   │           ├── dao/
│   │           ├── data/
│   │           ├── model/
│   │           ├── security/
│   │           └── ui/
│   │
│   └── test/
│
├── pom.xml
├── .gitignore
└── README.md
```

## پیش‌نیازها

برای اجرای پروژه موارد زیر مورد نیاز است:

* Java JDK 21 یا بالاتر
* Maven
* Microsoft SQL Server
* دسترسی به یک SQL Server فعال
* Git، در صورت دریافت پروژه از Repository

## نصب و اجرا

### 1. دریافت پروژه

Repository را Clone کنید:

```bash
git clone https://github.com/programsfuture/user-management-system.git
cd user-management-system
```

### 2. ایجاد دیتابیس

فایل زیر را پیدا کنید:

```text
Database/database-setup.sql
```

این فایل را با SQL Server Management Studio یا ابزار مناسب SQL Server اجرا کنید.

این Script دیتابیس زیر را ایجاد می‌کند:

```text
UserManagementSystem
```

و جداول مورد نیاز سیستم را ایجاد و داده‌های اولیه را وارد می‌کند.

> **هشدار:** اسکریپت `database-setup.sql` در ابتدای اجرا، در صورت وجود دیتابیس `UserManagementSystem` آن را حذف و مجدداً ایجاد می‌کند. بنابراین آن را روی دیتابیسی که حاوی اطلاعات مهم است اجرا نکنید.

### 3. تنظیم اتصال دیتابیس

پس از اجرای برنامه، تنظیمات اتصال به SQL Server را از طریق بخش **Database Settings** در خود برنامه انجام دهید.

اطلاعات مورد نیاز:

* **Server:** نام یا IP سرور SQL Server
* **Port:** پورت SQL Server، معمولاً `1433`
* **Database:** نام دیتابیس، به‌صورت پیش‌فرض `UserManagementSystem`
* **Username:** نام کاربری SQL Server
* **Password:** رمز عبور SQL Server

پس از وارد کردن اطلاعات، از گزینه **Test Connection** برای بررسی اتصال استفاده کنید.

تنظیمات اتصال توسط برنامه ذخیره می‌شوند و برای اتصال‌های بعدی مورد استفاده قرار می‌گیرند.

> **نکته:** قبل از تنظیم اتصال، ابتدا فایل `Database/database-setup.sql` را روی SQL Server اجرا کنید تا دیتابیس و جداول مورد نیاز برنامه ایجاد شوند.

### 4. اجرای پروژه با Maven

برای Build کردن پروژه:

```bash
mvn clean install
```

برای اجرای برنامه:

```bash
mvn exec:java
```

> در صورت استفاده از IDE می‌توانید کلاس اصلی زیر را اجرا کنید:
>
> `com.programsfuture.usermanagement.UserManagementSystem`

## اطلاعات ورود پیش‌فرض

پس از اجرای `database-setup.sql`، یک کاربر پیش‌فرض با اطلاعات زیر ایجاد می‌شود:

```text
Username: admin
Password: admin
```

این کاربر در گروه **Administrators** قرار دارد و مجوزهای مدیریتی اولیه را دریافت می‌کند.

> **توجه:** این اطلاعات برای اجرای اولیه و تست پروژه در نظر گرفته شده‌اند.

## دیتابیس

نام دیتابیس پیش‌فرض:

```text
UserManagementSystem
```

برخی از جداول اصلی:

* `Forms`
* `Permissions`
* `Positions`
* `Groups`
* `Users`
* `UserGroups`
* `GroupFormPermissions`
* `UserCustomPermissionConfigurations`
* `UserCustomPermissions`
* `UserFormSettings`
* `UserPositionHistory`

## سیستم مجوزها

سیستم دسترسی پروژه بر اساس Permission طراحی شده است.

مجوزها می‌توانند از طریق گروه‌ها یا به‌صورت سفارشی برای کاربران تنظیم شوند.

بخش‌های اصلی سیستم مجوز شامل موارد زیر است:

* Group Permissions
* User Custom Permissions
* Form Permissions
* Permission Management

کلاس `PermissionManager` مسئول بررسی دسترسی‌های مربوط به کاربر در بخش‌های مختلف برنامه است.

## امنیت رمز عبور

رمزهای عبور کاربران به‌صورت Plain Text در منطق احراز هویت استفاده نمی‌شوند و پروژه از کلاس `PasswordHasher` برای Hash کردن رمزهای عبور استفاده می‌کند.

## تست

برای اجرای تست‌های پروژه:

```bash
mvn test
```

## توسعه

ساختار پروژه به بخش‌های مختلف تقسیم شده است:

* `dao` — دسترسی به داده‌ها و عملیات دیتابیس
* `data` — تنظیمات و سرویس‌های مرتبط با داده و دیتابیس
* `model` — مدل‌های داده
* `security` — احراز هویت، Hash کردن رمز عبور و Permission Management
* `ui` — رابط کاربری Java Swing

## فرم‌های اصلی برنامه

* Database Settings
* Login
* Main
* User Management
* Groups
* Positions
* Permissions

## وضعیت پروژه

این پروژه یک **Java Desktop Application** است که برای مدیریت کاربران و کنترل دسترسی طراحی شده است.

---

# English

## Overview

**User Management System** is a desktop application for user management and access control, developed using **Java 21**, **Java Swing**, and **Microsoft SQL Server**.

The project provides functionality for managing users, groups, positions, and permissions, with support for role/group-based and custom user permissions.

## Features

* User authentication
* User management
* Create, update, and delete users
* Enable or disable users
* User search
* User list pagination
* Configurable page size
* Group management
* Position management
* Permission management
* Assign users to groups
* Custom user permissions
* Group-based permissions
* User form settings
* User position history
* Password hashing
* Permission-based access control
* SQL Server connection configuration
* Database connection testing
* UI theme support

## Technologies

* Java 21
* Java Swing
* Maven
* Microsoft SQL Server
* JDBC
* FlatLaf
* SwingX
* JUnit

## Project Structure

```text
UserManagementSystem/
│
├── Database/
│   └── database-setup.sql
│
├── lib/
│   ├── JavaDate 1.3.jar
│   ├── icu4j-57_1.jar
│   ├── swingx-all-1.6.5-1.jar
│   └── swingx-beaninfo-1.6.5-1.jar
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/programsfuture/usermanagement/
│   │           ├── dao/
│   │           ├── data/
│   │           ├── model/
│   │           ├── security/
│   │           └── ui/
│   │
│   └── test/
│
├── pom.xml
├── .gitignore
└── README.md
```

## Requirements

The following software is required:

* Java JDK 21 or later
* Maven
* Microsoft SQL Server
* Access to a running SQL Server instance
* Git, if cloning the repository

## Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/programsfuture/user-management-system.git
cd user-management-system
```

### 2. Create the Database

Locate the following file:

```text
Database/database-setup.sql
```

Run it using SQL Server Management Studio or another compatible SQL Server tool.

The script creates the following database:

```text
UserManagementSystem
```

It also creates the required tables and inserts the initial data.

> **Warning:** At the beginning of its execution, `database-setup.sql` drops the `UserManagementSystem` database if it already exists and then recreates it. Do not run this script against a database containing important data.

### 3. Configure the Database Connection

After launching the application, configure the SQL Server connection through the **Database Settings** section.

Required information:

* **Server:** SQL Server server name or IP address
* **Port:** SQL Server port, usually `1433`
* **Database:** Database name, by default `UserManagementSystem`
* **Username:** SQL Server username
* **Password:** SQL Server password

After entering the connection details, use **Test Connection** to verify the connection.

The application stores the connection settings and uses them for subsequent database connections.

> **Note:** Run `Database/database-setup.sql` before configuring the connection so that the required database and tables are available.

### 4. Run with Maven

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn exec:java
```

> You can also run the following main class directly from your IDE:
>
> `com.programsfuture.usermanagement.UserManagementSystem`

## Default Login

After running `database-setup.sql`, a default user is created:

```text
Username: admin
Password: admin
```

The user belongs to the **Administrators** group and receives the initial administrative permissions.

> **Note:** These credentials are provided for initial project setup and testing.

## Database

Default database name:

```text
UserManagementSystem
```

Main database tables include:

* `Forms`
* `Permissions`
* `Positions`
* `Groups`
* `Users`
* `UserGroups`
* `GroupFormPermissions`
* `UserCustomPermissionConfigurations`
* `UserCustomPermissions`
* `UserFormSettings`
* `UserPositionHistory`

## Permission System

The application uses a permission-based access-control system.

Permissions can be assigned through groups or configured as custom permissions for individual users.

The main permission-related components include:

* Group Permissions
* User Custom Permissions
* Form Permissions
* Permission Management

The `PermissionManager` class is responsible for checking user permissions across the application.

## Password Security

User passwords are not handled as plain-text values by the authentication logic. The project uses the `PasswordHasher` class to hash user passwords.

## Testing

Run the test suite with:

```bash
mvn test
```

## Development

The project is organized into several main packages:

* `dao` — Database access and data operations
* `data` — Data and database-related configuration/services
* `model` — Data models
* `security` — Authentication, password hashing, and permission management
* `ui` — Java Swing user interface

## Main Application Forms

* Database Settings
* Login
* Main
* User Management
* Groups
* Positions
* Permissions

## Project Status

This project is a **Java Desktop Application** designed for user management and access control.

---

# العربية

## نظرة عامة

**User Management System** هو تطبيق سطح مكتب لإدارة المستخدمين والتحكم في الوصول، تم تطويره باستخدام **Java 21** و**Java Swing** و**Microsoft SQL Server**.

يوفر المشروع وظائف لإدارة المستخدمين والمجموعات والمناصب والصلاحيات، مع دعم التحكم في الوصول باستخدام صلاحيات المجموعات والصلاحيات المخصصة للمستخدمين.

## الميزات

* تسجيل دخول المستخدمين
* إدارة المستخدمين
* إنشاء المستخدمين وتعديلهم وحذفهم
* تفعيل المستخدمين أو تعطيلهم
* البحث عن المستخدمين
* تقسيم قائمة المستخدمين إلى صفحات
* تحديد عدد السجلات في كل صفحة
* إدارة المجموعات
* إدارة المناصب
* إدارة الصلاحيات
* إضافة المستخدمين إلى المجموعات
* تخصيص صلاحيات للمستخدمين
* إدارة صلاحيات المجموعات
* إعدادات نماذج المستخدمين
* حفظ سجل المناصب للمستخدمين
* تشفير/تجزئة كلمات المرور
* التحكم في الوصول بناءً على الصلاحيات
* إعداد اتصال Microsoft SQL Server
* اختبار اتصال قاعدة البيانات
* دعم تغيير مظهر واجهة المستخدم

## التقنيات المستخدمة

* Java 21
* Java Swing
* Maven
* Microsoft SQL Server
* JDBC
* FlatLaf
* SwingX
* JUnit

## هيكل المشروع

```text
UserManagementSystem/
│
├── Database/
│   └── database-setup.sql
│
├── lib/
│   ├── JavaDate 1.3.jar
│   ├── icu4j-57_1.jar
│   ├── swingx-all-1.6.5-1.jar
│   └── swingx-beaninfo-1.6.5-1.jar
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/programsfuture/usermanagement/
│   │           ├── dao/
│   │           ├── data/
│   │           ├── model/
│   │           ├── security/
│   │           └── ui/
│   │
│   └── test/
│
├── pom.xml
├── .gitignore
└── README.md
```

## المتطلبات

لتشغيل المشروع تحتاج إلى:

* Java JDK 21 أو إصدار أحدث
* Maven
* Microsoft SQL Server
* خادم SQL Server يعمل ويمكن الوصول إليه
* Git، في حال تنزيل المشروع من Repository

## التثبيت والإعداد

### 1. تنزيل المشروع

قم باستنساخ Repository:

```bash
git clone https://github.com/programsfuture/user-management-system.git
cd user-management-system
```

### 2. إنشاء قاعدة البيانات

ابحث عن الملف التالي:

```text
Database/database-setup.sql
```

قم بتشغيله باستخدام SQL Server Management Studio أو أي أداة متوافقة مع SQL Server.

يقوم الملف بإنشاء قاعدة البيانات:

```text
UserManagementSystem
```

كما يقوم بإنشاء الجداول المطلوبة وإدخال البيانات الأولية.

> **تحذير:** في بداية تنفيذ `database-setup.sql`، يتم حذف قاعدة البيانات `UserManagementSystem` إذا كانت موجودة، ثم يتم إنشاؤها من جديد. لذلك لا تقم بتشغيل هذا الملف على قاعدة بيانات تحتوي على بيانات مهمة.

### 3. إعداد اتصال قاعدة البيانات

بعد تشغيل البرنامج، قم بإعداد اتصال SQL Server من خلال قسم **Database Settings** داخل التطبيق.

المعلومات المطلوبة:

* **Server:** اسم خادم SQL Server أو عنوان IP
* **Port:** منفذ SQL Server، وعادةً يكون `1433`
* **Database:** اسم قاعدة البيانات، والقيمة الافتراضية هي `UserManagementSystem`
* **Username:** اسم مستخدم SQL Server
* **Password:** كلمة مرور SQL Server

بعد إدخال معلومات الاتصال، استخدم خيار **Test Connection** للتحقق من الاتصال.

يقوم التطبيق بحفظ إعدادات الاتصال واستخدامها في عمليات الاتصال اللاحقة بقاعدة البيانات.

> **ملاحظة:** يجب تشغيل `Database/database-setup.sql` أولاً حتى يتم إنشاء قاعدة البيانات والجداول المطلوبة.

### 4. تشغيل المشروع باستخدام Maven

لبناء المشروع:

```bash
mvn clean install
```

لتشغيل التطبيق:

```bash
mvn exec:java
```

> يمكن أيضاً تشغيل الكلاس الرئيسي التالي مباشرة من خلال بيئة التطوير IDE:
>
> `com.programsfuture.usermanagement.UserManagementSystem`

## بيانات تسجيل الدخول الافتراضية

بعد تشغيل `database-setup.sql`، يتم إنشاء مستخدم افتراضي:

```text
Username: admin
Password: admin
```

يتم إضافة المستخدم إلى مجموعة **Administrators** ويحصل على الصلاحيات الإدارية الأولية.

> **ملاحظة:** بيانات الدخول هذه مخصصة للإعداد الأولي واختبار المشروع.

## قاعدة البيانات

اسم قاعدة البيانات الافتراضي:

```text
UserManagementSystem
```

تشمل الجداول الرئيسية:

* `Forms`
* `Permissions`
* `Positions`
* `Groups`
* `Users`
* `UserGroups`
* `GroupFormPermissions`
* `UserCustomPermissionConfigurations`
* `UserCustomPermissions`
* `UserFormSettings`
* `UserPositionHistory`

## نظام الصلاحيات

يعتمد التطبيق على نظام للتحكم في الوصول باستخدام الصلاحيات.

يمكن تعيين الصلاحيات من خلال المجموعات أو تخصيصها بشكل منفصل لكل مستخدم.

تشمل الأجزاء الرئيسية لنظام الصلاحيات:

* Group Permissions
* User Custom Permissions
* Form Permissions
* Permission Management

يُستخدم الكلاس `PermissionManager` للتحقق من صلاحيات المستخدم في مختلف أجزاء التطبيق.

## أمان كلمات المرور

لا يعتمد نظام المصادقة على تخزين كلمات المرور كنصوص عادية، ويستخدم المشروع الكلاس `PasswordHasher` لمعالجة تجزئة كلمات المرور.

## الاختبارات

لتشغيل اختبارات المشروع:

```bash
mvn test
```

## التطوير

تم تقسيم المشروع إلى عدة حزم رئيسية:

* `dao` — الوصول إلى قاعدة البيانات وتنفيذ عمليات البيانات
* `data` — إعدادات وخدمات البيانات وقاعدة البيانات
* `model` — نماذج البيانات
* `security` — المصادقة وتجزئة كلمات المرور وإدارة الصلاحيات
* `ui` — واجهة المستخدم المبنية باستخدام Java Swing

## النماذج الرئيسية للتطبيق

* Database Settings
* Login
* Main
* User Management
* Groups
* Positions
* Permissions

## حالة المشروع

هذا المشروع هو **تطبيق سطح مكتب مبني باستخدام Java** ومخصص لإدارة المستخدمين والتحكم في الوصول والصلاحيات.
