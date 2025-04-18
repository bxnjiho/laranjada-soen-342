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
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    ownedByInstitution BOOLEAN DEFAULT FALSE,
    auctioned BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create auctionHouses table
CREATE TABLE IF NOT EXISTS auctionHouses(
    id INT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create auctions table
CREATE TABLE IF NOT EXISTS auctions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    auctionHouse_id INT NOT NULL,
    FOREIGN KEY (auctionHouse_id) REFERENCES auctionHouses(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create auctions-objects table
CREATE TABLE IF NOT EXISTS auctions_objects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    auction_id INT NOT NULL,
    object_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (auction_id) REFERENCES auctions(id),
    FOREIGN KEY (object_id) REFERENCES objects_of_interest(id)
);

-- Create service requests table
CREATE TABLE IF NOT EXISTS service_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    client_id INT NOT NULL,
    expert_id INT DEFAULT NULL,
    expertise VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (expert_id) REFERENCES experts(id)
);

-- Create auctions-servicerequests table
CREATE TABLE IF NOT EXISTS auctions_servicerequests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    auction_id INT NOT NULL,
    service_request_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (auction_id) REFERENCES auctions(id),
    FOREIGN KEY (service_request_id) REFERENCES service_requests(id)
);

-- Create availabilities table
CREATE TABLE IF NOT EXISTS availabilities(
    id INT AUTO_INCREMENT PRIMARY KEY, 
    expert_id INT NOT NULL,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    FOREIGN KEY (expert_id) REFERENCES experts(id)
);

-- Create DB user (used in env file)
CREATE USER IF NOT EXISTS 'laranjadauser'@'localhost' IDENTIFIED BY 'laranjadapass';

-- Grant privileges
GRANT ALL PRIVILEGES ON laranjadadb.* TO 'laranjadauser'@'localhost';
FLUSH PRIVILEGES;
