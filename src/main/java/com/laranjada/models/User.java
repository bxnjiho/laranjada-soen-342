package com.laranjada.models;

import com.laranjada.dao.AdminDAO;
import com.laranjada.dao.ClientDAO;
import com.laranjada.dao.ExpertDAO;
import com.laranjada.dao.ObjectOfInterestDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private static final Scanner scanner = new Scanner(System.in);

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public static void logIn() {
        System.out.println("\n--- Log In ---");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            // Check Admin
            Admin admin = AdminDAO.getAdminByEmail(email);
            if (admin != null && admin.getPassword().equals(password)) {
                System.out.println("Admin logged in successfully!");
                admin.adminMenu();
                return;
            }

            // Check Client
            Client client = ClientDAO.getClientByEmail(email);
            if (client != null && client.getPassword().equals(password)) {
                if (!client.isAccountApproved()) {
                    System.out.println("Your account is awaiting admin approval.");
                    return;
                }
                System.out.println("Client logged in successfully!");
                userMenu();
                return;
            }

            // Check Expert
            Expert expert = ExpertDAO.getExpertByEmail(email);
            if (expert != null && expert.getPassword().equals(password)) {
                System.out.println("Expert logged in successfully!");
                userMenu();
                return;
            }

            System.out.println("Invalid email or password.");

        } catch (Exception e) {
            System.out.println("An error occurred while logging in:");
            e.printStackTrace();
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Objects of Interest");
            System.out.println("2. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewObjectsOfInterest();
                    break;
                case 2:
                    System.out.println("User logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void viewObjectsOfInterest() {
        System.out.println("\n--- Objects of Interest ---");

        try {
            List<ObjectOfInterest> objects = ObjectOfInterestDAO.getAllObjects();

            if (objects.isEmpty()) {
                System.out.println("No objects available.");
                return;
            }

            for (ObjectOfInterest obj : objects) {
                System.out.println(obj);
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch objects from database.");
            e.printStackTrace();
        }
    }
}
