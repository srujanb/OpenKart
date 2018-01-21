package com.example.sbarai.openkart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotActivity extends AppCompatActivity {

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fgot);
        submit = (Button)findViewById(R.id.forgot_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}
