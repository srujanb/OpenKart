package com.example.sbarai.openkart.Models;

import java.util.List;

/**
 * Created by sbarai on 1/18/18.
 */

public class ProspectOrder {

    String poid;
    long dateTime;
    float loc_lat;
    float loc_lon;
    float colabRadius;
    String desiredStore;
    long orderDate;
    float targetTotal;
    List<Collaborator> collaborators;
    List<Comment> comments;
    int status;

}
