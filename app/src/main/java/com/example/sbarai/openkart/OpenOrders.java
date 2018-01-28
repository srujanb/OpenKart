package com.example.sbarai.openkart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sbarai.openkart.Models.ProspectOrder;
import com.example.sbarai.openkart.Utils.FirebaseManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class OpenOrders extends AppCompatActivity {

    FloatingActionButton createProspectOrder;
    FloatingActionButton testingButton;
    Toolbar toolbar;

    RecyclerView mRecyclerView;
    FirebaseRecyclerAdapter<ProspectOrder,ProspectOrderViewHolder> mRecyclerViewAdapter;
    FirebaseRecyclerOptions<ProspectOrder> options;
    DatabaseReference prospectOrdersReference;

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

        setRecyclerView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerViewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        mRecyclerViewAdapter.stopListening();
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

    public void setRecyclerView(){
        mRecyclerView = findViewById(R.id.rv_open_orders);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OpenOrders.this));
        prospectOrdersReference = FirebaseManager.getRefToProspectOrders();
        Query query = FirebaseManager.getRefToProspectOrders();
        options = new FirebaseRecyclerOptions.Builder<ProspectOrder>()
                        .setQuery(query, ProspectOrder.class)
                        .build();
        setAdapter();
    }

    private void setAdapter() {
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<ProspectOrder, ProspectOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(ProspectOrderViewHolder holder, int position, ProspectOrder model) {
                holder.storeName.setText(model.getDesiredStore());
            }

            @Override
            public ProspectOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_rv_prospect_order, parent, false);
                return new ProspectOrderViewHolder(view);
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private static class ProspectOrderViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        public ProspectOrderViewHolder(View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.storeName);
        }
    }
}
