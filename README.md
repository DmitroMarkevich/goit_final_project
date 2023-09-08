# [Note Service](https://notes.soon.it)
## **About**
Note Service is a helpful platform for creating and organizing tasks. It offers a user-friendly interface and useful features that boost productivity, making it a valuable tool for both personal and professional tasks.

## **How to run**
**Run application with PostgreSQL in your IDE (```spring.profiles.active=prod```)**

```application.properties     ```  –  use as DEV with H2 DB </br>
```application-prod.properties```  –  use as PROD with PostgreSQL

### DB CONNECTION CONFIG
Create environment variables:
```
${DB_URL}        ->  DB url;
${DB_USERNAME}   ->  DB user;
${DB_PASSWORD}   ->  DB password;
${MAIL_USERNAME} ->  Email;
${MAIL_PASSWORD} ->  Email password.
```
Example for Windows, run  CMD and execute commands:
```
setx DB_USERNAME "user"
setx DB_PASSWORD "12345"
```
Example for Linux, run  SH and execute commands:
```
export DB_USERNAME='user'
export DB_PASSWORD='12345'
```
For connection use defaults:
```
DB_USERNAME = user
DB_PASSWORD = 12345
```
## **Technologies**
- Java 17
- Gradle
- Spring boot (Core, MVC, Security, Data)
- H2 (in-memory), PostgreSQL
- Flyway
- Hibernate 
- JUnit, Mockito
- Thymeleaf, HTML, JavaScript, Bootstrap
- Java Mail Sender
