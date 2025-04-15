package com.laranjada.models;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.laranjada.dao.AuctionDAO;
import com.laranjada.dao.AuctionHouseDAO;
import com.laranjada.dao.AvailabilitiesDAO;
import com.laranjada.dao.ClientDAO;
import com.laranjada.dao.ExpertDAO;
import com.laranjada.dao.ObjectOfInterestDAO;

public class Admin extends User {

    private static final Scanner scanner = new Scanner(System.in);

    public Admin(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
    }

    public void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Approve Clients");
            System.out.println("2. Register an Expert");
            System.out.println("3. Create Object of Interest");
            System.out.println("4. View Objects of Interest");
            System.out.println("5. Create an Auction House");
            System.out.println("6. View Auction Houses");
            System.out.println("7. Create an Auction");
            System.out.println("8. View Auctions");
            System.out.println("9. Add an Object of Interest to an Auction");
            System.out.println("10. Add availabilities to expert");
            System.out.println("11. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    approveClients();
                    break;
                case 2:
                    registerExpert();
                    break;
                case 3:
                    createObjectOfInterest();
                    break;
                case 4:
                    viewObjectsOfInterest();
                    break;
                case 5:
                    createAuctionHouse();
                    break;
                case 6:
                    viewAuctionHouses();
                    break;
                case 7:
                    createAuction();
                    break;
                case 8:
                    viewAuctions();
                    break;
                case 9:
                    addObjectOfInterestToAuction();
                    break;
                case 10:
                    addExpertAvailability();
                    break;
                case 11:
                    System.out.println("Admin logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void approveClients() {
        System.out.println("\n--- Approve Clients ---");
        try {
            List<Client> pendingClients = ClientDAO.getUnapprovedClients();
            if (pendingClients.isEmpty()) {
                System.out.println("No clients awaiting approval.");
                return;
            }

            for (Client client : pendingClients) {
                System.out.println("Client: " + client.getEmail());
                System.out.print("Approve this client? (yes/no): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("yes")) {
                    client.setAccountApproved(true);
                    ClientDAO.updateClientApproval(client.getEmail(), true);
                    System.out.println(client.getEmail() + " has been approved!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching clients from database.");
            e.printStackTrace();
        }
    }

    private void registerExpert() {
        System.out.println("\n--- Register Expert ---");

        System.out.print("Enter expert email: ");
        String email = scanner.nextLine();

        try {
            if (ExpertDAO.getExpertByEmail(email) != null) {
                System.out.println("Email is already registered.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error checking existing expert.");
            e.printStackTrace();
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        while (!password.equals(confirmPassword)) {
            System.out.print("Confirm password: ");
            confirmPassword = scanner.nextLine();
        }

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter areas of expertise (comma-separated): ");
        String[] areasOfExpertise = scanner.nextLine().split(",");

        System.out.print("Enter license: ");
        String license = scanner.nextLine();

        Expert newExpert = new Expert(email, password, firstName, lastName, areasOfExpertise, license);

        try {
            ExpertDAO.insertExpert(newExpert);
            System.out.println("Expert registered successfully!");
        } catch (SQLException e) {
            System.out.println("Error saving expert to database.");
            e.printStackTrace();
        }
    }

    private void createObjectOfInterest() {
        System.out.println("\n--- Create Object of Interest ---");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Is this owned by an institution? (yes/no): ");
        boolean ownedByInstitution = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.print("Is this object being auctioned? (yes/no): ");
        boolean auctioned = scanner.nextLine().equalsIgnoreCase("yes");

        ObjectOfInterest object = new ObjectOfInterest(name, description, ownedByInstitution, auctioned);

        try {
            ObjectOfInterestDAO.insertObject(object);
            System.out.println("Object of Interest created successfully!");
        } catch (SQLException e) {
            System.out.println("Error saving object to database.");
            e.printStackTrace();
        }
    }

    
private void createAuction() {
    System.out.println("\n--- Create Auction ---");

    System.out.print("Enter name of Auction: ");
    String name = scanner.nextLine();

    System.out.print("Enter Type of Auction: ");
    String type = scanner.nextLine();

    System.out.print("Select the auction house where this auction will take place: ");
    String auctionHouse = scanner.nextLine();

    int auctionHouse_id;
    try {
        auctionHouse_id = AuctionHouseDAO.getAuctionHouseIdByName(auctionHouse);
    } catch (Exception e) {
        System.out.println("Auction House doesn't exist");
        e.printStackTrace();
        return;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    System.out.print("Enter the start Date and Time of Auction (yyyy-MM-dd HH:mm): ");
    String startDateInput = scanner.nextLine();

    LocalDateTime startDate;
    try {
        startDate = LocalDateTime.parse(startDateInput, formatter);
    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm");
        return;
    }

    System.out.print("Enter the end Date and Time of Auction (yyyy-MM-dd HH:mm): ");
    String endDateInput = scanner.nextLine();

    LocalDateTime endDate;
    try {
        endDate = LocalDateTime.parse(endDateInput, formatter);
    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm");
        return;
    }

    try {
        if (AuctionDAO.isAuctionDateTaken(startDate, endDate, auctionHouse_id)) {
            System.out.println("An auction is already scheduled at that time in this auction house.");
            return;
        }
    } catch (SQLException e) {
        System.out.println("Error checking for auction conflicts.");
        e.printStackTrace();
        return;
    }

    Auction auction = new Auction(name, startDate, endDate, type, auctionHouse_id);
    try {
        AuctionDAO.insertAuction(auction);
    } catch (Exception e) {
        System.out.println("Auction could not be added to the DB");
        e.printStackTrace();
        return;
    }

    System.out.println("Auction created successfully!");
}

    private void addObjectOfInterestToAuction(){
        System.out.println("\n--- Add Object of Interest ---");

        System.out.print("Enter the name of the Auction: ");
        String name = scanner.nextLine();

        System.out.print("Enter the description of the Object Of Interest: ");
        String description = scanner.nextLine();

        try {
            AuctionDAO.addObjectToAuction(name, description);
        } catch (Exception e) {
            System.out.println("Failed to add Object Of Interest to Auction");
        }

    }

    private void createAuctionHouse() {
        System.out.println("\n--- Create Auction House ---");

        System.out.print("Enter Name of Auction House: ");
        String name = scanner.nextLine();

        System.out.print("Enter City of Auction House: ");
        String city = scanner.nextLine();

        AuctionHouse auctionHouse = new AuctionHouse(name, city);

        try {
            AuctionHouseDAO.insertAuctionHouse(auctionHouse);
            System.out.println("Auction House created successfully!");
        } catch (SQLException e) {
            System.out.println("Error saving auction house to database.");
            e.printStackTrace();
        }
    }

    private static void addExpertAvailability() {
        System.out.println("\n--- Add Expert Availability ---");
    
        System.out.print("Enter Expert ID: ");
        int expertId = Integer.parseInt(scanner.nextLine());
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
        System.out.print("Enter Start DateTime (yyyy-MM-dd HH:mm): ");
        String startInput = scanner.nextLine();
    
        LocalDateTime start;
        try {
            start = LocalDateTime.parse(startInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid format. Please use: yyyy-MM-dd HH:mm");
            return;
        }
    
        System.out.print("Enter End DateTime (yyyy-MM-dd HH:mm): ");
        String endInput = scanner.nextLine();
    
        LocalDateTime end;
        try {
            end = LocalDateTime.parse(endInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid format. Please use: yyyy-MM-dd HH:mm");
            return;
        }
    
        Availability availability = new Availability(expertId, start, end);
    
        try {
            AvailabilitiesDAO.insertAvailability(availability);
            System.out.println("Availability added!");
        } catch (SQLException e) {
            System.out.println("Error saving availability to database.");
            e.printStackTrace();
        }
    }
    
    
}
