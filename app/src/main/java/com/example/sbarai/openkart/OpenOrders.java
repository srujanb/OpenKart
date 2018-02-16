package com.example.sbarai.openkart;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sbarai.openkart.Adapters.RvProspectOrderAdapter;
import com.example.sbarai.openkart.Models.ProspectOrder;
import com.example.sbarai.openkart.Utils.FirebaseManager;
import com.firebase.geofire.GeoFire;

import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpenOrders extends AppCompatActivity {

    FloatingActionButton createProspectOrder;
    FloatingActionButton testingButton;
    Toolbar toolbar;
    private FusedLocationProviderClient mFusedLocationClient;
    RecyclerView mRecyclerView;
    private RvProspectOrderAdapter adapter;
    DatabaseReference prospectOrdersReference;
    GeoFire geoFire;
    static List<String> data = Collections.emptyList();
    double fetchRadius = 2;
//    int totalKeysEntered;
//    Boolean isGeoQueryReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_orders);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        defineVariables();
        setFABListeners();

        initVariables();
        setRecyclerView();
        executeOneTimeLocationListener();
//        setRecyclerView();


    }

    private void executeOneTimeLocationListener() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Does not have Location permission", Toast.LENGTH_SHORT).show();
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            fetchData(location);
                        }
                    }
                });
    }

    private void initVariables() {
        mRecyclerView = findViewById(R.id.rv_open_orders);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geoFire = new GeoFire(FirebaseManager.getRefToGeofireForProspectOrders());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void defineVariables() {
        createProspectOrder = findViewById(R.id.menu_item_1);
        testingButton = findViewById(R.id.menu_item_2);
    }

    private void setFABListeners() {
        createProspectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpenOrders.this,CreateProspectOrder.class));
            }
        });

        testingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference ref = database.getReference();
//                ref.setValue("Hello");
            }
        });
    }

    public List<ProspectOrder> getData(){
        List<ProspectOrder> orders = new ArrayList<>();
        ProspectOrder order = new ProspectOrder();
        order.setDesiredStore("Walmart");
        ProspectOrder order2 = new ProspectOrder();
        order2.setDesiredStore("Cosco");
        ProspectOrder order3 = new ProspectOrder();
        order3.setDesiredStore("Apple bee");
        ProspectOrder order4 = new ProspectOrder();
        order4.setDesiredStore("NCSU store");
        ProspectOrder order5 = new ProspectOrder();
        order5.setDesiredStore("Walmart");

        orders.add(order);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);

        return orders;
    }

    public void fetchData(Location location){
        data = new ArrayList<>();
//        totalKeysEntered = 0;
//        isGeoQueryReady = false;
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(location.getLatitude(), location.getLongitude()), fetchRadius);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
//                totalKeysEntered++;
                Log.d("TAGG","onKeyEntered");
                insertIntoData(key);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
//                isGeoQueryReady = true;
                Log.d("TAGG","Geoquery ready");
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void insertIntoData(String key) {
//        data.add(key);
        adapter.insertIntoData(key);
    }

    public void setRecyclerView(){
//        if (data == null){
//            Toast.makeText(this, "Data is null", Toast.LENGTH_SHORT).show();
//        }else if (data.size() == 0){
//            Log.d("TAGG","setRecyclerView - data size: " + data.size());
//        } else {
            adapter = new RvProspectOrderAdapter(this, data);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        }
    }

    public void GO(View view) {
        EditText et = findViewById(R.id.radius);
        String string = et.getText().toString();
        fetchRadius = Double.parseDouble(string);
        executeOneTimeLocationListener();
    }
}
