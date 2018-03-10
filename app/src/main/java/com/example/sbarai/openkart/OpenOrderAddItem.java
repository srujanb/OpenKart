package com.example.sbarai.openkart;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sbarai.openkart.Models.CollaborationItem;
import com.example.sbarai.openkart.Models.Collaborator;
import com.example.sbarai.openkart.Models.ProspectOrder;
import com.example.sbarai.openkart.Utils.FirebaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpenOrderAddItem extends AppCompatActivity {

    String POid;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_order_add_item);

        Bundle extras = getIntent().getExtras();
        POid = extras.getString("POid");
        userId = FirebaseAuth.getInstance().getUid();

        initVariables();
    }

    private void initVariables() {
        View view = findViewById(R.id.submitItem);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitItem();
            }
        });
    }

    //TODO validate edittext data before sending.
    private void submitItem() {
        EditText etItemLink;
        EditText etItemName;
        EditText etItemRate;
        EditText etItemCount;

        etItemLink = findViewById(R.id.itemLink);
        etItemName = findViewById(R.id.itemName);
        etItemRate = findViewById(R.id.itemRate);
        etItemCount = findViewById(R.id.itemCount);

        if (etItemLink.getText().toString().equals("") || etItemName.getText().toString().equals("") || etItemRate.getText().toString().equals("") || etItemCount.getText().toString().equals("")){
            Toast.makeText(this, "Data invalid. Please check fields", Toast.LENGTH_SHORT).show();
            return;
        }

        final CollaborationItem item = new CollaborationItem();
        item.setItemLink(etItemLink.getText().toString());
        item.setItemName(etItemName.getText().toString());
        item.setRatePerUnit(Float.valueOf(etItemRate.getText().toString()));
        item.setCount(Float.valueOf(etItemCount.getText().toString()));

        FirebaseManager.getRefToSpecificProspectOrder(POid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProspectOrder order = dataSnapshot.getValue(ProspectOrder.class);
                List<Collaborator> collaborators = null;
                if (order != null) {
                    collaborators = order.getCollaborators();
                }
                if (collaborators == null){
                    collaborators = new ArrayList<>();
                }
                Collaborator currentCollaborator = null;
                for (Collaborator collaborator:collaborators){
                    if (collaborator.getUserId().equals(userId)){
                        currentCollaborator = collaborator;
                        break;
                    }
                }
                if (currentCollaborator == null){
                    currentCollaborator = new Collaborator();
                    currentCollaborator.setUserId(userId);
                }else {
                    collaborators.remove(currentCollaborator);
                }
                currentCollaborator.addCollaborationItem(item);
                collaborators = new ArrayList<>(collaborators);
                collaborators.add(currentCollaborator);
                if (order != null) {
                    order.setCollaborators(collaborators);
                }
                Gson gson = new Gson();
                String json = gson.toJson(collaborators);
                Log.d("TAGG","Collaborators:" + json);
                dataSnapshot.getRef().setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(OpenOrderAddItem.this, "Item added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OpenOrderAddItem.this, "Some error occurent. Please check", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
