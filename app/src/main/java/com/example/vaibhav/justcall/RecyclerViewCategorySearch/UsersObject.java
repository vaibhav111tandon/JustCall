package com.example.vaibhav.justcall.RecyclerViewCategorySearch;

public class UsersObject {

    private String mCategory;
    private String mName;
    private String mComapany;
    private String mUid;

    public UsersObject(String mCategory, String mName, String mComapany, String mUid) {
        this.mCategory = mCategory;
        this.mName = mName;
        this.mComapany = mComapany;
        this.mUid = mUid;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmComapany() {
        return mComapany;
    }

    public void setmComapany(String mComapany) {
        this.mComapany = mComapany;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }
}
