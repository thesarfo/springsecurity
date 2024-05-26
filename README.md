# Spring Boot and Spring Security Starter Pack

This is a spring boot and spring security implementation that comes with

* Jwt
* Oauth2
* Refresh Tokens
* a RESTful API
* Unit and Integration Tests

### Rest Api Endpoints

<details>
<summary>Click here</summary>
<table style="width:100%">
  <tr>
      <th>Method</th>
      <th>Url</th>
      <th>Description</th>
      <th>Request Body</th>
      <th>Header</th>
      <th>Valid Path Variable</th>
      <th>No Path Variable</th>
  </tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/authentication/admin/register</td>
      <td>Admin Register</td>
      <td>AdminRegisterRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/authentication/admin/login</td>
      <td>Admin Login</td>
      <td>LoginRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/authentication/admin/refreshtoken</td>
      <td>Admin Refresh Token</td>
      <td>TokenRefreshRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/authentication/admin/logout</td>
      <td>Admin Logout</td>
      <td>TokenInvalidateRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/authentication/user/register</td>
      <td>User Register</td>
      <td>UserRegisterRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/authentication/user/login</td>
      <td>User Login</td>
      <td>LoginRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/authentication/user/refreshtoken</td>
      <td>User Refresh Token</td>
      <td>TokenRefreshRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/authentication/user/logout</td>
      <td>User Logout</td>
      <td>TokenInvalidateRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>POST</td>
      <td>/api/v1/products</td>
      <td>Create Product</td>
      <td>ProductCreateRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>GET</td>
      <td>/api/v1/products/{productId}</td>
      <td>Get Product By Id</td>
      <td></td>
      <td></td>
      <td>ProductId</td>
      <td></td>
  <tr>
  <tr>
      <td>GET</td>
      <td>/api/v1/products</td>
      <td>Get Products</td>
      <td>ProductPagingRequest</td>
      <td></td>
      <td></td>
      <td></td>
  <tr>
  <tr>
      <td>PUT</td>
      <td>/api/v1/products/{productId}</td>
      <td>Update Product By Id</td>
      <td>ProductUpdateRequest</td>
      <td></td>
      <td>ProductId</td>
      <td></td>
  <tr>
  <tr>
      <td>DELETE</td>
      <td>/api/v1/products/{productId}</td>
      <td>Delete Product By Id</td>
      <td></td>
      <td></td>
      <td>ProductId</td>
      <td></td>
  <tr>
</table>
</details>


### Built with

---
- Java 17
- Spring Boot 3.0
- Lombok
- Maven
- Junit5
- Mockito
- Integration Tests
- Docker
- Docker Compose


### Prerequisites

#### Define Variables in .env file

```
DATABASE_USERNAME={DATABASE_USERNAME}
DATABASE_PASSWORD={DATABASE_PASSWORD}
```

---
- Maven or Docker
---


### Docker Run
The application can be built and run by the `Docker` engine. The `Dockerfile` has multistage build, so you don;t need to build and run separately.

Follow directions shown below in order to build and run the application with Docker Compose file;

```sh
$ cd springsecurity
$ docker-compose up -d
```

If you change anything in the project and run it on Docker, you can also use this command shown below

```sh
$ cd springsecurity
$ docker-compose up --build
```

---
### Maven Run
To build and run the application with `Maven`, please follow the directions shown below;

```sh
$ cd springsecurity
$ mvn clean install
$ mvn spring-boot:run
```

### Screenshots

<details>
<summary>Click here</summary>
    <p> Figure 1 </p>
    <img src ="screenshots/spring_1.PNG">
    <p> Figure 2 </p>
    <img src ="screenshots/spring_2.PNG">
    <p> Figure 3 </p>
    <img src ="screenshots/spring_3.PNG">
    <p> Figure 4 </p>
    <img src ="screenshots/spring_4.PNG">
    <p> Figure 5 </p>
    <img src ="screenshots/spring_5.PNG">
    <p> Figure 6 </p>
    <img src ="screenshots/spring_6.PNG">
    <p> Figure 7 </p>
    <img src ="screenshots/spring_7.PNG">
    <p> Figure 8 </p>
    <img src ="screenshots/spring_8.PNG">
    <p> Figure 9 </p>
    <img src ="screenshots/spring_9.PNG">
    <p> Figure 10 </p>
    <img src ="screenshots/spring_10.PNG">
    <p> Figure 11 </p>
    <img src ="screenshots/spring_11.PNG">
    <p> Figure 12 </p>
    <img src ="screenshots/spring_12.PNG">
    <p> Figure 13 </p>
    <img src ="screenshots/spring_13.PNG">
    <p> Figure 14 </p>
    <img src ="screenshots/spring_14.PNG">
    <p> Figure 15 </p>
    <img src ="screenshots/spring_15.PNG">
    <p> Figure 16 </p>
    <img src ="screenshots/spring_16.PNG">
</details>