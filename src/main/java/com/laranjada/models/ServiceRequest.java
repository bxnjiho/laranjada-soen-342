package com.laranjada.models;

import java.util.Date;

import com.laranjada.utils.Type;

import com.laranjada.utils.ExpertiseArea;

public class ServiceRequest {

    private String name;
    private Client client;
    private Expert expert;
    private ExpertiseArea expertise;
    private Type type;
    private Boolean claimed;
    private Date date;
    
    public ServiceRequest(String name, Client client, ExpertiseArea expertise, Type type, Date date) {
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

    public String getName(){
        return name;
    }

    public Client getClient(){
        return client;
    }

    public Expert getExpert(){
        return expert;
    }

    public ExpertiseArea getExpertise(){
        return expertise;
    }

    public Type getType(){
        return type;
    }

    public Boolean isClaimed(){
        return claimed;
    }

    public Date getDate(){
        return date;
    }

}
