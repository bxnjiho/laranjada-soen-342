import java.util.Date;
public class ServiceRequest {

    String name;
    Client client;
    Expert expert;
    String expertise;
    String type;
    Boolean claimed;
    Date date;
    
    public ServiceRequest(String name, Client client, String expertise, String type, Date date) {
        this.name = name;
        this.client = client;
        this.expertise = expertise;
        this.type = type;
        this.claimed = false;
        this.date = date;
    }

    public void setExpert(Expert expert){
        this.expert = expert;
    }
    public void setClaimed(Boolean claimed){
        this.claimed = claimed;
    }

    public void assignExpert( Expert expert){
        setClaimed(true);
        setExpert(expert);
    }

}
