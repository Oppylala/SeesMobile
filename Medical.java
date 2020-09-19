package com.example.user.emergencyapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Medical extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    ListView listView;
    private ImageView Back;

    // Array of strings...
    String[] PhoneNumbers = {"Medical1:    08100000000","Medical2:     08100000000","Medical3:    08100000000","Medical4:    08100000000",
            "Medical5:    08100000000","Medical6:    09000000000","Medical7:     08100000000","Medical8:    08100000000","Medical9:    08100000000 ",
            "Medical10:    08100000000","Medical11:    08100000000","Medical12:    08100000000","Medical13:    08100000000","Medical14:    08100000000",
            "Medical15:    08100000000","Medical16:    09000000000","Medical17:    08100000000","Medical18:    08100000000","Medical19:    08100000000 "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
        listView = findViewById(R.id.phonenumbers);

        Back = findViewById(R.id.backicon);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.listview, PhoneNumbers);

        ListView listView =  findViewById(R.id.phonenumbers);
        listView.setAdapter(adapter);

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
                startActivity(new Intent(Medical.this, Profile.class));
                return true;
            case R.id.myreport:
                startActivity(new Intent(Medical.this, ViewMyReport.class));
                return true;
            case R.id.notification:
                startActivity(new Intent(Medical.this, UserNotification.class));
                return true;
            case R.id.share:
                Toast.makeText(getApplicationContext(), "Thanks for sharing", Toast.LENGTH_LONG).show();
                return true;
            case R.id.support:
                startActivity(new Intent(Medical.this, UserSupport.class));
                return true;
            case R.id.guide:
                startActivity(new Intent(Medical.this, UserGuide.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(Medical.this, Loginpage.class));
                return true;
            default:
                return false;
        }
    }
}