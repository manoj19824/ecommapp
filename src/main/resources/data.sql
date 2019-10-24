--Delete all data before insert
delete from product_category;
delete from category;
delete from product;
-- Categories
insert into category (id, name, parentid) values (1, 'Electronic appliances', null);
insert into category (id, name, parentid) values (2, 'Clothes, Shoes & Accessories', null);
insert into category (id, name, parentid) values (3, 'Home & Outdoor appliances', null);
insert into category (id, name, parentid) values (4, 'Beauty & Personal Care', null);
insert into category (id, name, parentid) values (5, 'Others', null);
-- Subcategories for 'Electronics'
insert into category (id, name, parentid) values (6, 'Audio & Video Components', 1);
insert into category (id, name, parentid) values (7, 'Camera & Photo', 1);
insert into category (id, name, parentid) values (8, 'Computers & Laptop', 1);
-- Subcategories for 'Clothing, Shoes & Accessories'
insert into category (id, name, parentid) values (9, 'Clothing', 2);
insert into category (id, name, parentid) values (10, 'Costumes & Accessories', 2);
insert into category (id, name, parentid) values (11, 'Fashion Accessories', 2);
-- Subcategories for 'Home & Outdoor'
insert into category (id, name, parentid) values (12, 'Furniture, Decor & Storage', 3);
insert into category (id, name, parentid) values (13, 'Antiques', 3);
insert into category (id, name, parentid) values (14, 'Appliances', 3);
-- Subcategories for 'Beauty & Personal Care'
insert into category (id, name, parentid) values (15, 'Accessories', 4);
insert into category (id, name, parentid) values (16, 'Bath & Shower', 4);
insert into category (id, name, parentid) values (17, 'Feminine Hygiene', 4);

-- Products for Category 'Electronics > Audio & Video Components'
insert into product (id, name, price) values (1, 'TV 4K (32 GB)', 223.22);
insert into product (id, name, price) values (2, 'RCA ANT751 OUTDOOR ANTENNA - UPTO 3.3 FT', 49.22);
insert into product (id, name, price) values (3, 'APC REPLACEMENT BATTERY NO 24', 299.00);
insert into product_category (productid, categoryid) values (1, 6);
insert into product_category (productid, categoryid) values (2, 6);
insert into product_category (productid, categoryid) values (3, 6);
-- Products for Category 'Electronics > Camera & Photo'
insert into product (id, name, price) values (4, 'CAXXX EOS REBEL T6 DSLR 18-55MM - 1159C003', 379.95);
insert into product (id, name, price) values (5, 'RCA ANT751 OUTDOOR ANTENNA  - UPTO 3.3 FT', 49.22);
insert into product (id, name, price) values (6, 'BEST CHOICE PRODUCTS 6-AXIS HEADLESS RC QUADCOPTER FPV RC DRONE W/ WIFI HD CAMERA', 42.99);
insert into product_category (productid, categoryid) values (4, 7);
insert into product_category (productid, categoryid) values (5, 7);
insert into product_category (productid, categoryid) values (6, 7);
-- Products for Category 'Electronics > Computers and Laptop'
insert into product (id, name, price) values (7, 'APXXX MXCBXXK AIR MQD42LL/A 13.3" LCD NOTEBOOK - INTEL CORE I5 (5TH GEN) DUAL-CORE (2 CORE) 1.80 GHZ - 8 GB LPDDR3 - 256 GB SSD - MXC OS SIERRX - 1440 X 900 - INTXL HD GRAPHICS 6000 LPDDR3 - BLUETOOTH - ENGLISH (US) KEYBOARD - FRONT CAMERA/WEBCAM - IEEE 8', 1052.49);
insert into product (id, name, price) values (8, 'DXLL INSPIXXN 13 5000 SERIES 2-IN-1- I3-7100U- 1TB HDD- 4GB RAM', 479.99);
insert into product (id, name, price) values (9, 'HEWLXXX PAXXXRD 11-AE010NR X360 11 INTXL N3350 CHROMEBOOK CONVERTIBLE LAPTOP - 2MW49UA#ABA', 239.00);
insert into product_category (productid, categoryid) values (7, 8);
insert into product_category (productid, categoryid) values (8, 8);
insert into product_category (productid, categoryid) values (9, 8);
-- Products for Category 'Clothing, Shoes & Accessories > Clothing'
insert into product (id, name, price) values (10, 'DICKXXX WP595DS 30 32 MENS REGULAR STRAIGHT STRETCH TWILL CARGO PANT - DESERT SAND - 30 - 32', 26.99);
insert into product (id, name, price) values (11, 'TINKXXXXLL - TINXXXXOTS GIRLS YOUTH SLEEP PANTS', 11.00);
insert into product (id, name, price) values (12, 'INTENSXXX N4700112LRG ADULT NYLON DOUBLE KNIT PANT&#44; WHITE & ROYAL - LARGE', 12.28);
insert into product_category (productid, categoryid) values (10, 9);
insert into product_category (productid, categoryid) values (11, 9);
insert into product_category (productid, categoryid) values (12, 9);
-- Products for Category 'Clothing, Shoes & Accessories > Costumes & Accessories'
insert into product (id, name, price) values (13, 'DISGUIXX SHADOW NINJA GREEN MASTER NINJA DELUXE BOYS COSTUME, ONE COLOR, 7-8', 24.15);
insert into product (id, name, price) values (14, 'ZAXXXX STUDIOS M6003 SUPER DELUXE ORANGUTAN MASK', 48.94);
insert into product (id, name, price) values (15, 'RUBXXX INFLATABLE CHEWBACCA ADULT COSTUME-STANDARD', 82.94);
insert into product_category (productid, categoryid) values (13, 10);
insert into product_category (productid, categoryid) values (14, 10);
insert into product_category (productid, categoryid) values (15, 10);
-- Products for Category 'Clothing, Shoes & Accessories > Fashion Accessories'
insert into product (id, name, price) values (16, 'RXX-XXN MENS GRADIENT ACTIVE RB3386-004/13-67 SUNGLASSES', 103.99);
insert into product (id, name, price) values (17, 'TENXXXY BASIC KNIT WIRELESS HANDS-FREE BLUETOOTH ', 24.95);
insert into product (id, name, price) values (18, 'AIRBORXX EXXROIDERED BUCKET HAT W44S44E', 20.49);
insert into product_category (productid, categoryid) values (16, 11);
insert into product_category (productid, categoryid) values (17, 11);
insert into product_category (productid, categoryid) values (18, 11);
-- Products for Category 'Home & Outdoor > Furniture, Decor & Storage'
insert into product (id, name, price) values (19, 'BESXXXFICE HIGH BACK MODERN MESH OFFICE CHAIR - BLACK', 47.99);
insert into product (id, name, price) values (20, 'FOLXXXLE OUTDOOR WOOD ADIRONDACK CHAIR W/ PULL OUT OTTOMAN', 56.99);
insert into product (id, name, price) values (21, '13-GALXXN TXXCH-FREE SENSOR AUTOMATIC STAINLESS STEEL', 52.99);
insert into product_category (productid, categoryid) values (19, 12);
insert into product_category (productid, categoryid) values (20, 12);
insert into product_category (productid, categoryid) values (21, 12);
-- Products for Category 'Home & Outdoor > Antiques'
insert into product (id, name, price) values (22, 'COLXXXN SAN FRXXXISCO GIANTS 24 CAN SOFT SIDED COOLER/LUNCHBOX MLB', 29.99);
insert into product (id, name, price) values (23, 'AMERIXXWARE PSGAUS45 45 MM AUSTIN SNOW GLOBE', 10.11);
insert into product (id, name, price) values (24, '24 IN. W X 24 IN. H ROXXD XXBLE VENT LOXXER&#44; FUNCTIONAL', 73.24);
insert into product_category (productid, categoryid) values (22, 13);
insert into product_category (productid, categoryid) values (23, 13);
insert into product_category (productid, categoryid) values (24, 13);
-- Products for Category 'Home & Outdoor > Appliances'
insert into product (id, name, price) values (25, 'E-Z DISHXXXHER BRACKET AND SCREWS GRANITE', 9.99);
insert into product (id, name, price) values (26, 'BONAXXXA BV1500TS 5-CUP CAXAFE COFFEE BREWER, STAINLESS STEEL', 67.92);
insert into product (id, name, price) values (27, 'DUXXXP LCD 1800-WATT PORXXXLE INDUCTION COOKTOP', 100.99);
insert into product_category (productid, categoryid) values (25, 14);
insert into product_category (productid, categoryid) values (26, 14);
insert into product_category (productid, categoryid) values (27, 14);
-- Products for Category 'Beauty & Personal Care > Accessories'
insert into product (id, name, price) values (28, 'SPACE-SAVING SHOE ORGANIZER: SET OF 12', 24.99);
insert into product (id, name, price) values (29, 'COLDSTXXL COLD STEEL TI-LITE ZYTEL', 57.79);
insert into product (id, name, price) values (30, 'LXZ CLXIBORNE BORX BORX MEN COLOGNE SPRAY 3.4 OZ', 19.98);
insert into product_category (productid, categoryid) values (28, 15);
insert into product_category (productid, categoryid) values (29, 15);
insert into product_category (productid, categoryid) values (30, 15);
-- Products for Category 'Beauty & Personal Care > Bath & Shower'
insert into product (id, name, price) values (31, 'MOLTXN BROWN SOOTHING MILK & OATMEAL SOAP', 37.99);
insert into product (id, name, price) values (32, 'BATH BRUSH BACK SCRXB  NEW', 7.49);
insert into product (id, name, price) values (33, '9X SOAP DISPENSER DISH CASE HOLDER CONTAINER BOX', 13.99);
insert into product_category (productid, categoryid) values (31, 16);
insert into product_category (productid, categoryid) values (32, 16);
insert into product_category (productid, categoryid) values (33, 16);
-- Products for Category 'Beauty & Personal Care > Feminine Hygiene'
insert into product (id, name, price) values (34, 'GENUINE JXE GJO14457 CITRUS ORANGE', 11.63);
insert into product (id, name, price) values (35, 'GENUINE JXE GJO14467 ALCOHOL-FREE FOAM ', 12.14);
insert into product (id, name, price) values (36, 'MENDX 35601 LASTING-TOUCH - BROWN ROUND 8 OZ', 10.37);
insert into product_category (productid, categoryid) values (34, 17);
insert into product_category (productid, categoryid) values (35, 17);
insert into product_category (productid, categoryid) values (36, 17);

-- Adding some Products to other categories
insert into product_category (productid, categoryid) values (1, 8);
insert into product_category (productid, categoryid) values (11, 10);
insert into product_category (productid, categoryid) values (23, 12);
insert into product_category (productid, categoryid) values (32, 17);
insert into product_category (productid, categoryid) values (34, 16);
insert into product_category (productid, categoryid) values (35, 16);
insert into product_category (productid, categoryid) values (36, 16);