package com.example.sbarai.openkart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
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
                if (isLocationPermitted()) {
                    startActivity(new Intent(MainActivity.this, OpenOrders.class));
                }
            }
        });

    }

    private boolean isLocationPermitted() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }
        return true;
    }
}
