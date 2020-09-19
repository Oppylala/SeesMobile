package com.example.user.emergencyapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class UserGuide extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        Back = findViewById(R.id.backicon);


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Profile:
                startActivity(new Intent(UserGuide.this, Profile.class));
                return true;
            case R.id.myreport:
                startActivity(new Intent(UserGuide.this, ViewMyReport.class));
                return true;
            case R.id.notification:
                startActivity(new Intent(UserGuide.this, UserNotification.class));
                return true;
            case R.id.share:
                Toast.makeText(getApplicationContext(), "Thanks for sharing", Toast.LENGTH_LONG).show();
                return true;
            case R.id.support:
                startActivity(new Intent(UserGuide.this, UserSupport.class));
                return true;
            case R.id.guide:
                startActivity(new Intent(UserGuide.this, UserGuide.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(UserGuide.this, Loginpage.class));
                return true;
            default:
                return false;
        }
    }
}
