package com.example.admin.mealplanner2new.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "cat_master",indices = {@Index(value = {"cat_id"},
        unique = true)})
public class CategoryMaster implements Parcelable {


    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getTottal_time() {
        return tottal_time;
    }

    public void setTottal_time(String tottal_time) {
        this.tottal_time = tottal_time;
    }

    public String getDateOf() {
        return dateOf;
    }

    public void setDateOf(String dateOf) {
        this.dateOf = dateOf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Ignore
    private String id;

    @ColumnInfo(name = "cat_name")
    private String cat_name;


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cat_id")
    private String cat_id;

    @ColumnInfo(name = "total_time")
    private String tottal_time;

    @ColumnInfo(name = "date")
    private String dateOf;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "title")
    private String title;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cat_name);
        dest.writeString(this.cat_id);
        dest.writeString(this.tottal_time);
        dest.writeString(this.dateOf);
        dest.writeString(this.status);
        dest.writeString(this.title);
    }

    public CategoryMaster() {
    }

    protected CategoryMaster(Parcel in) {
        this.id = in.readString();
        this.cat_name = in.readString();
        this.cat_id = in.readString();
        this.tottal_time = in.readString();
        this.dateOf = in.readString();
        this.status = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<CategoryMaster> CREATOR = new Parcelable.Creator<CategoryMaster>() {
        @Override
        public CategoryMaster createFromParcel(Parcel source) {
            return new CategoryMaster(source);
        }

        @Override
        public CategoryMaster[] newArray(int size) {
            return new CategoryMaster[size];
        }
    };
}
