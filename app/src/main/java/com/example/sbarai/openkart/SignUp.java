package com.example.sbarai.openkart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    public DatabaseReference ref;
    EditText etname;
    Button btnSubmit;

    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        etname = (EditText) findViewById(R.id.name);
        btnSubmit = (Button) findViewById(R.id.Sign_submit);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database =FirebaseDatabase.getInstance();
                ref = database.getReference();
                String name = etname.getText().toString().trim();
                final User user = new User();
                user.setName(name);

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                ref.child("users").child(userId).child("name").setValue(name);

                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
