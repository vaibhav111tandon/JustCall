package com.example.vaibhav.justcall.RecyclerViewCategorySearch;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vaibhav.justcall.R;

public class RCViewHolder extends RecyclerView.ViewHolder {

    TextView mName,mCompany,mCategory;
    Button mMore;

    public RCViewHolder(View itemView) {
        super(itemView);

        mName = itemView.findViewById(R.id.name);
        mCategory = itemView.findViewById(R.id.category);
        mCompany = itemView.findViewById(R.id.company);
        mMore = itemView.findViewById(R.id.moreButton);

    }


}
