import java.util.Date;
import java.util.ArrayList;
public class Auction{
    Date date;
    String type;
    ArrayList<ObjectOfInterest> objectsAuctionned;
    ArrayList<ServiceRequest> serviceRequests;

    public Auction(Date date, String tyoe, ArrayList<ObjectOfInterest> objectsAuctionned, ArrayList<ServiceRequest> serviceRequests){
        this.date = date;
        this.type = type;
        this.objectsAuctionned = objectsAuctionned;
        this.serviceRequests = serviceRequests;
    }

    public Date getDate(){
        return date;
    }

    public String getType(){
        return type;
    }

    public ArrayList<ObjectOfInterst> getObjectsAuctionned(){
        return objectsAuctionned;
    }

    public ArrayList<ServiceRequest> getServiceRequest(){
        return serviceRequests;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setType(String type){
        this.type = type;
    }
}