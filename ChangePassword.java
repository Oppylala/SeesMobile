package com.example.user.emergencyapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ChangePassword extends AppCompatActivity {
    private Button btnChange;
    private EditText email;
    private TextView Logout;
    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        btnChange = findViewById(R.id.change);
        email = findViewById(R.id.email);
        Back = findViewById(R.id.backicon);
        Logout = findViewById(R.id.logout);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePassword.this, Loginpage.class));
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is required !");
                }
                else{
                    Toast.makeText(getApplicationContext(), "Check your Email Indox", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
