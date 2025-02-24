import java.util.*;

public class Admin extends User {

    private static final Scanner scanner = new Scanner(System.in);

    // Constructor
    public Admin(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
    }

    public void adminMenu(Map<String, User> users, List<ObjectOfInterest> objectsOfInterest) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Approve Clients");
            System.out.println("2. Register an Expert");
            System.out.println("3. Create Object of Interest");
            System.out.println("4. View Objects of Interest");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    approveClients(users);
                    break;
                case 2:
                    registerExpert(users);
                    break;
                case 3:
                    createObjectOfInterest(objectsOfInterest);
                    break;
                case 4:
                    viewObjectsOfInterest(objectsOfInterest);
                    break;
                case 5:
                    System.out.println("Admin logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void approveClients(Map<String, User> users) {
        System.out.println("\n--- Approve Clients ---");

        boolean found = false;
        for (User user : users.values()) {
            if (user instanceof Client) {
                Client client = (Client) user;
                if (!client.isAccountApproved()) {
                    System.out.println("Client: " + client.getEmail());
                    System.out.print("Approve this client? (yes/no): ");
                    String input = scanner.nextLine();

                    if (input.equalsIgnoreCase("yes")) {
                        client.setAccountApproved(true);
                        System.out.println(client.getEmail() + " has been approved!");
                    }
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No clients awaiting approval.");
        }
    }

    private void registerExpert(Map<String, User> users) {
        System.out.println("\n--- Register Expert ---");

        System.out.print("Enter expert email: ");
        String email = scanner.nextLine();

        if (users.containsKey(email)) {
            System.out.println("Email is already registered.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        while(!(password.equals(confirmPassword))) {
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
        users.put(email, newExpert);

        System.out.println("Expert registered successfully!");
    }

    private void createObjectOfInterest(List<ObjectOfInterest> objectsOfInterest) {
        System.out.println("\n--- Create Object of Interest ---");

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Is this owned by an institution? (yes/no): ");
        boolean ownedByInstitution = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.print("Is this object being auctioned? (yes/no): ");
        boolean auctioned = scanner.nextLine().equalsIgnoreCase("yes");

        ObjectOfInterest object = new ObjectOfInterest(description, ownedByInstitution, auctioned);
        objectsOfInterest.add(object);

        System.out.println("Object of Interest created successfully!");
    }
}