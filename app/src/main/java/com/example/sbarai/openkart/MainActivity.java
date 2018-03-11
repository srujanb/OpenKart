package com.example.sbarai.openkart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
                    if (isLocationEnabled()) {
                        startActivity(new Intent(MainActivity.this, OpenOrders.class));
                    }else {
                        checkGPS();
                    }
                }
            }
        });

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
        checkGPS();
    }

    public void checkGPS(){
//        Toast.makeText(thisActivity, "isLocationEnabled: " + isLocationEnabled(), Toast.LENGTH_SHORT).show();
        if (!isLocationEnabled()){
            new AlertDialog.Builder(this)
                    .setTitle("Please enable GPS")
                    .setMessage("This app requires GPS to work as intended, please enable to continue")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent gpsOptionsIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(gpsOptionsIntent);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    public Boolean isLocationEnabled(){
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private boolean isLocationPermitted() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }
        return true;
    }
}
