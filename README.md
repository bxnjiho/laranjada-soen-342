[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/-9QgYBSe)
# SOEN 342 

### Contributors:
- **Benjamin Ho**  
  Student ID: 40249917

- **Samuel Henderson**  
  Student ID: 40248526

### Prerequisites
- Java
- Maven
- MySQL
- A code editor such as Visual Studio Code or IntelliJ

### Local Database Setup:
- Run the following commands in your terminal:
    - `mysql -u root -p < setup.sql`
    - `mysql -u laranjadauser -p`
    - Enter the password `laranjadapass`
    - `USE laranjadadb;`

### Compile and Execute the Project:
- Run the following commands:
    - `mvn compile`
    - `mvn exec:java -Dexec.mainClass="com.laranjada.Main"`
