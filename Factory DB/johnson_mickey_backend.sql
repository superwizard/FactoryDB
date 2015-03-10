CREATE DATABASE Johnson_Mickey_db;

USE Johnson_Mickey_db;

CREATE TABLE PRODUCT (
    ProductNumber       INT         NOT NULL AUTO_INCREMENT,
    ProductionTime      INT         NOT NULL,
    Description			TEXT		NOT NULL,
    CONSTRAINT  ProductPK           PRIMARY KEY(ProductNumber)
);

CREATE TABLE PART (
    PartNumber          INT         NOT NULL,
    Description         TEXT 		NOT NULL,
    CONSTRAINT  PartPK              PRIMARY KEY(PartNumber)
);

CREATE TABLE PRODUCT_PARTLIST (
    ProductNumber       INT         NOT NULL,
    PartNumber          INT         NOT NULL,
    NumberRequired      INT         NOT NULL,
    CONSTRAINT  ProductPartPK       PRIMARY KEY(ProductNumber, PartNumber),
    CONSTRAINT  ProductFK           FOREIGN KEY(ProductNumber)
										REFERENCES PRODUCT(ProductNumber)
											ON DELETE CASCADE,
    CONSTRAINT  PartFK              FOREIGN KEY(PartNumber)
										REFERENCES PART(PartNumber)
											ON DELETE CASCADE
);

CREATE TABLE INVENTORY (
    PartNumber          INT         NOT NULL,
    Stock               INT         NOT NULL,
    CONSTRAINT  InventoryPK         PRIMARY KEY(PartNumber),
    CONSTRAINT  InventoryFK         FOREIGN KEY(PartNumber)
										REFERENCES PART(PartNumber)
											ON DELETE CASCADE
);

CREATE TABLE PART_ORDERS (
    OrderNumber         INT         NOT NULL,
    PartNumber          INT         NOT NULL,
    NumberOrdered       INT         NOT NULL,
    DeliveryDate        DATE        NOT NULL,
    CONSTRAINT  PartOrdersPK        PRIMARY KEY(OrderNumber),
    CONSTRAINT  PartOrdersFK        FOREIGN KEY(PartNumber)
										REFERENCES PART(PartNumber)
											ON DELETE CASCADE
);

CREATE TABLE CUSTOMER_ORDER (
    OrderNumber         INT         NOT NULL,
    Customer            TEXT		NOT NULL,
    DateRequired        DATE        NOT NULL,
    DateShipped         DATE        NULL,
    CONSTRAINT  OrderPK             PRIMARY KEY(OrderNumber)
);

CREATE TABLE PRODUCT_ORDER (
    OrderNumber         INT         NOT NULL,
    ProductNumber       INT         NOT NULL,
    NumToProduce        INT         NOT NULL,
    CONSTRAINT  ProductOrderPK      PRIMARY KEY(OrderNumber, ProductNumber),
    CONSTRAINT  ProductOrderFK1     FOREIGN KEY(OrderNumber)
										REFERENCES CUSTOMER_ORDER(OrderNumber)
											ON DELETE CASCADE,
    CONSTRAINT  ProductOrderFK2     FOREIGN KEY(ProductNumber)
										REFERENCES PRODUCT(ProductNumber)
											ON DELETE CASCADE
);

CREATE VIEW PartsNeededPerOrder AS
	SELECT 	PRODUCT_ORDER.OrderNumber as OrderNumber,
			INVENTORY.PartNumber as PartNumber,
			(PRODUCT_PARTLIST.NumberRequired * PRODUCT_ORDER.NumToProduce - INVENTORY.Stock) 
			as NumberRequired,
			CUSTOMER_ORDER.DateRequired as DateRequired
    FROM 	PRODUCT_PARTLIST, INVENTORY, PRODUCT_ORDER, CUSTOMER_ORDER
	WHERE 	PRODUCT_PARTLIST.PartNumber = INVENTORY.PartNumber
    AND 	(PRODUCT_PARTLIST.NumberRequired * PRODUCT_ORDER.NumToProduce - INVENTORY.Stock) > INVENTORY.Stock
    AND		 PRODUCT_ORDER.ProductNumber = PRODUCT_PARTLIST.ProductNumber
    AND 	PRODUCT_ORDER.OrderNumber = CUSTOMER_ORDER.OrderNumber;

CREATE VIEW ProductionPerProduct AS
	SELECT 		ProductNumber, SUM(NumToProduce) AS NumProd
	FROM 		PRODUCT_ORDER
	GROUP BY	 ProductNumber;

CREATE VIEW TotalPartsNeeded AS
	SELECT 	INVENTORY.PartNumber as PartNumber, 
			(Nums.NumProd * PRODUCT_PARTLIST.NumberRequired - INVENTORY.Stock) as NumberNeeded
	FROM 	ProductionPerProduct AS Nums, INVENTORY, PRODUCT_PARTLIST
	WHERE 	Nums.ProductNumber = PRODUCT_PARTLIST.ProductNumber
	AND		INVENTORY.PartNumber = PRODUCT_PARTLIST.PartNumber
	AND		INVENTORY.Stock < (Nums.NumProd * PRODUCT_PARTLIST.NumberRequired);

INSERT INTO PART VALUES(1, "widget");
INSERT INTO PART VALUES(2, "spring");
INSERT INTO PART VALUES(3, "wheel");
INSERT INTO PART VALUES(4, "block");
INSERT INTO PART VALUES(5, "gizmo");
INSERT INTO PART VALUES(6, "thingy");

INSERT INTO INVENTORY VALUES(1, 110);
INSERT INTO INVENTORY VALUES(2, 500);
INSERT INTO INVENTORY VALUES(3, 860);
INSERT INTO INVENTORY VALUES(4, 0);
INSERT INTO INVENTORY VALUES(5, 258);
INSERT INTO INVENTORY VALUES(6, 14);

INSERT INTO PART_ORDERS VALUES(1, 4, 300, 20160412);
INSERT INTO PART_ORDERS VALUES(4, 4, 50, 20151203);
INSERT INTO PART_ORDERS VALUES(6, 1, 78, 20151203);

INSERT INTO PRODUCT VALUES(null, 1, "gadget");
INSERT INTO PRODUCT VALUES(null, 5, "another gadget");

INSERT INTO PRODUCT_PARTLIST VALUES(1, 1, 1);
INSERT INTO PRODUCT_PARTLIST VALUES(1, 2, 3);
INSERT INTO PRODUCT_PARTLIST VALUES(1, 3, 4);
INSERT INTO PRODUCT_PARTLIST VALUES(1, 5, 1);
INSERT INTO PRODUCT_PARTLIST VALUES(2, 4, 2);
INSERT INTO PRODUCT_PARTLIST VALUES(2, 6, 3);

INSERT INTO CUSTOMER_ORDER VALUES(1, "Lord Industries", 20161111, null);
INSERT INTO PRODUCT_ORDER VALUES(1, 1, 50);
INSERT INTO PRODUCT_ORDER VALUES(1, 2, 5);


INSERT INTO CUSTOMER_ORDER VALUES(2, "Metal Manf.", 20150611, null);
INSERT INTO PRODUCT_ORDER VALUES(2, 1, 90);

INSERT INTO CUSTOMER_ORDER VALUES(3, "Tiny Corp.", 20150611, null);
INSERT INTO PRODUCT_ORDER VALUES(3, 2, 10);

INSERT INTO CUSTOMER_ORDER VALUES(4, "Touchin LLC", 20150611, null);
INSERT INTO PRODUCT_ORDER VALUES(4, 1, 50);

INSERT INTO CUSTOMER_ORDER VALUES(5, "Metal Manf.", 20150611, null);
INSERT INTO PRODUCT_ORDER VALUES(5, 1, 90);

INSERT INTO CUSTOMER_ORDER VALUES(6, "Metal Manf.", 20150611, null);
INSERT INTO PRODUCT_ORDER VALUES(6, 2, 5);
INSERT INTO PRODUCT_ORDER VALUES(6, 1, 5);

INSERT INTO CUSTOMER_ORDER VALUES(7, "Tiny Corp.", 20150611, null);
INSERT INTO PRODUCT_ORDER VALUES(7, 1, 25);

INSERT INTO CUSTOMER_ORDER VALUES(8, "Metal Manf.", 20150611, null);
INSERT INTO PRODUCT_ORDER VALUES(8, 1, 6);

INSERT INTO CUSTOMER_ORDER VALUES(9, "Touchin LLC", 20150611, null);
INSERT INTO PRODUCT_ORDER VALUES(9, 2, 55);

INSERT INTO CUSTOMER_ORDER VALUES(10, "Touchin LLC", 20140611, 20150101);
INSERT INTO PRODUCT_ORDER VALUES(10, 2, 55);

INSERT INTO CUSTOMER_ORDER VALUES(11, "Touchin LLC", 20170211, null);
INSERT INTO PRODUCT_ORDER VALUES(11, 2, 55);
