package com.example.vaibhav.justcall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    EditText mName,mEmail,mMobile,mAltMobile,mAddress,mPincode,mCategory,mCompany,mDescription;
    Button mSave;

    public static ProfileFragment newInstance(){
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        setHasOptionsMenu(true);

        mName = view.findViewById(R.id.name);
        mEmail = view.findViewById(R.id.email);
        mCompany = view.findViewById(R.id.company);
        mCategory = view.findViewById(R.id.category);
        mAltMobile = view.findViewById(R.id.altNumber);
        mMobile = view.findViewById(R.id.mobile);
        mAddress= view.findViewById(R.id.address);
        mPincode = view.findViewById(R.id.pincode);
        mSave = view.findViewById(R.id.resave);
        mDescription = view.findViewById(R.id.description);

        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid().toString());

        currentUserDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mName.setText(dataSnapshot.child("name").getValue().toString());
                mEmail.setText(dataSnapshot.child("email").getValue().toString());
                mCompany.setText(dataSnapshot.child("company").getValue().toString());
                mCategory.setText(dataSnapshot.child("category").getValue().toString());
                mAddress.setText(dataSnapshot.child("address").getValue().toString());
                mAltMobile.setText(dataSnapshot.child("alternative_mobile").getValue().toString());
                mMobile.setText(dataSnapshot.child("mobile").getValue().toString());
                mPincode.setText(dataSnapshot.child("pincode").getValue().toString());
                mDescription.setText(dataSnapshot.child("description").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTheData();
            }
        });


        return view;
    }

    private void updateTheData() {
        final String NAME = mName.getText().toString();
        final String EMAIL = mEmail.getText().toString();
        final String ADDRESS = mAddress.getText().toString();
        final String MOBILE = mMobile.getText().toString();
        final String ALTMOB = mAltMobile.getText().toString();
        final String CATEGORY = mCategory.getText().toString();
        final String COMPANY = mCompany.getText().toString();
        final String PINCODE = mPincode.getText().toString();
        final String DESCRIPTION = mDescription.getText().toString();

        DatabaseReference resetUserDb = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid().toString());

        Map<String,Object> userInfo = new HashMap();
        userInfo.put("name",NAME);
        userInfo.put("email",EMAIL);
        userInfo.put("mobile",MOBILE);
        userInfo.put("alternative_mobile",ALTMOB);
        userInfo.put("address",ADDRESS);
        userInfo.put("pincode",PINCODE);
        userInfo.put("category",CATEGORY);
        userInfo.put("company",COMPANY);
        userInfo.put("description",DESCRIPTION);

        resetUserDb.updateChildren(userInfo);

        Toast.makeText(getContext(), "Successfully Updated!!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.topmenu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
            {
                try {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), ChooseLoginRegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return true;
                }catch (Exception e){
                    Toast.makeText(getContext().getApplicationContext(), "Oops... Something went wrong", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return false;
    }
}
