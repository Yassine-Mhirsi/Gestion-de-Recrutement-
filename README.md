# Gestion-de-Recrutement
A Recruitment Management System built with Spring Boot that enables companies to post job listings and job seekers to browse and apply for positions.

## Features

- **Job Management**: Create, view, edit and delete job postings
- **Company Profiles**: Register and manage company information
- **Job Application System**: Allow users to apply for jobs with resume upload
- **User Authentication**: Secure login and registration system
- **Admin Dashboard**: Manage users, companies, and job listings

## Technologies Used

- **Backend**: Spring Boot, Spring MVC, Spring Data JPA, Spring Security
- **Frontend**: Thymeleaf, Bootstrap, jQuery
- **Database**: MySQL
- **Build Tool**: Maven
- **Java Version**: 17

## Setup Database

```bash
# Pull custom Docker image for this project
docker pull yassinemhirsi/gestion-de-recrutement:latest

# Run MySQL container
docker run -p 3307:3306 --name mysqlcontainer \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=RECRUTMENT \
  -d yassinemhirsi/gestion-de-recrutement:latest

# Access MySQL container shell
docker exec -it mysqlcontainer bash

# Login to MySQL
mysql -u root -p

# Enter password
root

# Use the database
use RECRUTMENT;
```

## How to Run

1. Clone the repository
2. Make sure you have Java 17 and Maven installed
3. Set up the MySQL database using the instructions above
4. Run the application using Maven:

```bash
mvn spring-boot:run
```

5. Access the application at `http://localhost:8080/jobs`
