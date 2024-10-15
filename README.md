# Project 2
# Athlete and Coach Management in Gymnasium

This project was developed as part of the Project 2 curricular unit at the Polytechnic Institute of Viana do Castelo. The aim is to create a management system for athletes and coaches in a gym, available in desktop and web versions.

## Technologies Used

- PostgreSQL
- Java
- Java Persistence API (JPA)
- JavaFX
- Spring Web MVC and Thymeleaf

## Project Structure

### Database

The first step was to create the database in PostgreSQL. The database structure was designed to store information about athletes, coaches, classes and the associations between them.

### Entities and Persistence

Using the Java Persistence API (JPA), we created the entities that represent the database tables. These entities were then mapped to Java classes that facilitate interaction with the database.



### Desktop Version

The desktop version was developed using JavaFX to create a rich and interactive graphical interface. The desktop application allows users to:

- Manage athlete and coach information
- Schedule, edit and cancel classes
- View timetables and availability

### Web version

The web version was developed using Spring Web MVC and Thymeleaf. This version offers similar functionalities to the desktop version, allowing users to manage information and timetables via a web browser.

## Features

### Classes

- Schedule Classes**: Allows trainers to schedule new classes, specifying the name, date, time, duration and location.
- Manage Lessons**: Allows you to edit and cancel lessons that have already been scheduled.
- View Classes**: Displays a list of classes with details such as date, time, discipline, location, type of class, participating member and state.

### Coaches and Athletes

- Coach Management**: Adding, editing and removing coach information.
- Athlete Management Adding, editing and removing athlete information.
- Class Association**: Associate athletes with individual or group classes.

Developed by: Francisco Santos [@FranciscoSantos1](https://github.com/FranciscoSantos1), João Araújo [jlimaaraujo](https://github.com/jlimaaraujo)

Polytechnic Institute of Viana do Castelo (IPVC) - 2024
