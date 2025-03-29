-- Create the database
CREATE DATABASE IF NOT EXISTS laranjadadb;
USE laranjadadb;

-- Create the clients table
CREATE TABLE IF NOT EXISTS clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    affiliation VARCHAR(255) NOT NULL,
    accountApproved BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the experts table
CREATE TABLE IF NOT EXISTS experts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    areasOfExpertise TEXT NOT NULL,
    licenseNumber VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the admins table
CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert admin account
INSERT INTO admins (email, password, firstname, lastname)
VALUES ('admin@example.com', '12345', 'Admin', 'Admin');

-- Create objects of interest table
CREATE TABLE IF NOT EXISTS objects_of_interest (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description TEXT NOT NULL,
    ownedByInstitution BOOLEAN DEFAULT FALSE,
    auctioned BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create DB user (used in env file)
CREATE USER IF NOT EXISTS 'laranjadauser'@'localhost' IDENTIFIED BY 'laranjadapass';

-- Grant privileges
GRANT ALL PRIVILEGES ON laranjadadb.* TO 'laranjadauser'@'localhost';
FLUSH PRIVILEGES;
