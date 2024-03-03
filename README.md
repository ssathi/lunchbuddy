# lunchbuddy
Sample Application to choose a restaurent for having lunch.


# Dependencies
1. JDK 17
2. MySQL 8 or later


# ER Diagram
<img width="928" alt="Screenshot 2024-03-03 at 4 22 34 PM" src="https://github.com/ssathi/lunchbuddy/assets/42490965/d2afc8e0-d683-4bd8-b326-e1abb0889e9b">


# Api Documents Swagger and OpenAPI

swagger - http://localhost:8080/swagger-ui/index.html
<img width="1123" alt="Screenshot 2024-03-03 at 5 09 07 PM" src="https://github.com/ssathi/lunchbuddy/assets/42490965/c061e81d-3ec9-42e9-b19e-b0455526260c">



openapi - http://localhost:8080/v3/api-docs
<img width="606" alt="Screenshot 2024-03-03 at 5 07 43 PM" src="https://github.com/ssathi/lunchbuddy/assets/42490965/c705824c-5b31-4153-a1a5-150f05cfafa2">


# Authentication
For the purpose of user identity, I have implemented a basic authentication using spring security and jwt. This is a bare minimum implementation of authentication as It is not the focus of this assignemnt


# Running the application

1. clone the repo
git clone https://github.com/ssathi/lunchbuddy.git

2. Edit the application.properties to change the db credentials.
At src/main/resources/application.properties
  spring.datasource.url=jdbc:mysql://localhost:3306/lunchbuddy
  spring.datasource.username=root
  spring.datasource.password=root1234

3. Test the application


./mvnw clean test

4. Build the application

./mvnw clean package

5. Run the application

java -jar target/lunchbuddy-0.0.1-SNAPSHOT.jar


# Apis

1. Initial Data
4 resturans are inserted into db as an initial data. more can be inserted using the api.


2. Auth token
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "Admin",
    "password": "admin"
}'

Response: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBZG1pbiJ9.StbHyYkYHU5XC9MYQQuESIT8qMv4WHnSJGaAfttDpic


3. Start a session

curl --location 'http://localhost:8080/sessions/start' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBZG1pbiJ9.StbHyYkYHU5XC9MYQQuESIT8qMv4WHnSJGaAfttDpic' \
--header 'Content-Type: application/json' \
--data '{
    
    "sessionInvites": [
        {
            "username": "Satthi"
        }
    ]
}'

Response:
<img width="638" alt="Screenshot 2024-03-03 at 4 50 49 PM" src="https://github.com/ssathi/lunchbuddy/assets/42490965/5fb62d63-bc6d-4e8f-8a65-fda60be15860">


4. Joining a session

curl --location 'http://localhost:8080/sessions/1/join' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXR0aGkifQ.p5FcmsldD21szGHOtSb_7EP43_30zWFKFiJDxcr71Ck' \
--header 'Content-Type: application/json' \
--data '{
    "sessionInvites": [
        {
            "username": "Satthi"
        }
    ]
}'

Response:
<img width="969" alt="Screenshot 2024-03-03 at 4 51 47 PM" src="https://github.com/ssathi/lunchbuddy/assets/42490965/332c3bee-e292-484f-b320-32004419a6dd">


4. Voting for a session

curl --location 'http://localhost:8080/sessions/1/vote/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXR0aGkifQ.p5FcmsldD21szGHOtSb_7EP43_30zWFKFiJDxcr71Ck' \
--header 'Content-Type: application/json' \
--data '{
    "sessionInvites": [
        {
            "username": "Satthi"
        }
    ]
}'

Response:
<img width="985" alt="Screenshot 2024-03-03 at 4 52 33 PM" src="https://github.com/ssathi/lunchbuddy/assets/42490965/ccf45a51-6bfa-46b5-b013-24fd3ce8c9a9">


5. Ending a session

curl --location 'http://localhost:8080/sessions/1/end' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBZG1pbiJ9.StbHyYkYHU5XC9MYQQuESIT8qMv4WHnSJGaAfttDpic' \
--header 'Content-Type: application/json' \
--data '{
    "sessionInvites": [
        {
            "username": "Satthi"
        }
    ]
}'

Response:
<img width="935" alt="Screenshot 2024-03-03 at 4 53 20 PM" src="https://github.com/ssathi/lunchbuddy/assets/42490965/132006e5-ffee-4772-ae10-18eec6b9aa17">



# Questions
1. How would you ensure that one user’s submitted location does not cause issues on
other users’ displays?

User responses has one to many relationships with the session. As such, other users will not cause issues to other users.

2. How would you assure others that your application meets the requirements, and
continues to meet the requirements even after changes have been made to it?

Enough test cases are writting so it will help us to make sure the requirements are met even when the applications goes though several changes.

3. How would you ensure that your application can be deployed to serve an increasing
number of users who may be concurrently using your service?

It is a web application. so many users can access the application concurrently. It can scalled by containerising and running it on k8s.

4. How would you help a fellow team member who may need to debug your application
in future?

Errors are handled properly. It will give a good error message. The code is written in a way it is not complex and it is readable so other developers can undertand without issues.
Comments are made in certain places to give more details of the functionality.

api document is attached above. 






