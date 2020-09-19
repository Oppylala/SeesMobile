package com.example.user.emergencyapps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ViewMyReport extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private static final String TAG = "ViewMyReport";

    private ListView li;
    ArrayAdapter<String> adapter;
    DatabaseReference databaseReference;
    FirebaseUser user;
    List<String> itemlist;
    String uid;

    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_report);
        Back = findViewById(R.id.backicon);
        li = findViewById(R.id.myreports);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        itemlist = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("reportcase");

           databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   itemlist.clear();
                   try {
                       String crimetype = dataSnapshot.child(uid).child("crimeType").getValue(String.class);
                       String Report = dataSnapshot.child(uid).child("report").getValue(String.class);
                       String Station = dataSnapshot.child(uid).child("station").getValue(String.class);
                       String MyEmail = dataSnapshot.child(uid).child("myemail").getValue(String.class);


                       itemlist.add(crimetype);
                       itemlist.add(Report);
                       itemlist.add(Station);
                       itemlist.add(MyEmail);


                       adapter = new ArrayAdapter<>(ViewMyReport.this, android.R.layout.simple_list_item_1, itemlist);
                       li.setAdapter(adapter);
                   }catch (Exception ex){
                       Log.d(TAG,"Exception caught") ;
                       ex.printStackTrace();
                   }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {
                   Log.d(TAG,"Error Occur");

               }
           });
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
                startActivity(new Intent(ViewMyReport.this, Profile.class));
                return true;
            case R.id.myreport:
                startActivity(new Intent(ViewMyReport.this, ViewMyReport.class));
                return true;
            case R.id.notification:
                startActivity(new Intent(ViewMyReport.this, UserNotification.class));
                return true;
            case R.id.share:
                Toast.makeText(getApplicationContext(), "Thanks for sharing", Toast.LENGTH_LONG).show();
                return true;
            case R.id.support:
                startActivity(new Intent(ViewMyReport.this, UserSupport.class));
                return true;
            case R.id.guide:
                startActivity(new Intent(ViewMyReport.this, UserGuide.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(ViewMyReport.this, Loginpage.class));
                return true;
            default:
                return false;
        }
    }
}
