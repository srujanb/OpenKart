package com.example.sbarai.openkart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.github.clans.fab.FloatingActionButton

public class OpenOrders extends AppCompatActivity {

    FloatingActionButton createProspectOrder;
    Toolbar toolbar;

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
    }

    private void defineVariables() {
        createProspectOrder = findViewById(R.id.menu_item_1);
    }

    private void setFABListeners() {
        createProspectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpenOrders.this,CreateProspectOrder.class));
            }
        });
    }
}
