# JavaFX Library Management Application

This project is a JavaFX-based application designed to manage a library system. It demonstrates key JavaFX concepts such as stages, scenes, and data handling, while integrating other technologies like Maven, MySQL, and CSS for an enhanced user experience.

## Overview

This project serves as an example of how to build a desktop application using JavaFX. It covers creating user interfaces, handling user interactions, and managing data persistence through a MySQL database.

## JavaFX Framework

JavaFX is a framework for creating desktop applications in Java. It provides a rich set of UI controls, easy integration with CSS, and support for FXML to separate UI design from application logic.

### Key Concepts

- **Stage**: The main window of the application.
- **Scene**: The content within a stage. A stage can switch between different scenes to represent different views.
- **FXML**: An XML-based language used to define the UI structure.

## Example Application: Library Management System

This example application provides a simple system to administrate a library.

### Features

- **User Accounts**: Create and manage user accounts with different profiles.
- **Book Management**: Store, search, and manage books in a MySQL database.
- **User Roles**: Different user profiles, including administrator privileges.
- **Multi-Stage Workflow**: Separate stages to handle login, book management, and user administration.

## Technical Aspects

- **JavaFX Application**: Core framework for UI development.
- **Maven**: Handles project dependencies and build management.
- **Scene Builder**: Used to visually design the UI and generate FXML files.
- **CSS Integration**: Applies custom styling to enhance the application's appearance.
- **MySQL Database**: Utilizes `java.sql` package for database connectivity and CRUD operations.
- **Stage Communication**: Implements a gateway pattern to pass information between different stages.

## References

- [JavaFX Documentation](https://openjfx.io/)
- [Maven Documentation](https://maven.apache.org/)
- [Scene Builder](https://gluonhq.com/products/scene-builder/)
- [MySQL Connector for Java](https://dev.mysql.com/downloads/connector/j/)
