package com.example.vaibhav.justcall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vaibhav.justcall.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Permission;

public class ServiceProfileActivity extends AppCompatActivity {

    TextView mName,mCompany,mCategory,mMobile,mAltMobile,mDescription,mPincode,mAddress,mEmail;
    Button callOne, callTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_profile);

        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mCompany = findViewById(R.id.company);
        mCategory = findViewById(R.id.category);
        mEmail = findViewById(R.id.email);
        mMobile = findViewById(R.id.mobile);
        mAltMobile = findViewById(R.id.altNumber);
        mDescription = findViewById(R.id.description);

        callOne = findViewById(R.id.callOne);
        callTwo = findViewById(R.id.callTwo);

        Bundle extras = getIntent().getExtras();
        String userKey = extras.getString("userKey");

        DatabaseReference sereviceDb = FirebaseDatabase.getInstance().getReference().child("users").child(userKey);
        sereviceDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mName.setText(dataSnapshot.child("name").getValue().toString());
                mAddress.setText(dataSnapshot.child("address").getValue().toString()+"\t"+dataSnapshot.child("pincode").getValue().toString());
                mMobile.setText(dataSnapshot.child("mobile").getValue().toString());
                mAltMobile.setText(dataSnapshot.child("alternative_mobile").getValue().toString());
                mEmail.setText(dataSnapshot.child("email").getValue().toString());
                mDescription.setText(dataSnapshot.child("description").getValue().toString());
                mCompany.setText(dataSnapshot.child("company").getValue().toString());
                mCategory.setText(dataSnapshot.child("category").getValue().toString());

                callOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+dataSnapshot.child("mobile").getValue().toString()));
                        if(ActivityCompat.checkSelfPermission(ServiceProfileActivity.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                            return;
                        }
                        startActivity(callIntent);
                    }
                });

                callTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+dataSnapshot.child("alternative_mobile").getValue().toString()));
                        if(ActivityCompat.checkSelfPermission(ServiceProfileActivity.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                            return;
                        }
                        startActivity(callIntent);
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
