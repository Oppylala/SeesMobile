package com.example.user.emergencyapps;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSupport extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private static final String TAG = "UserSupport";

    private TextView SupportEmail, SupportPhone;
    private EditText SupportDetails;
    private ImageView SendSupport;
    private ProgressBar SupportPro;
    private userSupportreports SupportMessage;
    private ImageView Back;
    private DatabaseReference dbreff;


    private Handler handler;

    DatabaseReference databaseReference;
    FirebaseUser user;
    List<String> itemlist;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_support);

        SupportEmail = findViewById(R.id.supportemail);
        SupportDetails = findViewById(R.id.supportdetails);
        SupportPhone = findViewById(R.id.supportphone);
        SendSupport = findViewById(R.id.sendreport);
        SupportPro = findViewById(R.id.supportprogress);
        Back = findViewById(R.id.backicon);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        dbreff = FirebaseDatabase.getInstance().getReference("usersupport");
        user = FirebaseAuth.getInstance().getCurrentUser();

        uid = user.getUid();
        itemlist = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemlist.clear();
                try {
                    String email = dataSnapshot.child(uid).child("email").getValue(String.class);
                    String password = dataSnapshot.child(uid).child("password").getValue(String.class);
                    String phone = dataSnapshot.child(uid).child("phone").getValue(String.class);

                    itemlist.add(phone);
                    itemlist.add(password);
                    itemlist.add(email);


                    //  adapter = new ArrayAdapter<>(EditProfile.this, android.R.layout.simple_list_item_1, itemlist);

                    SupportPhone.setText(phone);
                    SupportEmail.setText(email);



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



        SupportMessage = new userSupportreports();

        SendSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = SupportEmail.getText().toString().trim();
                String Details = SupportDetails.getText().toString().trim();
                String SupportPhonenumber = SupportPhone.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    SupportEmail.setError("Your Email is Required");
                    return;
                }
                else if(TextUtils.isEmpty(Details)){
                    SupportDetails.setError("Your write up is required!");
                    return;
                }
                else if(TextUtils.isEmpty(SupportPhonenumber)){
                    SupportPhone.setError("Phone Number is required! ");
                    return;
                }
                else{


                    uploadDetails();





                }
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
                startActivity(new Intent(UserSupport.this, Profile.class));
                return true;
            case R.id.myreport:
                startActivity(new Intent(UserSupport.this, ViewMyReport.class));
                return true;
            case R.id.notification:
                startActivity(new Intent(UserSupport.this, UserNotification.class));
                return true;
            case R.id.share:
                Toast.makeText(getApplicationContext(), "Thanks for sharing", Toast.LENGTH_LONG).show();
                return true;
            case R.id.support:
                startActivity(new Intent(UserSupport.this, UserSupport.class));
                return true;
            case R.id.guide:
                startActivity(new Intent(UserSupport.this, UserGuide.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(UserSupport.this, Loginpage.class));
                return true;
            default:
                return false;
        }
    }
    private void uploadDetails(){
        SupportPro.setVisibility(View.VISIBLE);
      SupportMessage.setSupportemail(SupportEmail.getText().toString().trim());
        SupportMessage.setSupportdetails(SupportDetails.getText().toString().trim());
        SupportMessage.setSupportphone(SupportPhone.getText().toString().trim());


        dbreff.push().setValue(SupportMessage);

        final ProgressDialog progressDialog = new ProgressDialog(UserSupport.this);
        progressDialog.setTitle("Sending UserSupport");
        progressDialog.show();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage();
               // Toast.makeText(getApplicationContext(),"Your Report has been Uploaded",Toast.LENGTH_LONG).show();
                //finish();
            }
        },4000);



    }
    private void sendMessage(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserSupport.this);
        alertDialogBuilder
                .setMessage("Your message has being sent!")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),Dashboard.class));
                                SupportPro.setVisibility(View.INVISIBLE);

                            }
                        })
                .setNegativeButton("Send more Support Message",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),UserSupport.class));
                                SupportDetails.setText("");


                            }
                        });
        SupportPro.setVisibility(View.INVISIBLE);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
