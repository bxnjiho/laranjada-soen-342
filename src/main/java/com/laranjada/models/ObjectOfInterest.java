package com.laranjada.models;
public class ObjectOfInterest {
    private String description;
    private boolean ownedByInstitution;
    private boolean auctioned;

    // Constructor
    public ObjectOfInterest(String description, boolean ownedByInstitution, boolean auctioned) {
        this.description = description;
        this.ownedByInstitution = ownedByInstitution;
        this.auctioned = auctioned;
    }

    // Getters
    public String getDescription() {
        return description;
    }

    public boolean isOwnedByInstitution() {
        return ownedByInstitution;
    }

    public boolean isAuctioned() {
        return auctioned;
    }

    @Override
    public String toString() {
        return "Description: " + description + ", Owned by Institution: " + ownedByInstitution + ", Auctioned: " + auctioned;
    }
}