/* =========================================================
   USER MANAGEMENT SYSTEM
   Complete SQL Server Database Setup
   ========================================================= */

USE master;
GO

/* ---------------------------------------------------------
   1. DROP OLD DATABASE
   WARNING: This permanently deletes the existing database.
   --------------------------------------------------------- */

IF DB_ID(N'UserManagementSystem') IS NOT NULL
BEGIN
    ALTER DATABASE [UserManagementSystem]
    SET SINGLE_USER WITH ROLLBACK IMMEDIATE;

    DROP DATABASE [UserManagementSystem];
END
GO

/* ---------------------------------------------------------
   2. CREATE DATABASE
   No MDF/LDF machine-specific paths are used.
   SQL Server chooses the default database file locations.
   --------------------------------------------------------- */

CREATE DATABASE [UserManagementSystem];
GO

USE [UserManagementSystem];
GO

/* =========================================================
   3. TABLES
   ========================================================= */

/* ---------------------------------------------------------
   Forms
   --------------------------------------------------------- */

CREATE TABLE dbo.Forms
(
    Id INT IDENTITY(1,1) NOT NULL,
    FormName NVARCHAR(150) NOT NULL,
    DisplayName NVARCHAR(150) NOT NULL,
    IsActive BIT NOT NULL
        CONSTRAINT DF_Forms_IsActive DEFAULT (1),

    CONSTRAINT PK_Forms PRIMARY KEY (Id),
    CONSTRAINT UQ_Forms_FormName UNIQUE (FormName)
);
GO

/* ---------------------------------------------------------
   Permissions
   --------------------------------------------------------- */

CREATE TABLE dbo.Permissions
(
    Id INT IDENTITY(1,1) NOT NULL,
    PermissionName NVARCHAR(100) NOT NULL,
    DisplayName NVARCHAR(150) NOT NULL,
    IsActive BIT NOT NULL
        CONSTRAINT DF_Permissions_IsActive DEFAULT (1),

    CONSTRAINT PK_Permissions PRIMARY KEY (Id),
    CONSTRAINT UQ_Permissions_PermissionName UNIQUE (PermissionName)
);
GO

/* ---------------------------------------------------------
   Positions
   --------------------------------------------------------- */

CREATE TABLE dbo.Positions
(
    PositionId INT IDENTITY(1,1) NOT NULL,
    PositionName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(500) NULL,
    IsActive BIT NOT NULL
        CONSTRAINT DF_Positions_IsActive DEFAULT (1),

    CONSTRAINT PK_Positions PRIMARY KEY (PositionId),
    CONSTRAINT UQ_Positions_PositionName UNIQUE (PositionName)
);
GO

/* ---------------------------------------------------------
   Groups
   --------------------------------------------------------- */

CREATE TABLE dbo.Groups
(
    Id INT IDENTITY(1,1) NOT NULL,
    Name NVARCHAR(100) NOT NULL,
    Description NVARCHAR(500) NULL,
    IsActive BIT NOT NULL
        CONSTRAINT DF_Groups_IsActive DEFAULT (1),

    CONSTRAINT PK_Groups PRIMARY KEY (Id),
    CONSTRAINT UQ_Groups_Name UNIQUE (Name)
);
GO

/* ---------------------------------------------------------
   Users
   --------------------------------------------------------- */

CREATE TABLE dbo.Users
(
    Id INT IDENTITY(1,1) NOT NULL,
    FirstName NVARCHAR(100) NOT NULL,
    LastName NVARCHAR(100) NOT NULL,
    Username NVARCHAR(100) NOT NULL,
    PasswordHash NVARCHAR(255) NOT NULL,
    IsActive BIT NOT NULL
        CONSTRAINT DF_Users_IsActive DEFAULT (1),
    PositionId INT NULL,

    CONSTRAINT PK_Users PRIMARY KEY (Id),
    CONSTRAINT UQ_Users_Username UNIQUE (Username),
    CONSTRAINT FK_Users_Positions
        FOREIGN KEY (PositionId)
        REFERENCES dbo.Positions(PositionId)
);
GO

/* ---------------------------------------------------------
   UserGroups
   No unnecessary surrogate Id.
   The natural key (UserId, GroupId) is the primary key.
   --------------------------------------------------------- */

CREATE TABLE dbo.UserGroups
(
    UserId INT NOT NULL,
    GroupId INT NOT NULL,

    CONSTRAINT PK_UserGroups
        PRIMARY KEY (UserId, GroupId),

    CONSTRAINT FK_UserGroups_Users
        FOREIGN KEY (UserId)
        REFERENCES dbo.Users(Id),

    CONSTRAINT FK_UserGroups_Groups
        FOREIGN KEY (GroupId)
        REFERENCES dbo.Groups(Id)
);
GO

/* ---------------------------------------------------------
   GroupFormPermissions
   No unnecessary surrogate Id.
   --------------------------------------------------------- */

CREATE TABLE dbo.GroupFormPermissions
(
    GroupId INT NOT NULL,
    FormId INT NOT NULL,
    PermissionId INT NOT NULL,

    CONSTRAINT PK_GroupFormPermissions
        PRIMARY KEY (GroupId, FormId, PermissionId),

    CONSTRAINT FK_GroupFormPermissions_Groups
        FOREIGN KEY (GroupId)
        REFERENCES dbo.Groups(Id),

    CONSTRAINT FK_GroupFormPermissions_Forms
        FOREIGN KEY (FormId)
        REFERENCES dbo.Forms(Id),

    CONSTRAINT FK_GroupFormPermissions_Permissions
        FOREIGN KEY (PermissionId)
        REFERENCES dbo.Permissions(Id)
);
GO

/* ---------------------------------------------------------
   UserCustomPermissionConfigurations
   --------------------------------------------------------- */

CREATE TABLE dbo.UserCustomPermissionConfigurations
(
    UserId INT NOT NULL,
    UpdatedAt DATETIME2 NOT NULL
        CONSTRAINT DF_UserCustomPermissionConfigurations_UpdatedAt
        DEFAULT (SYSDATETIME()),

    CONSTRAINT PK_UserCustomPermissionConfigurations
        PRIMARY KEY (UserId),

    CONSTRAINT FK_UserCustomPermissionConfigurations_Users
        FOREIGN KEY (UserId)
        REFERENCES dbo.Users(Id)
);
GO

/* ---------------------------------------------------------
   UserCustomPermissions
   No unnecessary surrogate Id.
   --------------------------------------------------------- */

CREATE TABLE dbo.UserCustomPermissions
(
    UserId INT NOT NULL,
    FormId INT NOT NULL,
    PermissionId INT NOT NULL,

    CONSTRAINT PK_UserCustomPermissions
        PRIMARY KEY (UserId, FormId, PermissionId),

    CONSTRAINT FK_UserCustomPermissions_Users
        FOREIGN KEY (UserId)
        REFERENCES dbo.Users(Id),

    CONSTRAINT FK_UserCustomPermissions_Forms
        FOREIGN KEY (FormId)
        REFERENCES dbo.Forms(Id),

    CONSTRAINT FK_UserCustomPermissions_Permissions
        FOREIGN KEY (PermissionId)
        REFERENCES dbo.Permissions(Id)
);
GO

/* ---------------------------------------------------------
   UserFormSettings
   No unnecessary surrogate Id.
   --------------------------------------------------------- */

CREATE TABLE dbo.UserFormSettings
(
    UserId INT NOT NULL,
    FormId INT NOT NULL,
    PageSize INT NOT NULL
        CONSTRAINT DF_UserFormSettings_PageSize DEFAULT (10),

    CONSTRAINT PK_UserFormSettings
        PRIMARY KEY (UserId, FormId),

    CONSTRAINT CK_UserFormSettings_PageSize
        CHECK (PageSize BETWEEN 1 AND 50),

    CONSTRAINT FK_UserFormSettings_Users
        FOREIGN KEY (UserId)
        REFERENCES dbo.Users(Id),

    CONSTRAINT FK_UserFormSettings_Forms
        FOREIGN KEY (FormId)
        REFERENCES dbo.Forms(Id)
);
GO

/* ---------------------------------------------------------
   UserPositionHistory
   --------------------------------------------------------- */

CREATE TABLE dbo.UserPositionHistory
(
    Id INT IDENTITY(1,1) NOT NULL,
    UserId INT NOT NULL,
    OldPositionId INT NULL,
    NewPositionId INT NULL,
    ChangedByUserId INT NOT NULL,
    ChangedDateShamsi NVARCHAR(30) NOT NULL,

    CONSTRAINT PK_UserPositionHistory
        PRIMARY KEY (Id),

    CONSTRAINT FK_UserPositionHistory_User
        FOREIGN KEY (UserId)
        REFERENCES dbo.Users(Id),

    CONSTRAINT FK_UserPositionHistory_ChangedByUser
        FOREIGN KEY (ChangedByUserId)
        REFERENCES dbo.Users(Id),

    CONSTRAINT FK_UserPositionHistory_OldPosition
        FOREIGN KEY (OldPositionId)
        REFERENCES dbo.Positions(PositionId),

    CONSTRAINT FK_UserPositionHistory_NewPosition
        FOREIGN KEY (NewPositionId)
        REFERENCES dbo.Positions(PositionId)
);
GO

/* =========================================================
   4. BASE DATA
   ========================================================= */

/* ---------------------------------------------------------
   Forms
   IDs are explicitly fixed because the Java application
   currently uses these FormId values.
   --------------------------------------------------------- */

SET IDENTITY_INSERT dbo.Forms ON;
GO

INSERT INTO dbo.Forms
(
    Id,
    FormName,
    DisplayName,
    IsActive
)
VALUES
(1, N'PositionsForm',      N'Positions',        1),
(2, N'UserManagementForm', N'User Management',  1),
(3, N'PermissionsForm',    N'Permissions',      1),
(4, N'GroupsForm',         N'Groups',           1);
GO

SET IDENTITY_INSERT dbo.Forms OFF;
GO

/* ---------------------------------------------------------
   Permissions
   IDs are explicitly fixed because the Java application
   currently uses these PermissionId values.
   --------------------------------------------------------- */

SET IDENTITY_INSERT dbo.Permissions ON;
GO

INSERT INTO dbo.Permissions
(
    Id,
    PermissionName,
    DisplayName,
    IsActive
)
VALUES
(1, N'View',   N'View',   1),
(2, N'Save',   N'Save',   1),
(3, N'Update', N'Update', 1),
(4, N'Delete', N'Delete', 1);
GO

SET IDENTITY_INSERT dbo.Permissions OFF;
GO

/* ---------------------------------------------------------
   Administrator Group
   --------------------------------------------------------- */

INSERT INTO dbo.Groups
(
    Name,
    Description,
    IsActive
)
VALUES
(
    N'Administrators',
    N'Full access to all forms and permissions',
    1
);
GO

/* =========================================================
   5. ADMIN USER
   Username: admin
   Password: admin
   ========================================================= */

/*
   PBKDF2WithHmacSHA256
   Iterations: 120000
   Salt: 740+AC9sp2uGROtUiVK7/A==
   Hash: /x8mZvyzKYRp76TXXM4ljl13w6Q+1fGUoG7YeCQl03g=
*/

INSERT INTO dbo.Users
(
    FirstName,
    LastName,
    Username,
    PasswordHash,
    IsActive,
    PositionId
)
VALUES
(
    N'System',
    N'Administrator',
    N'admin',
    N'PBKDF2$120000$740+AC9sp2uGROtUiVK7/A==$/x8mZvyzKYRp76TXXM4ljl13w6Q+1fGUoG7YeCQl03g=',
    1,
    NULL
);
GO

/* =========================================================
   6. ADD ADMIN TO ADMINISTRATORS GROUP
   ========================================================= */

INSERT INTO dbo.UserGroups
(
    UserId,
    GroupId
)
SELECT
    U.Id,
    G.Id
FROM dbo.Users U
CROSS JOIN dbo.Groups G
WHERE U.Username = N'admin'
  AND G.Name = N'Administrators';
GO

/* =========================================================
   7. GIVE ADMIN ALL FORM/PERMISSION COMBINATIONS
   ========================================================= */

INSERT INTO dbo.GroupFormPermissions
(
    GroupId,
    FormId,
    PermissionId
)
SELECT
    G.Id,
    F.Id,
    P.Id
FROM dbo.Groups G
CROSS JOIN dbo.Forms F
CROSS JOIN dbo.Permissions P
WHERE G.Name = N'Administrators'
  AND F.IsActive = 1
  AND P.IsActive = 1;
GO

/* =========================================================
   8. OPTIONAL: VERIFY THE RESULT
   ========================================================= */

SELECT
    Id,
    FormName,
    DisplayName,
    IsActive
FROM dbo.Forms
ORDER BY Id;
GO

SELECT
    Id,
    PermissionName,
    DisplayName,
    IsActive
FROM dbo.Permissions
ORDER BY Id;
GO

SELECT
    Id,
    Name,
    Description,
    IsActive
FROM dbo.Groups
ORDER BY Id;
GO

SELECT
    Id,
    FirstName,
    LastName,
    Username,
    IsActive,
    PositionId
FROM dbo.Users
ORDER BY Id;
GO

SELECT
    UG.UserId,
    U.Username,
    UG.GroupId,
    G.Name AS GroupName
FROM dbo.UserGroups UG
INNER JOIN dbo.Users U ON U.Id = UG.UserId
INNER JOIN dbo.Groups G ON G.Id = UG.GroupId
ORDER BY U.Username, G.Name;
GO

SELECT
    G.Name AS GroupName,
    F.DisplayName AS FormName,
    P.DisplayName AS PermissionName
FROM dbo.GroupFormPermissions GFP
INNER JOIN dbo.Groups G ON G.Id = GFP.GroupId
INNER JOIN dbo.Forms F ON F.Id = GFP.FormId
INNER JOIN dbo.Permissions P ON P.Id = GFP.PermissionId
WHERE G.Name = N'Administrators'
ORDER BY F.Id, P.Id;
GO