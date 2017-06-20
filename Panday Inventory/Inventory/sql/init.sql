DROP DATABASE pmic_inventory;
CREATE DATABASE pmic_inventory;
USE pmic_inventory;

-- all deletion of foreign keys is assumed to be restricted
-- Some tables are created in a certain order in order to avoid 
-- having some foreign keys point to uninitialized primary keys

CREATE TABLE PRODUCT(
	product_serial_no VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY DEFAULT 0,
	brand VARCHAR(255) NOT NULL,
	model VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL,
	price DOUBLE NOT NULL
);
CREATE TABLE INVENTORY(
	product_serial_no VARCHAR(255) NOT NULL,
	date_ordered DATE,
	date_received DATE,
	date_released DATE,
	FOREIGN KEY (product_serial_no)
	REFERENCES PRODUCT (product_serial_no)
	ON DELETE RESTRICT
);
