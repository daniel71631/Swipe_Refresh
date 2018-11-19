package com.example.daniel.swipe_refresh;

public class Metro_Cons {

    public String station;
    public String destination;
    public String arrive_time;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public Metro_Cons(String station, String destination, String arrive_time) {
        this.station = station;
        this.destination = destination;
        this.arrive_time = arrive_time;
    }
}
