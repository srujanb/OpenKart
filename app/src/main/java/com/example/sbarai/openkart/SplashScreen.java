package com.example.sbarai.openkart;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    public DatabaseReference ref;
    private FirebaseAuth mAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        ref = database.getReference();
        FirebaseAuth loginCheck;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(this);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAuth.getInstance().getCurrentUser() == null)
                {
                    Intent show = new Intent(SplashScreen.this, PhoneAuth.class);
                    startActivity(show);
                }
                else
                {
                    ref.child("users").child(userId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() == null)
                            {
                                Intent show = new Intent(SplashScreen.this, SignUp.class);
                                startActivity(show);
                            }
                            else {
                                Intent show = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(show);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                finish();
            }
        },500);
    }
}
