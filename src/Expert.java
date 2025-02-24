public class Expert extends User {
    private String[] areasOfExpertise;
    private String license;

    // Constructor
    public Expert(String email, String password, String firstName, String lastName, String[] areasOfExpertise, String license) {
        super(email, password, firstName, lastName);
        this.areasOfExpertise = areasOfExpertise;
        this.license = license;
    }
}