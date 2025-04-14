package com.laranjada.models;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.laranjada.dao.AvailabilitiesDAO;
import com.laranjada.dao.ServiceRequestDAO;
public class Expert extends User {
    private int id;
    private String[] areasOfExpertise;
    private String license;
    private ArrayList<Availability> availabilities = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    // Constructor
    public Expert(String email, String password, String firstName, String lastName, String[] areasOfExpertise, String license) {
        super(email, password, firstName, lastName);
        this.areasOfExpertise = areasOfExpertise;
        this.license = license;
    }

    // Constructor with id
    public Expert(int id, String email, String password, String firstName, String lastName, String[] areasOfExpertise, String license) {
        super(email, password, firstName, lastName);
        this.id = id;
        this.areasOfExpertise = areasOfExpertise;
        this.license = license;
    }

    // Getters
    public String[] getAreasOfExpertise() {
        return areasOfExpertise;
    }

    public String getLicenseNumber() {
        return license;
    }

    public void setAvailabilities(ArrayList<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public ArrayList<Availability> getAvailabilies(){
        return availabilities;
    }

    public void addAvailability(Availability availability) {
        availabilities.add(availability);
    }

    public int getId() {
        return id;
    }    

    public void expertMenu(Expert expert) {
        while (true) {
            System.out.println("\n--- Expert Menu ---");
            System.out.println("1. View Objects of Interest");
            System.out.println("2. View Auctions");
            System.out.println("3. View Service Requests");
            System.out.println("4. Add to my availabilities");
            System.out.println("5. Logout");
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
                    viewServiceRequests();
                    break;
                case 4:
                    addToAvailabilities();
                    break;
                case 5:
                    System.out.println("Client logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void viewServiceRequests() {
        System.out.println("\n--- My Service Requests ---");

        try {
            List<ServiceRequest> requests = ServiceRequestDAO.getServiceRequestsByExpertId(this.getId());

            if (requests.isEmpty()) {
                System.out.println("You have no assigned service requests.");
                return;
            }

            for (ServiceRequest sr : requests) {
                System.out.println("Service Name: " + sr.getName());
                System.out.println("Client: " + sr.getClient().getFullName());
                System.out.println("Expertise: " + sr.getExpertise());
                System.out.println("Type: " + sr.getType());
                System.out.println("Start: " + sr.getStartDate());
                System.out.println("End: " + sr.getEndDate());
                System.out.println("-------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching service requests.");
            e.printStackTrace();
        }
    }

    public void addToAvailabilities() {
    System.out.println("\n--- Add Availability ---");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    try {
        System.out.print("Enter availability START datetime (yyyy-MM-dd HH:mm): ");
        String startInput = scanner.nextLine();
        LocalDateTime start = LocalDateTime.parse(startInput, formatter);

        System.out.print("Enter availability END datetime (yyyy-MM-dd HH:mm): ");
        String endInput = scanner.nextLine();
        LocalDateTime end = LocalDateTime.parse(endInput, formatter);

        if (end.isBefore(start)) {
            System.out.println("End time must be after start time.");
            return;
        }

        Availability availability = new Availability(getId(), start, end);

        // Add to database
        AvailabilitiesDAO.insertAvailability(availability);

        // Add to local list
        this.addAvailability(availability);

        System.out.println("Availability successfully added.");

    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm.");
    } catch (SQLException e) {
        System.out.println("Failed to save availability to the database.");
        e.printStackTrace();
    }
}
}