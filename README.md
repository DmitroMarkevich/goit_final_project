# **Notes**
### **About**
Notes is an application to create and share notes among users. 

### **Key Functions**
- Authorization and registration an acсount;
- Creating, updating and sharing notes;

### **How to run**
```application.properties       ```-> use as DEFAULT    for DEV, TEST  build: configuration with H2 DB; </br>
```application-prod.properties ```-> use as PRODUCTION for PRODUCTION build: configuration with PostgreSQL DB
```
bootRun {
jvmArgs = ["-Dspring.profiles.active=production"]
}
```
OR
2.Run application with environment variable ```spring.profiles.active=production``` in your IDE:
### DB CONNECTION CONFIGURATION AND SET ENVIRONMENT VARIABLES:

1.Create environment variables:
```
${NOTE_DB_USER}         =>  DB user;
${NOTE_DB_PASSWORD}     =>  DB password;
```

Example for Windows, run  CMD and execute commands:
```
setx NOTE_DB_USER "user"
setx NOTE_DB_PASSWORD "12345"
```
Example for Linux, run  SH and execute commands:
```
export NOTE_DB_USER='user'
export NOTE_DB_PASSWORD='12345'
```
For connection use defaults:
```
NOTE_DB_USER = user
NOTE_DB_PASSWORD = 12345
```
описати як запустити настроїт ита запустити 

### **Config**
- Java 17
- Gradle
- Spring boot (Core, MVC, Security, Data, Mail Sender)
- H2(in Memory), PostgreSQL
- Flyway
- Hibernate 
- JUnit, Mockito
- Thymeleaf, HTML, JavaScript, Bootstrap



