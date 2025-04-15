# laranjada-soen-342

# Demo:
https://github.com/user-attachments/assets/4071ac23-79fe-4182-9739-ef09bb3cb855

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
