package com.example.vaibhav.justcall;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText mName,mEmail,mMobile,mAltMobile,mAddress,mPincode,mCategory,mCompany,mPassword;
    Button mRegister;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

     mName = findViewById(R.id.name);
     mEmail = findViewById(R.id.email);
     mAddress = findViewById(R.id.address);
     mAltMobile = findViewById(R.id.altNumber);
     mMobile = findViewById(R.id.mobile);
     mPassword = findViewById(R.id.password);
     mPincode = findViewById(R.id.pincode);
     mCategory = findViewById(R.id.category);
     mCompany = findViewById(R.id.company);
     mRegister = findViewById(R.id.register);

     mAuth = FirebaseAuth.getInstance();

     mRegister.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             final String NAME = mName.getText().toString();
             final String EMAIL = mEmail.getText().toString();
             final String ADDRESS = mAddress.getText().toString();
             final String MOBILE = mMobile.getText().toString();
             final String ALTMOB = mAltMobile.getText().toString();
             String PASSWORD = mPassword.getText().toString();
             final String CATEGORY = mCategory.getText().toString();
             final String COMPANY = mCompany.getText().toString();
             final String PINCODE = mPincode.getText().toString();

             mAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         String userUID = mAuth.getCurrentUser().getUid();
                         DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("users").child(userUID);

                         Map<String,Object> userInfo = new HashMap();
                         userInfo.put("name",NAME);
                         userInfo.put("email",EMAIL);
                         userInfo.put("mobile",MOBILE);
                         userInfo.put("alternative_mobile",ALTMOB);
                         userInfo.put("address",ADDRESS);
                         userInfo.put("pincode",PINCODE);
                         userInfo.put("category",CATEGORY);
                         userInfo.put("company",COMPANY);
                         userInfo.put("description","default");
                         currentUserDb.updateChildren(userInfo);

                         Toast.makeText(RegisterActivity.this, "Successfully Updated !!!", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                         startActivity(intent);
                         finish();
                     }else{
                         Toast.makeText(RegisterActivity.this, "Oops... Something went wrong", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
         }
     });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
