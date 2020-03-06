DROP DATABASE IF EXISTS jefrenivi;
CREATE DATABASE jefrenivi;
USE jefrenivi;

DROP TABLE IF EXISTS Categories;
CREATE TABLE Categories (
CategoryID INT PRIMARY KEY AUTO_INCREMENT,
Name VARCHAR(50),
Description VARCHAR(255),
ParentCategoryID int,
FOREIGN KEY (ParentCategoryID) REFERENCES Categories(CategoryID));

INSERT INTO Categories
(Name, Description, ParentCategoryID) VALUES 
('Produce', 'Produce is a generalized term for many farm-produced crops, including fruits and vegetables (grains, oats, etc. are also sometimes considered produce).', null),
('Fruits', 'In botany, a fruit is the seed-bearing structure in flowering plants (also known as angiosperms) formed from the ovary after flowering.', 1),
('Vegetables', 'Vegetables are parts of plants that are consumed by humans or other animals as food.', 1),
('Apples', 'An apple is a sweet, edible fruit produced by an apple tree (Malus domestica).', 2),
('Oranges', 'The orange is the fruit of the citrus species Citrus × sinensis in the family Rutaceae, native to China.', 2),
('Carrots', 'The carrot (Daucus carota subsp. sativus) is a root vegetable, usually orange in colour, though purple, black, red, white, and yellow cultivars exist.', 3),
('Onions', 'The onion (Allium cepa L., from Latin cepa "onion"), also known as the bulb onion or common onion, is a vegetable that is the most widely cultivated species of the genus Allium.', 3),
('Bakery', 'A bakery is an establishment that produces and sells flour-based food baked in an oven such as bread, cookies, cakes, pastries, and pies.', null),
('Breads', 'Bread is a staple food prepared from a dough of flour and water, usually by baking.', 8),
('Fishing', 'Fishing is the activity of trying to catch fish.', null),
('Bait', 'Fishing bait is any substance used to attract and catch fish, e.g. on the end of a fishing hook, or inside a fish trap.', 10),
('Live Bait', 'The natural bait angler, with few exceptions, will use a common prey species of the fish as an attractant.', 11),
('Artificial Bait', 'Using lures is a popular method for catching predatory fish.', 11),
('Fishing Rods', 'A fishing rod is a long, flexible rod used by fishermen to catch fish.', 10);

DROP TABLE IF EXISTS Addresses;
CREATE TABLE Addresses (
AddressID INT PRIMARY KEY AUTO_INCREMENT,
Street VARCHAR(50),
City VARCHAR(50),
State VARCHAR(2),
Zip VARCHAR(10));

INSERT INTO Addresses (Street, City, State, Zip) VALUES
('31615 7th Pl S', 'Federal Way', 'WA', '98003'),
('26110 129th Ave SE', 'Kent', 'WA', 98030),
('17615 SE 272nd St #102', 'Covington', 'WA', '98042'),
('1001 4th Ave', 'Seattle', 'WA', '98154'),
('2001 NW Sammamish Rd', 'Issaquah', 'WA', '98027'),
('4103 2nd Ave S', 'Seattle', 'WA', '98134'),
('1148 Central Ave N', 'Kent', 'WA', '98032'),
('7300 Perimeter Rd S', 'Seattle', 'WA', '98108'),
('651 S Alaska St', 'Seattle', 'WA', '98108'),
('10010 Main St', 'Bothell', 'WA', '98011'),
('1410 NW Gilman Blvd', 'Issaquah', 'WA', '98027');

DROP TABLE IF EXISTS Suppliers;
CREATE TABLE Suppliers (
SupplierID INT PRIMARY KEY AUTO_INCREMENT,
AddressID INT,
Name VARCHAR(50),
ContactName VARCHAR(50),
Phone VARCHAR(14),
FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID));

INSERT INTO Suppliers (AddressID, Name, ContactName, Phone) VALUES
(6, 'Charlie\'s Produce', 'Charlie', '(206) 625-1412'),
(7, 'Carpinito Brothers', 'Suzy', '(253) 854-5692'),
(10, 'Hillcrest Bakery', 'Carl', '(425) 486-5292'),
(11, 'Creekside Angling Co', 'Barbara', '(425) 392-3800');

DROP TABLE IF EXISTS Products;
CREATE TABLE Products (
ProductID VARCHAR(10) PRIMARY KEY,
CategoryID INT,
SupplierID INT,
Name VARCHAR(50),
Price DECIMAL(13, 2),
Weight DECIMAL(10, 4),
Stock INT,
ReorderLevel INT,
FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID),
FOREIGN KEY (SupplierID) REFERENCES Suppliers(SupplierID));

INSERT INTO Products (ProductID, CategoryID, SupplierID, Name, Price, Weight, Stock, ReorderLevel) VALUES
('PRFRAP0001', 4, 1, 'Honeycrisp Apple', 0.99, 0.1, 400, 100),
('PRFRAP0002', 4, 1, 'Fuji Apple', 0.99, 0.1, 175, 60),
('PRVEON0001', 7, 2, 'Yellow Onion', 0.66, 0.15, 70, 45),
('PRVEON0002', 7, 2, 'Sweet Onion', 0.54, 0.125, 120, 50),
('BABREA0001', 9, 3, 'Sourdough Bread', 4.69, 0.680, 30, 12),
('BABREA0002', 9, 3, 'Wheat Bread', 2.79, 0.680, 42, 12),
('BABREA0003', 9, 3, 'French Baguette', 2.89, 0.453, 24, 10),
('FIBALI0001', 12, 4, 'Earthworm', 0.07, 0.001, 5000, 500),
('FIBALI0002', 12, 4, 'Maggot', 0.01, 0.0003, 10000, 1000),
('FIBAAR0001', 13, 4, 'Spoon Lure', 2.59, 0.014, 50, 10),
('FIRODS0001', 14, 4, 'Daiwa Minispin Ultralight Spinning Reel and Rod', 39.18, 0.306, 12, 4),
('FIRODS0002', 14, 4, 'PLUSINNO Fishing Rod and Reel', 39.98, 0.575, 7, 3);

DROP TABLE IF EXISTS Customers;
CREATE TABLE Customers (
CustomerID INT PRIMARY KEY AUTO_INCREMENT,
AddressID INT,
Name VARCHAR(50),
Email VARCHAR(50),
Phone VARCHAR(14),
FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID));

INSERT INTO Customers (AddressID, Name, Email, Phone) VALUES
('1', 'Freddie', 'freddie@email.com', '(555) 555-5555'),
('2', 'Jeff', 'jeff@email.com', '(555) 555-5555'),
('3', 'Vivian', 'vivian@email.com', '(555) 555-5555'),
('4', 'Nicholas', 'nicholas@email.com', '(555) 555-5555');

DROP TABLE IF EXISTS Billables;
CREATE TABLE Billables (
BillableID INT PRIMARY KEY AUTO_INCREMENT,
CustomerID INT,
Name VARCHAR(50),
CardNumber VARCHAR(16),
ExpirationDate DATE,
CVV VARCHAR(3),
FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID));

INSERT INTO Billables (CustomerID, Name, CardNumber, ExpirationDate, CVV) VALUES
(1, 'VISA', '1234567812345678', '2020-12-01', '123'),
(2, 'Debit Card', '4000123456789123', '2021-03-01', '987'),
(3, 'Debit Card', '5412751234567890', '2022-03-01', '372'),
(4, 'American Express', '375987654321001', '2017-10-01', '001');

DROP TABLE IF EXISTS Shippers;
CREATE TABLE Shippers (
ShipperID INT PRIMARY KEY AUTO_INCREMENT,
AddressID INT,
Name VARCHAR(50),
ContactName VARCHAR(50),
Phone VARCHAR(14),
FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID));

INSERT INTO Shippers (AddressID, Name, ContactName, Phone) VALUES
(8, 'UPS', 'Joe', '(206) 763-6429'),
(9, 'FedEx', 'Tiffany', '(800) 463-3339'),
(4, 'USPS', 'Uncle Sam', '(800) 275-8777');

DROP TABLE IF EXISTS Orders;
CREATE TABLE Orders (
OrderID INT PRIMARY KEY AUTO_INCREMENT,
CustomerID INT,
ShipperID INT,
BillableID INT,
Date DATE,
ShipDate DATE,
OpenStatus BOOLEAN,
FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
FOREIGN KEY (ShipperID) REFERENCES Shippers(ShipperID),
FOREIGN KEY (BillableID) REFERENCES Billables(BillableID));

INSERT INTO Orders (CustomerID, ShipperID, BillableID, Date, ShipDate, OpenStatus) VALUES
(1, 2, 1, '2020-03-05', null, true),
(2, 1, 2, '2020-01-27', '2020-01-29', false),
(3, 1, 3, '2020-03-01', null, true),
(4, 2, 4, '2020-02-28', '2020-03-02', false),
(2, 2, 2, '2020-03-04', null, true),
(4, 2, 4, '2020-01-04', '2020-01-05', false),
(3, 1, 3, '2020-02-02', '2020-02-04', false),
(1, 1, 1, '2020-03-05', null, true);

DROP TABLE IF EXISTS OrderDetails;
CREATE TABLE OrderDetails (
OrderID INT,
ProductID VARCHAR(10),
Price DECIMAL(13, 2),
Quantity INT,
Discount DECIMAL(5, 2),
PRIMARY KEY (OrderID, ProductID),
FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
FOREIGN KEY (ProductID) REFERENCES Products(ProductID));

INSERT INTO OrderDetails (OrderID, ProductID, Price, Quantity, Discount) VALUES
(1, 'PRFRAP0001', 0.99, 5, 0.1),
(1, 'PRFRAP0002', 0.99, 10, 0.1),
(2, 'PRVEON0001', 0.66, 50, 0.25),
(2, 'PRVEON0002', 0.54, 70, 0.25),
(2, 'PRFRAP0001', 0.99, 10, 0),
(3, 'PRFRAP0001', 1.29, 12, 0),
(4, 'PRFRAP0001', 0.99, 2, 0),
(4, 'PRFRAP0002', 0.99, 2, 0),
(4, 'PRVEON0001', 0.66, 2, 0),
(4, 'PRVEON0002', 0.54, 2, 0),
(5, 'BABREA0003', 2.89, 20, 0),
(6, 'FIRODS0002', 39.98, 1, 0.1),
(6, 'FIBAAR0001', 2.59, 2, 0),
(6, 'FIBALI0001', 0.07, 100, 0),
(7, 'FIBALI0002', 0.01, 9001, 0.5),
(8, 'PRVEON0001', 0.54, 4, 0.2),
(8, 'BABREA0001', 4.69, 1, 0.1),
(8, 'FIBALI0001', 0.07, 12, 0);


-- CREATING VIEWS
CREATE VIEW AllOrders AS
SELECT 
	o.OrderID, 
	c.Name AS Customer, 
	s.Name AS Shipper, 
	b.Name As Payment, 
	o.Date AS OrderDate, 
	o.ShipDate, 
	IF(o.OpenStatus=1, 'True', 'False') AS OpenStatus
FROM Orders o 
JOIN Customers c ON o.CustomerID = c.CustomerID
JOIN Shippers s ON o.ShipperID = s.ShipperID 
JOIN Billables b ON o.BillableID = b.BillableID;

CREATE VIEW OrdersDescendingTotal AS
SELECT 
	o.OrderId, 
	c.Name AS Customer, 
    s.Name AS Shipper, 
    b.Name AS Payment, 
    o.Date AS OrderDate, 
    o.ShipDate, 
    CONCAT('$', FORMAT(SUM(od.Price * od.Quantity * (1 - od.Discount)), 2)) AS Total
FROM Orders o 
JOIN OrderDetails od ON o.OrderID = od.OrderID
JOIN Customers c ON o.CustomerID = c.CustomerID
JOIN Shippers s ON o.ShipperID = s.ShipperID
JOIN Billables b ON o.BillableID = b.BillableID
GROUP BY od.OrderID
ORDER BY SUM(od.Price * od.Quantity * (1 - od.Discount)) DESC;

-- Following view does not include currency formatting
CREATE VIEW OrdersMinTotal AS
SELECT 
	o.OrderId, 
    c.Name AS Customer, 
    s.Name AS Shipper, 
    b.Name AS Payment, 
    o.Date AS OrderDate, 
    o.ShipDate, 
    SUM(od.Price * od.Quantity * (1 - od.Discount)) AS Total
FROM Orders o 
JOIN OrderDetails od ON o.OrderID = od.OrderID
JOIN Customers c ON o.CustomerID = c.CustomerID
JOIN Shippers s ON o.ShipperID = s.ShipperID
JOIN Billables b ON o.BillableID = b.BillableID
GROUP BY od.OrderID
ORDER BY SUM(od.Price * od.Quantity * (1 - od.Discount)) DESC;

SELECT * FROM Products p JOIN OrderDetails od ON p.ProductID = od.ProductID WHERE od.OrderID = 1;

CREATE VIEW ProductsInOrder AS
SELECT 
	od.OrderID,
	p.ProductId, 
    p.Name AS Product,
    c.Name AS Category,  
    CONCAT('$', FORMAT(od.Price, 2)) AS Price, 
    od.Quantity,
    CONCAT(FORMAT(p.Weight, 3),'kg') AS Weight, 
    CONCAT(FORMAT (od.Discount * 100, 0), '%') AS Discount 
FROM Products p 
JOIN OrderDetails od ON p.ProductID = od.ProductID 
JOIN Categories c ON p.CategoryID = c.CategoryID;

CREATE VIEW AllProducts AS
SELECT 
	p.ProductID, 
    p.Name, 
    c.name AS Category,
    s.name AS Supplier,
    CONCAT('$', p.Price) AS Price,
    CONCAT(FORMAT(p.Weight, 3),'kg') AS Weight,
    p.Stock,
    p.ReorderLevel
FROM Products p
JOIN Categories c ON p.CategoryID = c.CategoryID
JOIN Suppliers s on p.SupplierID = s.SupplierID;

CREATE VIEW AllCustomers AS
SELECT
	c.CustomerID,
    c.Name,
    c.Email,
    c.Phone,
    a.Street,
    a.City,
    a.State,
    a.Zip
FROM Customers c
JOIN Addresses a ON c.AddressID = a.AddressID;