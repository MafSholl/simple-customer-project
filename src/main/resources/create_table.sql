
DROP DATABASE SimpleCustomer;

IF OBJECT_ID(N'SimpleCustomer', N'D') IS NULL
    CREATE DATABASE SimpleCustomer;

USE SimpleCustomer;


IF OBJECT_ID(N'roles', N'U') IS NULL
    CREATE TABLE role (
        id INT PRIMARY KEY,
        role VARCHAR(20) NOT NULL
    );


IF OBJECT_ID(N'User', N'U') IS NULL
        CREATE TABLE Customer (
            id INT NOT NULL IDENTITY(1, 1),
            firstName VARCHAR(255) NOT NULL,
            lastName VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL UNIQUE,
            password VARCHAR(255),
            role VARCHAR(255) NOT NULL,
            billing_id INT NULL UNIQUE,
            CONSTRAINT CustomerPk PRIMARY KEY(id),
            CONSTRAINT CustomerAk UNIQUE(email),
        );


IF OBJECT_ID(N'billing_details', N'U') IS NULL
    CREATE TABLE billing_details (
        id INT NOT NULL CONSTRAINT BillingDetails_PK PRIMARY KEY IDENTITY(100, 5),
        accountNumber VARCHAR(15),
        tariff INT NULL DEFAULT 0,
        customer_id INT,
        CONSTRAINT Customer_FK FOREIGN KEY (customer_id)
            REFERENCES Customer(id)
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
    );

IF OBJECT_ID(N'User', N'U') IS NOT NULL
        ALTER TABLE Customer
            ADD CONSTRAINT BillingDetails_Fk FOREIGN KEY (billing_id)
                REFERENCES billing_details(id)
                ON UPDATE CASCADE
                ON DELETE CASCADE;

