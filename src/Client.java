import java.util.*;

public class Client extends User {
    private String affiliation;
    private boolean accountApproved;
    private static final Scanner scanner = new Scanner(System.in);

    // Contructor
    public Client(String email, String password, String firstName, String lastName, String affiliation, boolean accountApproved) {
        super(email, password, firstName, lastName);
        this.affiliation = affiliation;
        this.accountApproved = accountApproved;
    }

    // Getters
    public boolean isAccountApproved() {
        return accountApproved;
    }

    // Setters
    public void setAccountApproved(boolean accountApproved) {
        this.accountApproved = accountApproved;
    }

    public static void signUpClient(Map<String, User> users) {
        System.out.println("\n--- Client Sign Up ---");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (users.containsKey(email)) {
            System.out.println("Email is already registered. Try logging in.");
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

        System.out.print("Enter affiliation: ");
        String affiliation = scanner.nextLine();

        Client newClient = new Client(email, password, firstName, lastName, affiliation, false); // Needs approval
        users.put(email, newClient);

        System.out.println("Client registered successfully! Waiting for admin approval.");
    }
}