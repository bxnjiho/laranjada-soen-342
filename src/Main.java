import java.util.*;

public class Main {

    /*
    User credentials
    ------------------
    Admin --> email: admin, password: test123
    Expert --> email: expert@gmail.com, password: test123
    Client --> email: client@gmail.com, password: test123
     */

    private static final Scanner scanner = new Scanner(System.in);

    // Temporary way to store users
    private static final Map<String, User> users = new HashMap<>();

    //Temporary way to store objects of interest
    private static final List<ObjectOfInterest> objectsOfInterest = new ArrayList<>();

    //Predefined admin account
    private static final Admin admin = new Admin("admin", "test123", "Admin", "User");

    public static void main(String[] args) {
        // Add admin to users
        users.put(admin.getEmail(), admin);

        System.out.println("Welcome to the Institution!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Sign Up (Client only)");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Client.signUpClient(users);
                    break;
                case 2:
                    User.logIn(users, objectsOfInterest);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

}