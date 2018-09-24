package com.example.admin.mealplanner2new.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SavedExerciseData implements Parcelable {

    @SerializedName("u_id")
    @Expose
    private String u_id;

    @SerializedName("id")
    @Expose
    private String id;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    @SerializedName("task_id")
    @Expose
    private String task_id;

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Exercise> getExerciseArrayList() {
        return exerciseArrayList;
    }

    public void setExerciseArrayList(ArrayList<Exercise> exerciseArrayList) {
        this.exerciseArrayList = exerciseArrayList;
    }

    @SerializedName("exercise_list")
    @Expose
    private ArrayList<Exercise> exerciseArrayList;


    public SavedExerciseData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.u_id);
        dest.writeString(this.id);
        dest.writeString(this.task_id);
        dest.writeTypedList(this.exerciseArrayList);
    }

    protected SavedExerciseData(Parcel in) {
        this.u_id = in.readString();
        this.id = in.readString();
        this.task_id = in.readString();
        this.exerciseArrayList = in.createTypedArrayList(Exercise.CREATOR);
    }

    public static final Creator<SavedExerciseData> CREATOR = new Creator<SavedExerciseData>() {
        @Override
        public SavedExerciseData createFromParcel(Parcel source) {
            return new SavedExerciseData(source);
        }

        @Override
        public SavedExerciseData[] newArray(int size) {
            return new SavedExerciseData[size];
        }
    };
}
