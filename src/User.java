import java.util.*;
public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private static final Scanner scanner = new Scanner(System.in);

    // Constructor
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters
    public String getEmail() {
        return this.email;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public static void logIn(Map<String, User> users, List<ObjectOfInterest> objectsOfInterest) {
        System.out.println("\n--- Log In ---");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(email);

        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("Invalid email or password.");
            return;
        }

        if (user instanceof Admin) {
            System.out.println("Admin logged in successfully!");
            ((Admin) user).adminMenu(users, objectsOfInterest);
        } else if (user instanceof Client) {
            Client client = (Client) user;
            if (!client.isAccountApproved()) {
                System.out.println("Your account is awaiting admin approval.");
                return;
            }
            System.out.println("Client logged in successfully!");
            userMenu(objectsOfInterest, user);
        } else if (user instanceof Expert) {
            System.out.println("Expert logged in successfully!");
            userMenu(objectsOfInterest);
        }
    }

    private static void userMenu(List<ObjectOfInterest> objectsOfInterest) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Objects of Interest");
            System.out.println("2. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewObjectsOfInterest(objectsOfInterest);
                    break;
                case 2:
                    System.out.println("User logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void viewObjectsOfInterest(List<ObjectOfInterest> objectsOfInterest) {
        System.out.println("\n--- Objects of Interest ---");

        if (objectsOfInterest.isEmpty()) {
            System.out.println("No objects available.");
            return;
        }

        for (ObjectOfInterest obj : objectsOfInterest) {
            System.out.println(obj);
        }
    }

    public static void viewAuctions(List<Auction> auctions) {
        System.out.println("\n--- Auctions ---");

        if (auctions.isEmpty()) {
            System.out.println("No Auctions.");
            return;
        }

        for (Auctions obj : auctions) {
            System.out.println(obj);
        }
    }

}