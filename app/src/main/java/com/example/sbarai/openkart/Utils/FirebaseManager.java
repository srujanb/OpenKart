package com.example.sbarai.openkart.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sbarai on 1/26/18.
 */

public class FirebaseManager {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static DatabaseReference getRootRef(){
        return database.getReference();
    }

    public static DatabaseReference getRefToAppData(){
        DatabaseReference ref = getRootRef();
        return ref.child(Constants.FirebaseManager.APP_DATA);
    }

    public static DatabaseReference getRefToOpenOrders(){
        DatabaseReference ref = getRefToAppData();
        return ref.child(Constants.FirebaseManager.OPEN_ORDERS);
    }

    public static DatabaseReference getRefToProspectOrders(){
        DatabaseReference ref = getRefToOpenOrders();
        return ref.child(Constants.FirebaseManager.PROSPECT_ORDERS);
    }

    public static DatabaseReference getRefToSpecificProspectOrder(String key){
        DatabaseReference ref = getRefToProspectOrders();
        return ref.child(key);
    }

    public static DatabaseReference getRefToGeofireForProspectOrders(){
        DatabaseReference ref = getRefToOpenOrders();
        return ref.child(Constants.FirebaseManager.GEO_FIRE);
    }

}
