package com.example.sbarai.openkart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sbarai.openkart.Utils.FirebaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


    }

    public void showNamePrivacyReason(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(EditProfileActivity.this).create();
        alertDialog.setTitle("Name policy");
        alertDialog.setMessage("Your name will add convenience for people around you to recognize you before they collaborate with your order. Only your name will be visible when you post a prospect order, other information will be kept private.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void saveTheName(View view) {
        EditText editText = findViewById(R.id.name);
        String name = editText.getText().toString();
        String key = FirebaseAuth.getInstance().getUid();
        if (isNameValid(name)){
            DatabaseReference refToUserNames = FirebaseManager.getRefToUserName(key);
            refToUserNames.setValue(name).addOnSuccessListener(
                    new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            goToMainActivity();
                        }
                    }
            ).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfileActivity.this, "Error: Please retry.", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(EditProfileActivity.this,MainActivity.class));
        finish();
    }

    private boolean isNameValid(String name) {
        if (name == null){
            Toast.makeText(this, "Name cannot be null", Toast.LENGTH_SHORT).show();
        }else if(name.trim().length() < 3){
            Toast.makeText(this, "Name should be at-least 3 characters long", Toast.LENGTH_SHORT).show();
        }else if (name.matches(".*\\d+.*")){
            Toast.makeText(this, "Name cannot contain numeric characters", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    }
}
