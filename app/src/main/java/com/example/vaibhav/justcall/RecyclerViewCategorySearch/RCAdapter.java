package com.example.vaibhav.justcall.RecyclerViewCategorySearch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.vaibhav.justcall.CategorySearchFragment;
import com.example.vaibhav.justcall.R;
import com.example.vaibhav.justcall.ServiceProfileActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RCAdapter extends RecyclerView.Adapter<RCViewHolder> {

    List<UsersObject> usersObjectList;
    Context context;

    public RCAdapter(List<UsersObject> usersObjectList, Context context) {
        this.usersObjectList = usersObjectList;
        this.context = context;
    }

    @Override
    public RCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_category_search,null);
        RCViewHolder rcViewHolder = new RCViewHolder(layoutView);
        return rcViewHolder;
    }

    @Override
    public void onBindViewHolder(final RCViewHolder holder, int position) {
        holder.mName.setText(usersObjectList.get(position).getmName());
        holder.mCompany.setText(usersObjectList.get(position).getmComapany());
        holder.mCategory.setText(usersObjectList.get(position).getmCategory());

        holder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference moreDb = FirebaseDatabase.getInstance().getReference().child("users").child(usersObjectList.get(holder.getLayoutPosition()).getmUid());
                Intent intent = new Intent(context.getApplicationContext(),ServiceProfileActivity.class);
                intent.putExtra("userKey",moreDb.getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.usersObjectList.size();
    }
}
