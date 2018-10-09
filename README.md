# Demo application for Gmail API using OAuth
This application will demonstrate how to access inbox in gmail using oauth. More information on this project will be available at [https://computeinbasics.blogspot.com/2018/10/lets-read-emails-in-gmail-using-oauth.html](https://computeinbasics.blogspot.com/2018/10/lets-read-emails-in-gmail-using-oauth.html)


## Technologies
- Java 8
- Spring Boot 2.0.4
- Bootstrap
- jQuery

## Dependencies
- Apache HttpClient
- Google Gson
- Thymeleaf Template Engine

## System Requirements
- Java 8 or grater.
- Apache Maven.
- Working internet connection (scripts and css imported by online cdn)

## Running Steps
- Clone this repository into your local file system. 
- Navigate into the cloned project folder using command prompt (terminal).
- Issue this command `mvn clean package` to build the project.
- Then issue following command to run the application.
`java -jar target\cross-site-request-synchronizer-demo-0.0.1-SNAPSHOT.jar`
- Now the server will start on port 8443.
- In case of 8443 port is already reserved in your pc, you can specify different port in running command. For example if you want to start server in 8090 port, you can issue following command.
 `java -jar target\cross-site-request-synchronizer-demo-0.0.1-SNAPSHOT.jar --server.port=8090`
 
