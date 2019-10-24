# Products and categories REST API
Problem Defination
Develop a RESTFul API for managing products and categories and show product details in a
REACT frontend

Tasks
Database schema: Create a schema to store product and categories. (Getting the full category path for
the products might be a future requirement)
CRUD operations: The client should perform CRUD operations on products and categories.
Currency exchange: Add an ability to create products with price different from Euro. For this please
integrate with an open currency exchange API (for example http://fixer.io)

Assumptions
A product might be associated to multiple categories. For instance, an electric toothbrush may belong to both "Electronics" and "Beauty & Personal Care" categories.
There are some fields that for simplicity we are not having in Product table such as description, features, price discount, refurbished, among others.
Products . 
Notes
It's important to note that this is a HATEOAS-oriented REST API. That means you will find some URLs in the API response referencing to possible actions that can be performed on the resource. It's highly recommendable to take a look at the unit tests defined in com.github.damiox.ecommerce.api.controller to understand the expected API responses from a Development perspective.

How to use this API
A swagger UI interface is exposed to the end user using which you can do CURD operation on category and products.
http://localhost:8080/swagger-ui.html#/

Note: we are using HATEOAS-oriented REST endpoints (https://en.wikipedia.org/wiki/HATEOAS), 
so you will find the possible operations to perform on resources while browsing the main 
endpoints: /products and /categories 

Products
The list of Products is always a paginated result for scalability.

URL: /products

To get (paged) list of products: curl -H "Authorization: XXXX" -X GET "http://localhost:8080/products"
To get products info: curl -H "Authorization: XXXX" -X GET "http://localhost:8080/products/{id}"
To create products: curl -H "Content-Type: application/json" -H "Authorization: XXXX" -X POST "http://localhost:8080/products" -d '{ "name": "P1", "currency": "EUR", "price": 100.00 }'
To update products: curl -H "Content-Type: application/json" -H "Authorization: XXXX" -X PUT "http://localhost:8080/products/{id}" -d '{ "name": "P1", "currency": "EUR", "price": 100.00 }'
To delete products: curl -H "Authorization: XXXX" -X DELETE "http://localhost:8080/products/{id}"
Categories

URL: /categories

To get list of categories: curl -H "Authorization: XXXX" -X GET "http://localhost:8080/categories"
To get category info: curl -H "Authorization: XXXX" -X GET "http://localhost:8080/categories/{id}"
To create category: curl -H "Content-Type: application/json" -H "Authorization: XXXX" -X POST "http://localhost:8080/categories" -d '{ "name": "C1" }'
To update category: curl -H "Content-Type: application/json" -H "Authorization: XXXX" -X PUT "http://localhost:8080/categories/{id}" -d '{ "name": "C1" }'
To delete category: curl -H "Authorization: XXXX" -X DELETE "http://localhost:8080/categories/{id}"
Add / Remove child categories
To associate / dis-associate a child category with / from a parent category you can use the following URL: /categories/{parentid}/subcategories/{childid}
To see the current child categories for a given category, you can do a GET on /categories/{parentid}/subcategories
Link / Unlink products
To link / unlink products with categories you can use the following URL: /categories/{categoryid}/products/{productid}
To see the current products for a given category, you can do a GET on /categories/{parentid}/products. Note: the API will return also products that are being associated indirectly. That means if a Product is associated with Category B, which is in turn a child of Category A, then the product is directly associated with Category B, and indirectly associated with Category A. Accessing to /categories/A/products will return that product that is associated with Category A indirectly along with the products being associated directly with the Category A.

Technologies Used 

Java 8
Spring Boot
Spring Web / MVC
H2 (in-memory database)
Spring Data (JPA)
Hibernate (ORM)
Logback
Swagger
Spring actuator
Spring HATEOAS
Spring Test
