package com.laranjada.models;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.laranjada.dao.AuctionDAO;
import com.laranjada.dao.AuctionHouseDAO;
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
            System.out.println("9. Add an object of interest to an Auction");
            System.out.println("10. Logout");
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
                case 6:
                    viewAuctionHouse();
                case 7:
                    createAuction();
                case 8:
                    viewAuctions();
                case 9:
                    addObjectOfInterestToAuction();
                case 10:
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

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Is this owned by an institution? (yes/no): ");
        boolean ownedByInstitution = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.print("Is this object being auctioned? (yes/no): ");
        boolean auctioned = scanner.nextLine().equalsIgnoreCase("yes");

        ObjectOfInterest object = new ObjectOfInterest(description, ownedByInstitution, auctioned);

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

        System.out.print("Enter Date of Auction: (yyyy-MM-dd)");
        String dateInput = scanner.nextLine();
        Date date = java.sql.Date.valueOf(dateInput); // works if input is "yyyy-MM-dd"

        System.out.print("Enter Type of Auction");
        String type = scanner.nextLine();

        System.out.print("Enter what Auction House this auction will be at");
        String auctionHouse = scanner.nextLine();
        int auctionHouse_id;
        try {
            auctionHouse_id = AuctionHouseDAO.getAuctionHouseIdByName(auctionHouse);
        } catch (Exception e) {
            System.out.println("Auction House doesn't exist");
            e.printStackTrace();
            return;
        }

        Auction auction = new Auction(date, type, auctionHouse_id);
        try {
            AuctionDAO.insertAuction(auction);
        } catch (Exception e) {
            System.out.print("Auction could not be added to the db");
        }
        System.out.println("Auction created successfully!");
    }

    private void addObjectOfInterestToAuction(){
        System.out.println("\n--- Add Object of Interest ---");

        System.out.print("Enter Name of Auction: ");
        String name = scanner.nextLine();

        System.out.print("Enter description of Object Of Interest");
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

        System.out.print("Enter City of Auction House");
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
}
