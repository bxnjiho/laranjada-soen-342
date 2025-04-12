import java.util.ArrayList;
public class AuctionHouse{

    int id;
    String name;
    String city;
    ArrayList<Auction> auctions;


    public AuctionHouse(String name, String city){
        this.name = name;
        this.city = city;
    }

    public AuctionHouse(int id, String name, String city, ArrayList<Auction> auctions){
        this.id = id;
        this.name = name;
        this.city = city;
        this.auctions = auctions;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void addAuction(Auction auction){
        auctions.add(auction);
    }

    public String getName(){
        return name;
    }

    public String getCity(){
        return city;
    }

    public ArrayList<Auction> getAuctions(){
        return auctions;
    }

    @Override
    public String toString(){
        return this.name + ", " + this.city;
    }
}