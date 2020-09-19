package com.example.user.emergencyapps;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {
    private static final String TAG = "EditProfile";

   private Button Browse, UpdateUser;
    private EditText name, user1, Phone;
    private ImageView userimage;
    private ImageView Back;
    private TextView Logout, key;

    public Uri imguri;
    StorageTask uploadTask;
    StorageReference mStorageRef;
    DatabaseReference myRef;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    User datas;
    profileDetails ProfileDetails;

    ArrayAdapter<String> adapter;
    DatabaseReference databaseReference;
    FirebaseUser user;
    List<String> itemlist;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        Browse = findViewById(R.id.browse);
        UpdateUser = findViewById(R.id.updateuser);

        name = findViewById(R.id.fullname);
        user1 = findViewById(R.id.username);
        Phone = findViewById(R.id.phone);
        userimage = findViewById(R.id.userimage);
        key = findViewById(R.id.key);

        datas = new User();
        ProfileDetails = new profileDetails();



        Back = findViewById(R.id.backicon);
        Logout = findViewById(R.id.logout);


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

                    Phone.setText(phone);
                    user1.setText(email);



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
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfile.this, Loginpage.class));
            }
        });


       Browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
        UpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask !=null && uploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(),"Uploading in Progress!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Please Select Your Image!",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imguri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
                userimage.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }



    private String getExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImage() {
        String imageid;
        imageid = System.currentTimeMillis() + "." + getExtension(imguri);
        ProfileDetails.setFullname(name.getText().toString().trim());
        ProfileDetails.setUsername(user1.getText().toString().trim());
        ProfileDetails.setPhone(Phone.getText().toString().trim());
        myRef.push().setValue(datas);
        StorageReference Ref = mStorageRef.child(imageid);
        uploadTask = Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if(uploadTask.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), uploadTask.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }


}

