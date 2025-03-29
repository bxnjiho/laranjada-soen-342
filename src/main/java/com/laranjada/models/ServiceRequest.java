package com.laranjada.models;
import java.util.Date;

public class ServiceRequest {

    private String name;
    private Client client;
    private Expert expert;
    private String expertise;
    private String type;
    private Boolean claimed;
    private Date date;
    
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
