package com.example.admin.mealplanner2new.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("cat_name")
    @Expose
    private String cat_name;


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cat_id")
    @SerializedName("cat_id")
    @Expose
    private String cat_id;

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    @ColumnInfo(name = "total_time")
    @SerializedName("total_time")
    @Expose
    private String total_time;

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    private String dateOf;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    private String status;

    @ColumnInfo(name = "title")
    @SerializedName("name")
    @Expose
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
        dest.writeString(this.total_time);
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
        this.total_time = in.readString();
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