package com.laranjada.models;

import java.time.LocalDateTime;

public class Availability {
    private int id;
    private int expertId;
    private LocalDateTime start;
    private LocalDateTime end;

    public Availability(int expertId, LocalDateTime start, LocalDateTime end) {
        this.expertId = expertId;
        this.start = start;
        this.end = end;
    }

    public Availability(int id, int expertId, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.expertId = expertId;
        this.start = start;
        this.end = end;
    }


    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getExpertId(){
        return expertId;
    }

    @Override
    public String toString() {
        return "Available from " + start + " to " + end;
    }
}
