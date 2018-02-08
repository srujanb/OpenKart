package com.example.sbarai.openkart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.sbarai.openkart.Adapters.RvProspectOrderAdapter;
import com.example.sbarai.openkart.Models.ProspectOrder;
import com.example.sbarai.openkart.Utils.FirebaseManager;
import com.firebase.geofire.GeoFire;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class OpenOrders extends AppCompatActivity {

    FloatingActionButton createProspectOrder;
    FloatingActionButton testingButton;
    Toolbar toolbar;

    RecyclerView mRecyclerView;
    private RvProspectOrderAdapter adapter;
    DatabaseReference prospectOrdersReference;
    GeoFire geoFire;

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

    }

    private void initVariables() {
        mRecyclerView = findViewById(R.id.rv_open_orders);
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

    public void setRecyclerView(){
        adapter = new RvProspectOrderAdapter(this,getData());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
