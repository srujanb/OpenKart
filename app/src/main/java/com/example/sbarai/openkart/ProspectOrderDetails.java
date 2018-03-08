package com.example.sbarai.openkart;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProspectOrderDetails extends AppCompatActivity {

//    BottomSheetDialog dialog;
    String POid;
    Button chatButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospect_order_details);
        Bundle extras = getIntent().getExtras();
        POid = extras.getString("POid");


        initVariable();
    }

    private void initVariable() {
        chatButton = findViewById(R.id.button_chat);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChat(POid);
            }
        });
    }

    private void openChat(String POid) {
        Intent intent = new Intent(ProspectOrderDetails.this,POChatActivity.class);
        intent.putExtra("POid",POid);
        startActivity(intent);
    }

    /*private void openBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
    }*/
}
