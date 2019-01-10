package com.example.vaibhav.justcall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vaibhav.justcall.RecyclerViewCategorySearch.RCAdapter;
import com.example.vaibhav.justcall.RecyclerViewCategorySearch.UsersObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class CategorySearchFragment extends Fragment {

    EditText input;
    Button search;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public static CategorySearchFragment newInstance(){
        CategorySearchFragment categorySearchFragment = new CategorySearchFragment();
        return categorySearchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_search,container,false);

        input = view.findViewById(R.id.category_input);
        search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RCAdapter(getDataSet(),getActivity());
        recyclerView.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenForData();
            }
        });

        return view;
    }


    private void listenForData() {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = userDb.orderByChild("category").startAt(input.getText().toString()).endAt(input.getText().toString()+"\uf8ff");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String company = "";
                String category = "";
                String name = "";
                String uid = dataSnapshot.getRef().getKey();
                if(dataSnapshot.child("category").getValue()!=null){
                    category = dataSnapshot.child("category").getValue().toString();
                    name = dataSnapshot.child("name").getValue().toString();
                    company = dataSnapshot.child("company").getValue().toString();
                    UsersObject usersObject = new UsersObject(category,name,company,uid);
                    results.add(usersObject);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<UsersObject> results = new ArrayList<>();

    private ArrayList<UsersObject> getDataSet() {
        listenForData();
        return results;
    }
}
