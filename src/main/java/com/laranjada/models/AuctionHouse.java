package com.laranjada.models;
import java.util.ArrayList;
public class AuctionHouse{

    String name;
    String city;
    ArrayList<Auction> auctions;

    public AuctionHouse(String name, String city, ArrayList<Auction> auctions){
        this.name = name;
        this.city = city;
        this.auctions = auctions;
    }

    public void addAuction(Auction auction){
        auctions.add(auction);
    }
}