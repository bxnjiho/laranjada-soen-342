package com.laranjada.models;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.laranjada.dao.AvailabilitiesDAO;
import com.laranjada.dao.ClientDAO;
import com.laranjada.dao.ExpertDAO;
import com.laranjada.dao.ServiceRequestDAO;
import com.laranjada.utils.ExpertiseArea;
import com.laranjada.utils.Type;

public class Client extends User {
    private int id;
    private String affiliation;
    private boolean accountApproved;
    private static final Scanner scanner = new Scanner(System.in);

    // Constructor 
    public Client(String email, String password, String firstName, String lastName, String affiliation, boolean accountApproved) {
        super(email, password, firstName, lastName);
        this.affiliation = affiliation;
        this.accountApproved = accountApproved;
    }

    // Contructor with id
    public Client(int id, String email, String password, String firstName, String lastName, String affiliation, boolean accountApproved) {
        super(email, password, firstName, lastName);
        this.id = id;
        this.affiliation = affiliation;
        this.accountApproved = accountApproved;
    }

    // Getters
    public boolean isAccountApproved() {
        return accountApproved;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public int getId() {
        return id;
    }

    // Setters
    public void setAccountApproved(boolean accountApproved) {
        this.accountApproved = accountApproved;
    }

    public static void signUpClient() {
        System.out.println("\n--- Client Sign Up ---");

        try {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            if (ClientDAO.getClientByEmail(email) != null) {
                System.out.println("Email is already registered. Try logging in.");
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

            System.out.print("Enter affiliation: ");
            String affiliation = scanner.nextLine();

            Client newClient = new Client(email, password, firstName, lastName, affiliation, false);
            ClientDAO.insertClient(newClient);

            System.out.println("Client registered successfully! Waiting for admin approval.");

        } catch (SQLException e) {
            System.out.println("Error during client registration.");
            e.printStackTrace();
        }
    }

    public void clientMenu(Client client) {
        while (true) {
            System.out.println("\n--- Client Menu ---");
            System.out.println("1. View Objects of Interest");
            System.out.println("2. View Auctions");
            System.out.println("3. Make Service Request");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewObjectsOfInterest();
                    break;
                case 2:
                    viewAuctions();
                    break;
                case 3:
                    createServiceRequest(client);
                    break;
                case 4:
                    System.out.println("Client logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    
    public static int getMatchingExpertId(LocalDateTime start, LocalDateTime end, ExpertiseArea requiredExpertise) throws SQLException {
        List<Expert> experts = ExpertDAO.getAllExperts();

        for (Expert expert : experts) {
            // Check if the expert has the required expertise
            for (String expertiseStr : expert.getAreasOfExpertise()) {
                if (expertiseStr.equalsIgnoreCase(requiredExpertise.name())) {

                    // Check their availabilities
                    List<Availability> availabilities = AvailabilitiesDAO.getAvailabilitiesByExpertId(expert.getId());
                    for (Availability a : availabilities) {
                        boolean fullyAvailable =
                            !a.getStart().isAfter(start) && !a.getEnd().isBefore(end);

                        if (fullyAvailable) {
                            return expert.getId(); // ✅ Match found
                        }
                    }
                }
            }
        }

        return -1; // ❌ No expert found with matching expertise + availability
    }

    private static void createServiceRequest(Client client) {
        System.out.println("\n--- Service Request Form ---");

        String name = client.getFullName() + "'s Service Request";

        System.out.println("Please specify the expertise required for this service request.");
        System.out.println("Options available: [ART_HISTORY, ANTIQUES, RARE_BOOKS, PAINTINGS, JEWELRY, FURNITURE]");
        ExpertiseArea expertise = null;
        while (expertise == null) {
            System.out.print("Enter expertise: ");
            String input = scanner.nextLine().toUpperCase();
            try {
                expertise = ExpertiseArea.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid expertise. Please try again.");
            }
        }

        System.out.println("Please specify the type - [ONE, TWO, THREE]:");
        Type type = null;
        while (type == null) {
            System.out.print("Enter type: ");
            String input = scanner.nextLine().toUpperCase();
            try {
                type = Type.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type. Please try again.");
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.print("Enter the start date and time (yyyy-MM-dd HH:mm): ");
        LocalDateTime startDate;
        try {
            String startStr = scanner.nextLine();
            startDate = LocalDateTime.parse(startStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid start date format. Please use yyyy-MM-dd HH:mm.");
            return;
        }

        System.out.print("Enter the end date and time (yyyy-MM-dd HH:mm): ");
        LocalDateTime endDate;
        try {
            String endStr = scanner.nextLine();
            endDate = LocalDateTime.parse(endStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid end date format. Please use yyyy-MM-dd HH:mm.");
            return;
        }



        ServiceRequest serviceRequest = new ServiceRequest(name, client, expertise, type, startDate, endDate);

        try {
            ServiceRequestDAO.insertServiceRequest(serviceRequest);
            System.out.println("Service Request saved");
        } catch (SQLException e) {
            System.out.println("Failed to save service request to the database.");
            e.printStackTrace();
        }
    }


}