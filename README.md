Java, Spring Boot, JUnit

# README

Content:
- Main Source Code Structure
- How to build and run (using Docker)
- CURL Command to test
## Main Source Code Structure

### Source code:
src/
- Controller folder contains Controller Class, is the place to receive request information from the user. It is responsible for receiving requests (with request information) and passing these requests down to the @Serivce layer to handle logic.
- Service folder contains Service Class, is the place to process business logic
- Repository folder contains Repository Class, is the place to interact with the Database
- DTO folder contains Data Access Object Class
- Model folder contains Model Object, this object used for interacting with database
- Security folder contains classes related to Security

### Unit Testing
test/java/com.example.eshop/service.impl
- OrderServiceImplTest: Unit testing OrderService business logic
- ProductServiceImplTest: Unit testing ProductService business logic

### How to build and run (using Docker)

- file Dockerfile to build image for java source code 'docker build .'
- Docker-compose.yml to run Docker by command 'docker-compose up'

#### NOTES: Install Docker to run

just command: 'docker-compose up'

## CURL Command 

### Sign-up Admin
curl --location --request POST 'http://127.0.0.1:9090/api/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name":"admin",
	"username": "admin",
	"email":"admin@gmail.com",
	"password":"admin"
}'

``
Response: 
{
    "success": true,
    "message": "User registered successfully"
}
``

### Sign-in Admin

curl --location --request POST 'http://127.0.0.1:9090/api/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
	"usernameOrEmail":"admin@gmail.com",
	"password":"admin"
}'

``
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjIxMzYxODk4LCJleHAiOjE2MjE0NDgyOTh9.IOkD0EibNsQcCMUhHdkGtdPL5RYdvXV1UHiZNHDhJl0sjmO6CzlspAoMVMmaMgqN4WcklhGCliWzzubo_Y1_MA",
    "tokenType": "Bearer",
    "id": 1,
    "role": "ROLE_ADMIN"
}
``

### Add Product with tokeType and accessToken from Sign-in Response

curl --location --request POST 'http://127.0.0.1:9090/eshop/products' \
--header 'Authorization: {{tokenType}} {{accessToken}}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Tivi-1",
    "price" : 10000000,
    "brand" : "Samsung",
    "color" : "Black",
    "image" : "image",
    "description" : "description",
    "quantity": 10,
    "exInfo" : "{}"
}'

curl --location --request POST 'http://127.0.0.1:9090/eshop/products' \
--header 'Authorization: {{tokenType}} {{accessToken}}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Tivi-2",
    "price" : 10000000,
    "brand" : "Samsung",
    "color" : "Black",
    "image" : "image",
    "description" : "description",
    "quantity": 10,
    "exInfo" : "{}"
}'

curl --location --request POST 'http://127.0.0.1:9090/eshop/products' \
--header 'Authorization: {{tokenType}} {{accessToken}}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Tivi-3",
    "price" : 10000000,
    "brand" : "Samsung",
    "color" : "Black",
    "image" : "image",
    "description" : "description",
    "quantity": 10,
    "exInfo" : "{}"
}'

``
Response: ProductID just added
``

### Query can filter, sort, and search for products based on different criteria such as name, price, brand, color...

curl --location --request GET 'http://127.0.0.1:9090/eshop/products?q=Tivi&brand=Samsung&color=Black&price=100000,-1&sort=price,ASC'

List Product
``
[{"id":2,"name":"Tivi","price":10000000,"brand":"Samsung","color":"Black","image":"image","description":"description","quantity":10,"exInfo":"{}"},{"id":3,"name":"Tivi","price":10000000,"brand":"Samsung","color":"Black","image":"image","description":"description","quantity":10,"exInfo":"{}"},{"id":4,"name":"Tivi","price":10000000,"brand":"Samsung","color":"Black","image":"image","description":"description","quantity":10,"exInfo":"{}"},{"id":5,"name":"Tivi","price":10000000,"brand":"Samsung","color":"Black","image":"image","description":"description","quantity":10,"exInfo":"{}"}]
``

### Update Product with tokeType and accessToken from Sign-in Response

curl --location --request PUT 'http://127.0.0.1:9090/eshop/products/1' \
--header 'Authorization: {{tokenType}} {{accessToken}}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1,
    "name" : "Tivi2",
    "price" : 10000000,
    "brand" : "Sony",
    "color" : "Black",
    "image" : "image",
    "description" : "description",
    "quantity": 10,
    "exInfo" : "{}"
}'

``
Response: ProductID just updated
``

### Delete Product with tokeType and accessToken from Sign-in Response

curl --location --request DELETE 'http://127.0.0.1:9090/eshop/products/1' \
--header 'Authorization: {{tokenType}} {{accessToken}}' \

``
Response 
true: delete successfully
false: delete failed
``

### Submit Order

curl --location --request POST 'http://127.0.0.1:9090/eshop/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName" : "Nghia",
    "phoneNumber" : "0827877888",
    "address" : "VietNam",
    "email" : "nghiatdh@gmail.com",
    "orderDetailList" : [
        {
            "productID" : 6,
            "quantity"  : 10
        },
                {
            "productID" : 7,
            "quantity"  : 1
        }
    ]
}'

```
Response 
-1 : Submit failed cause out of product (check Entity) or ProductID not found
orderID: ID of Order just added
```

Due to time issues, the project may have some not good things :D
