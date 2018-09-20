package com.example.admin.mealplanner2new.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DayWiseExersice implements Parcelable {


    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getDateOfExercise() {
        return dateOfExercise;
    }

    public void setDateOfExercise(String dateOfExercise) {
        this.dateOfExercise = dateOfExercise;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String cat_name;

    private String total_time;

    private String dateOfExercise;

    private String status;

    public ArrayList<Exercise> getExerciseArrayList() {
        return exerciseArrayList;
    }

    public void setExerciseArrayList(ArrayList<Exercise> exerciseArrayList) {
        this.exerciseArrayList = exerciseArrayList;
    }

    private ArrayList<Exercise> exerciseArrayList;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.cat_name);
        dest.writeString(this.total_time);
        dest.writeString(this.dateOfExercise);
        dest.writeString(this.status);
        dest.writeTypedList(this.exerciseArrayList);
    }

    public DayWiseExersice() {
    }

    protected DayWiseExersice(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.cat_name = in.readString();
        this.total_time = in.readString();
        this.dateOfExercise = in.readString();
        this.status = in.readString();
        this.exerciseArrayList = in.createTypedArrayList(Exercise.CREATOR);
    }

    public static final Parcelable.Creator<DayWiseExersice> CREATOR = new Parcelable.Creator<DayWiseExersice>() {
        @Override
        public DayWiseExersice createFromParcel(Parcel source) {
            return new DayWiseExersice(source);
        }

        @Override
        public DayWiseExersice[] newArray(int size) {
            return new DayWiseExersice[size];
        }
    };
}
