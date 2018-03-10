package com.example.sbarai.openkart;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sbarai.openkart.Models.ProspectOrder;

public class ProspectOrderDetails extends AppCompatActivity {

//    BottomSheetDialog dialog;
    String POid;
    Button chatButton;
    View addItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospect_order_details);
        Bundle extras = getIntent().getExtras();
        POid = extras.getString("POid");


        initVariable();
        showAllItems();
    }

    private void showAllItems() {

    }

    private void initVariable() {
        chatButton = findViewById(R.id.button_chat);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChat(POid);
            }
        });
        addItem = findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProspectOrderDetails.this,OpenOrderAddItem.class);
                intent.putExtra("POid",POid);
                startActivity(intent);
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
