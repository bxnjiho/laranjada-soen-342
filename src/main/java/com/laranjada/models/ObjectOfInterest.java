package com.laranjada.models;
public class ObjectOfInterest {
    private int id;
    private String name;
    private String description;
    private boolean ownedByInstitution;
    private boolean auctioned;

    // Constructor
    public ObjectOfInterest(String name, String description, boolean ownedByInstitution, boolean auctioned) {
        this.name = name;
        this.description = description;
        this.ownedByInstitution = ownedByInstitution;
        this.auctioned = auctioned;
    }

    public ObjectOfInterest(int id, String name, String description, boolean ownedByInstitution, boolean auctioned) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownedByInstitution = ownedByInstitution;
        this.auctioned = auctioned;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public int getId() {
        return id;
    }
    
    public boolean isOwnedByInstitution() {
        return ownedByInstitution;
    }

    public boolean isAuctioned() {
        return auctioned;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description + ", Owned by Institution: " + ownedByInstitution + ", Auctioned: " + auctioned;
    }
}