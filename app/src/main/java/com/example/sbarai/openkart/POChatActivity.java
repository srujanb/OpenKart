package com.example.sbarai.openkart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class POChatActivity extends AppCompatActivity {

    String POid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pochat);
        POid = (String) getIntent().getExtras().get("POid");

        Toast.makeText(this, "POid: " + POid, Toast.LENGTH_SHORT).show();
    }
}
