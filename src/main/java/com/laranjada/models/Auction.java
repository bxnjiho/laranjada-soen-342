package com.laranjada.models;
import java.util.ArrayList;
import java.time.LocalDateTime;

import com.laranjada.dao.AuctionHouseDAO;
public class Auction{
    int id;
    String name;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String type;
    int auctionHouse_id;
    ArrayList<ObjectOfInterest> objectsAuctionned;
    ArrayList<ServiceRequest> serviceRequests;

    public Auction(String name, LocalDateTime startDate, LocalDateTime endDate, String type, int auctionHouse_id){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.auctionHouse_id = auctionHouse_id;
    }

    public Auction(String name, LocalDateTime startDate, LocalDateTime endDate, String type, int auctionHouse_id, ArrayList<ObjectOfInterest> objectsAuctionned, ArrayList<ServiceRequest> serviceRequests){
        this.name = name;
        this.startDate =  startDate;
        this.endDate = endDate;
        this.type = type;
        this.objectsAuctionned = objectsAuctionned;
        this.serviceRequests = serviceRequests;
    }

    public Auction(int id, String name, LocalDateTime starDate, LocalDateTime endDate, String type, int auctionHouse_id, ArrayList<ObjectOfInterest> objectsAuctionned, ArrayList<ServiceRequest> serviceRequests){
        this.id = id;
        this.name = name;
        this.startDate = starDate;
        this.endDate = endDate;
        this.type = type;
        this.objectsAuctionned = objectsAuctionned;
        this.serviceRequests = serviceRequests;
    }

    public int getId(){
        return id;
    }

    public LocalDateTime getStartDate(){
        return startDate;
    }

    public LocalDateTime getEndDate(){
        return endDate;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return name;
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

    public void setStartDate(LocalDateTime starDate){
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate){
        this.endDate = endDate;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return type + ", " + name + ", " + startDate + ", " + endDate + ", " + auctionHouse_id;
    }
}