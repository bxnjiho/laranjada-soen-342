package com.laranjada.models;
import java.util.ArrayList;
import java.util.Date;
public class Auction{
    int id;
    Date date;
    String type;
    int auctionHouse_id;
    ArrayList<ObjectOfInterest> objectsAuctionned;
    ArrayList<ServiceRequest> serviceRequests;

    public Auction(Date date, String type, int auctionHouse_id){
        this.date = date;
        this.type = type;
        this.auctionHouse_id = auctionHouse_id;
    }

    public Auction(Date date, String type, int auctionHouse_id, ArrayList<ObjectOfInterest> objectsAuctionned, ArrayList<ServiceRequest> serviceRequests){
        this.date = date;
        this.type = type;
        this.auctionHouse_id = auctionHouse_id;
        this.objectsAuctionned = objectsAuctionned;
        this.serviceRequests = serviceRequests;
    }

    public Auction(int id, Date date, String type, int auctionHouse_id, ArrayList<ObjectOfInterest> objectsAuctionned, ArrayList<ServiceRequest> serviceRequests){
        this.id = id;
        this.date = date;
        this.auctionHouse_id = auctionHouse_id;
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

    public int getId(){
        return id;
    }

    public int getAuctionHouseId(){
        return auctionHouse_id;
    }

    public ArrayList<ObjectOfInterest> getObjectsAuctionned(){
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