# File Structure

```
voa/src/main/java/com/nqz/voa/
├── VoaApplication.java       <-- Run File
├── controller                <-- REST Requests
├── mapper                    <-- Database Accesses
├── entry                     <-- Data Entities
├── helper                    <-- Helper Classes
└── service                   <-- Services and Implementations
```


# Install & Run
- Dependencies:
    - Java SDK 17
    - Spring Boot Web
    - Maven
    - MySQL Local Sever (XAMPP, MySQL Workbench)
- Make sure your sql server is running at port 3306, or you could change the sql server URL in file `./resources/application-dev.yml`
- Build the project and run `VoaApplication.java`
- The server is running on port 8080 by default.

# Example Request URL
- `localhost:8080/account/member/register` Register an account for a member user.
- `localhost:8080/account/login` Login to the website.
- `localhost:8080/account/profile` Get the profile for the current user. 
- `localhost:8080/visitor/list` List all visitors (Need login, need admin permission).
- `localhost:8080/account/logout` Logout for the current user. 
- Please refer to `./controller` for more API endpoints. 

# Features

- RESTful API
- Prevent SQL injection using Mybatis PreparedStatement
- Use cookie/cache to store account information
- Request validation
- Password Encryption
- Session evaluation for identification
- Use Mybatis to connect Java webserver to database
