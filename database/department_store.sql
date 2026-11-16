-- Co so du lieu cua he thong quan ly cua hang bach hoa truc tuyen (de 37)
-- 11 bang theo bieu do thiet ke CSDL. Chay file nay trong MySQL de tao CSDL va du lieu mau.

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS department_store;
CREATE DATABASE department_store CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE department_store;

CREATE TABLE tblMember0 (
    id INTEGER(10) NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    fullName VARCHAR(255),
    address VARCHAR(255),
    dateOfBirth DATE,
    email VARCHAR(255),
    phoneNumber VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE (username)
);

CREATE TABLE tblCustomer0 (
    tblMember0id INTEGER(10) NOT NULL,
    customerCode VARCHAR(255),
    PRIMARY KEY (tblMember0id),
    FOREIGN KEY (tblMember0id) REFERENCES tblMember0(id)
);

CREATE TABLE tblStaff0 (
    tblMember0id INTEGER(10) NOT NULL,
    staffCode VARCHAR(255),
    jobPosition VARCHAR(255),
    PRIMARY KEY (tblMember0id),
    FOREIGN KEY (tblMember0id) REFERENCES tblMember0(id)
);

CREATE TABLE tblSupplier0 (
    id INTEGER(10) NOT NULL AUTO_INCREMENT,
    supplierName VARCHAR(255),
    address VARCHAR(255),
    phoneNumber VARCHAR(255),
    email VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE tblManufacturer0 (
    id INTEGER(10) NOT NULL AUTO_INCREMENT,
    manufacturerName VARCHAR(255),
    country VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE tblCommodity0 (
    id INTEGER(10) NOT NULL AUTO_INCREMENT,
    commodityName VARCHAR(255),
    description VARCHAR(255),
    category VARCHAR(255),
    image VARCHAR(255),
    purchasePrice FLOAT(10),
    salePrice FLOAT(10),
    stockQuantity INTEGER(10),
    tblManufacturer0id INTEGER(10) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tblManufacturer0id) REFERENCES tblManufacturer0(id)
);

CREATE TABLE tblOrder0 (
    id INTEGER(10) NOT NULL AUTO_INCREMENT,
    orderDate DATE,
    shippingAddress VARCHAR(255),
    cancellationReason VARCHAR(255),
    tblCustomer0id INTEGER(10) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tblCustomer0id) REFERENCES tblCustomer0(tblMember0id)
);

CREATE TABLE tblOrderDetail0 (
    id INTEGER(10) NOT NULL AUTO_INCREMENT,
    quantity INTEGER(10),
    unitPrice FLOAT(10),
    tblOrder0id INTEGER(10) NOT NULL,
    tblCommodity0id INTEGER(10) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tblOrder0id) REFERENCES tblOrder0(id),
    FOREIGN KEY (tblCommodity0id) REFERENCES tblCommodity0(id)
);

CREATE TABLE tblInvoice0 (
    id INTEGER(10) NOT NULL AUTO_INCREMENT,
    issueDate DATE,
    status VARCHAR(255),
    tblOrder0id INTEGER(10) NOT NULL,
    saleStaffid INTEGER(10),
    deliveryStaffid INTEGER(10),
    PRIMARY KEY (id),
    UNIQUE (tblOrder0id),
    FOREIGN KEY (tblOrder0id) REFERENCES tblOrder0(id),
    FOREIGN KEY (saleStaffid) REFERENCES tblStaff0(tblMember0id),
    FOREIGN KEY (deliveryStaffid) REFERENCES tblStaff0(tblMember0id)
);

CREATE TABLE tblImportReceipt0 (
    id INTEGER(10) NOT NULL AUTO_INCREMENT,
    importDate DATE,
    status VARCHAR(255),
    tblSupplier0id INTEGER(10) NOT NULL,
    managementStaffid INTEGER(10) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tblSupplier0id) REFERENCES tblSupplier0(id),
    FOREIGN KEY (managementStaffid) REFERENCES tblStaff0(tblMember0id)
);

CREATE TABLE tblImportReceiptDetail0 (
    id INTEGER(10) NOT NULL AUTO_INCREMENT,
    quantity INTEGER(10),
    purchasePrice FLOAT(10),
    tblImportReceipt0id INTEGER(10) NOT NULL,
    tblCommodity0id INTEGER(10) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tblImportReceipt0id) REFERENCES tblImportReceipt0(id),
    FOREIGN KEY (tblCommodity0id) REFERENCES tblCommodity0(id)
);

-- ---------------------------------------------------------------
-- Du lieu mau, khop voi vi du trong kich ban cua hai module
-- ---------------------------------------------------------------

INSERT INTO tblMember0 (id, username, password, fullName, address) VALUES
(1, 'khachhang', '123456', 'Nguyễn Văn A', 'Km10 Nguyễn Trãi, Hà Đông, Hà Nội'),
(2, 'quanly', '123456', 'Lê Văn Q', NULL),
(3, 'banhang', '123456', 'Trần Thị B', NULL),
(4, 'giaohang', '123456', 'Phạm Văn G', NULL);

INSERT INTO tblCustomer0 (tblMember0id, customerCode) VALUES
(1, 'KH001');

INSERT INTO tblStaff0 (tblMember0id, staffCode, jobPosition) VALUES
(2, 'NV001', 'Nhân viên quản lý'),
(3, 'NV002', 'Nhân viên bán hàng'),
(4, 'NV003', 'Nhân viên giao hàng');

INSERT INTO tblSupplier0 (id, supplierName) VALUES
(1, 'Nhà cung cấp A'),
(2, 'Nhà cung cấp B');

INSERT INTO tblManufacturer0 (id, manufacturerName, country) VALUES
(1, 'Nhà sản xuất S', 'Việt Nam'),
(2, 'Nhà sản xuất G', 'Việt Nam');

-- Ton kho = tong nhap - tong da dat: sua 550 - 15 = 535, gao 100 - 10 = 90
INSERT INTO tblCommodity0 (id, commodityName, description, category, image, purchasePrice, salePrice, stockQuantity, tblManufacturer0id) VALUES
(1, 'Sữa tươi 1 lít', 'Sữa tươi tiệt trùng hộp 1 lít', 'Sữa', 'images/sua_tuoi.png', 20000, 30000, 535, 1),
(2, 'Gạo ST25 túi 5 kg', 'Gạo ST25 đóng túi 5 kg', 'Gạo', 'images/gao_st25.png', 120000, 150000, 90, 2);

-- Phieu nhap: nha cung cap A co 2 phieu (10.000.000 va 5.000.000), nha cung cap B co 1 phieu (8.000.000)
INSERT INTO tblImportReceipt0 (id, importDate, status, tblSupplier0id, managementStaffid) VALUES
(1, '2026-10-05', 'Đã nhập kho', 1, 2),
(2, '2026-10-20', 'Đã nhập kho', 1, 2),
(3, '2026-10-15', 'Đã nhập kho', 2, 2);

INSERT INTO tblImportReceiptDetail0 (quantity, purchasePrice, tblImportReceipt0id, tblCommodity0id) VALUES
(200, 20000, 1, 1),
(50, 120000, 1, 2),
(250, 20000, 2, 1),
(50, 120000, 3, 2),
(100, 20000, 3, 1);

-- Don hang va hoa don cua khach hang A
INSERT INTO tblOrder0 (id, orderDate, shippingAddress, cancellationReason, tblCustomer0id) VALUES
(101, '2026-10-28', 'Km10 Nguyễn Trãi, Hà Đông, Hà Nội', NULL, 1),
(102, '2026-11-05', 'Km10 Nguyễn Trãi, Hà Đông, Hà Nội', NULL, 1),
(103, '2026-11-12', 'Km10 Nguyễn Trãi, Hà Đông, Hà Nội', NULL, 1);

INSERT INTO tblOrderDetail0 (quantity, unitPrice, tblOrder0id, tblCommodity0id) VALUES
(10, 30000, 101, 1),
(8, 150000, 102, 2),
(5, 30000, 103, 1),
(2, 150000, 103, 2);

INSERT INTO tblInvoice0 (id, issueDate, status, tblOrder0id, saleStaffid, deliveryStaffid) VALUES
(101, '2026-10-28', 'Đã giao', 101, 3, 4),
(102, '2026-11-05', 'Đang giao', 102, 3, 4),
(103, '2026-11-12', 'Chờ xử lý', 103, NULL, NULL);
