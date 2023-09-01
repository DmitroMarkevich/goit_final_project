# **Notes**
### **About**
Notes is an application to create and share notes among users. 

### **Key Functions**
- Authorization and registration an acсount;
- Creating, updating and sharing notes;

### **How to run**
```application.properties       ```-  use as DEV with H2 DB </br>
```application-prod.properties ```-  use as PRODUCT with PostgreSQL 
```
1.Add this code in the build.gradle file:
bootRun {
jvmArgs = ["-Dspring.profiles.active=prod"]
}
```
OR
2.Run application with environment variable ```spring.profiles.active=prod``` in your IDE:
### DB CONNECTION CONFIGURATION AND SET ENVIRONMENT VARIABLES:

1.Create environment variables:
```
${DB_URL}    ->  DB url;
${DB_USERNAME}         ->  DB user;
${DB_PASSWORD}     ->  DB password;
${MAIL_USERNAME}     -> Email;
${MAIL_PASSWORD}    -> Email password;
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

### **Technologies**
- Java 17
- Gradle
- Spring boot (Core, MVC, Security, Data)
- H2(in Memory), PostgreSQL
- Flyway
- Hibernate 
- JUnit, Mockito
- Java Mail Sender
- Thymeleaf, HTML, JavaScript, Bootstrap



