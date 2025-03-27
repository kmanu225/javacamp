# **JavaFX Library Management Application**

This project is a **JavaFX-based** application designed to manage a library system. It demonstrates key JavaFX concepts such as **stages, scenes, and data handling**. The goal of this project is to showcase various features of **JavaFX** for efficient code reuse.

## **Technical Aspects**  

- **JavaFX** – Core framework for UI development  
- **Maven** – Dependency and build management  
- **Scene Builder** – GUI designing tool (FXML generation)  
- **CSS Styling** – Enhances UI appearance  
- **MySQL Database** – Uses `java.sql` for database connectivity and CRUD operations  
- **Stage Communication** – Implements a **gateway pattern** for inter-stage communication 

## **Features**  

- **User Accounts** – Create and manage user profiles  
- **Book Management** – Store, search, and manage books in a MySQL database  
- **User Roles** – Define different access levels, including administrator privileges  
- **Multi-Stage Workflow** – Separate windows for login, book management, and user administration  


## **How to Use?**  

### **1️⃣ Build the Project**  

Run the following command to build the project using **Maven**:  

```sh
mvn clean install
```

### **2️⃣ Run the Application**  

To execute the JAR file, use the following command:  

```sh
java --module-path "javafx-sdk-<version>/lib" \
     --add-modules javafx.controls,javafx.base,javafx.fxml,javafx.graphics,javafx.media,javafx.web \
     --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED \
     --add-exports javafx.base/com.sun.javafx.event=ALL-UNNAMED \
     -jar target/Library-<version>.jar
```

`javafx-sdk-<version>` can be downloaded from the project [Gluon](https://gluonhq.com/products/javafx/).

**Note**:  

- Replace `<version>` with the actual **JavaFX SDK** and **Library version** you are using.  
- Ensure that the **JavaFX SDK** is properly installed and the module path is correctly set.  

## **References**  

- 📌 [Maven Documentation](https://maven.apache.org/)  
- 📌 [Scene Builder](https://gluonhq.com/products/scene-builder/)  
- 📌 [MySQL Connector for Java](https://dev.mysql.com/downloads/connector/j/)  

