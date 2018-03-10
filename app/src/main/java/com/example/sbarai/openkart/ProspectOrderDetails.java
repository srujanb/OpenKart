package com.example.sbarai.openkart;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sbarai.openkart.Models.CollaborationItem;
import com.example.sbarai.openkart.Models.Collaborator;
import com.example.sbarai.openkart.Models.ProspectOrder;
import com.example.sbarai.openkart.Utils.FirebaseManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProspectOrderDetails extends AppCompatActivity {

//    BottomSheetDialog dialog;
    String POid;
    Button chatButton;
    View addItem;
    LayoutInflater inflater;
    LinearLayout listOfItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospect_order_details);
        Bundle extras = getIntent().getExtras();
        POid = extras.getString("POid");


        initVariable();
        getOrderDetailsAndShowItems();
    }

    private void getOrderDetailsAndShowItems() {
        FirebaseManager.getRefToSpecificProspectOrder(POid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProspectOrder order = dataSnapshot.getValue(ProspectOrder.class);
                showAllItems(order);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showAllItems(ProspectOrder order) {
        //dummy data
        for (String collaboratorHashKey: order.getCollaborators().keySet()){
            Collaborator collaborator = order.getCollaborators().get(collaboratorHashKey);
            View collaboratorView = inflater.inflate(R.layout.prospect_order_collaborator_name,null);
            TextView collaboratorName = collaboratorView.findViewById(R.id.collaborator_name);
            setCollaboratorName(collaboratorName,collaboratorHashKey);
            listOfItems.addView(collaboratorView);
            for (String itemHashKey: collaborator.getCollaborationItems().keySet()){
                CollaborationItem item = collaborator.getCollaborationItems().get(itemHashKey);
                View itemView = inflater.inflate(R.layout.prospect_order_item_details,null);
                TextView itemName = itemView.findViewById(R.id.item_name);
                itemName.setText(item.getItemName());
                TextView itemCount = itemView.findViewById(R.id.item_count);
                itemCount.setText(String.valueOf(item.getCount()));

                listOfItems.addView(itemView);
            }
        }
    }

    private void setCollaboratorName(final TextView collaboratorName, String collaboratorHashKey) {
        FirebaseManager.getRefToUserName(collaboratorHashKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    collaboratorName.setText(dataSnapshot.getValue().toString());
                }catch (Exception e){
                    collaboratorName.setText("Error fetching name");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initVariable() {
        inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        listOfItems = findViewById(R.id.list_of_items);
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
