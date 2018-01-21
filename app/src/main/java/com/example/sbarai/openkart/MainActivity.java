package com.example.sbarai.openkart;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    View openOrders;
    View shareRides;
    View bulkOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        defineVariables();
        hideHomeScreenCards();

        Handler handler = new Handler();
        Runnable runnable1 =  new Runnable() {
            @Override
            public void run() {
                openOrders.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInUp).duration(300).interpolate(new AccelerateDecelerateInterpolator()).playOn(findViewById(R.id.open_orders));
            }
        };
        handler.postDelayed(runnable1, 100);

        Runnable runnable2 =  new Runnable() {
            @Override
            public void run() {
                shareRides.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInUp).duration(300).interpolate(new AccelerateDecelerateInterpolator()).playOn(findViewById(R.id.share_rides));
            }
        };
        handler.postDelayed(runnable2, 200);

        Runnable runnable3 =  new Runnable() {
            @Override
            public void run() {
                bulkOrders.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInUp).duration(300).interpolate(new AccelerateDecelerateInterpolator()).playOn(findViewById(R.id.buld_orders));
            }
        };
        handler.postDelayed(runnable3, 300);

        setOnClickListeners();

    }

    private void hideHomeScreenCards() {
        openOrders.setVisibility(View.INVISIBLE);
        shareRides.setVisibility(View.INVISIBLE);
        bulkOrders.setVisibility(View.INVISIBLE);
    }

    private void defineVariables() {
        openOrders = findViewById(R.id.open_orders);
        shareRides = findViewById(R.id.share_rides);
        bulkOrders = findViewById(R.id.buld_orders);
    }

    private void setOnClickListeners() {

        View view;

        view = findViewById(R.id.open_orders);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OpenOrders.class));
            }
        });

    }
}
