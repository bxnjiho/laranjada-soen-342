package com.laranjada.models;

import java.time.LocalDateTime;

import com.laranjada.utils.ExpertiseArea;
import com.laranjada.utils.Type;

public class ServiceRequest {

    private String name;
    private Client client;
    private Expert expert;
    private ExpertiseArea expertise;
    private Type type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public ServiceRequest(String name, Client client, ExpertiseArea expertise, Type type, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.client = client;
        this.expertise = expertise;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setExpert(Expert expert){
        this.expert = expert;
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

    public LocalDateTime getStartDate(){
        return startDate;
    }

    public LocalDateTime getEndDate(){
        return endDate;
    }

}
