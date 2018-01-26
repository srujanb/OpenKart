package com.example.sbarai.openkart.Models;

import android.location.Location;

import java.util.List;

/**
 * Created by sbarai on 1/18/18.
 */

public class ProspectOrder {

    private String poid;
    private long dateTime; //Will be stored as milliseconds on server therefore long.
    private double loc_lat;
    private double loc_lon;
    private float colabRadius;
    private String desiredStore;
    private String orderDate; //Can be easily converted to String from java.time
    private float targetTotal;
    private List<Collaborator> collaborators;
    private List<Comment> comments;
    private int status;

    public void setLocation(Location location){
        loc_lat = location.getLatitude();
        loc_lon = location.getLongitude();
    }

    public Location getLocation(){
        Location location = new Location("");
        location.setLatitude(loc_lat);
        location.setLongitude(loc_lon);
        return location;
    }

    public void setColabRadius(float radius){
        colabRadius = radius;
    }

    public float getColabRadius(){
        return colabRadius;
    }

    public void setDesiredStore(String store){
        desiredStore = store;
    }

    public String getDesiredStore(){
        return desiredStore;
    }

    public void setOrderDate(String date){
        orderDate = date;
    }

    public String getOrderDate(){
        return orderDate;
    }

    public float getTargetTotal() {
        return targetTotal;
    }

    public void setTargetTotal(float targetTotal) {
        this.targetTotal = targetTotal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
