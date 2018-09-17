package com.example.admin.mealplanner2new.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    String id;
    String name;
    String reps;

    public Long getTimeOfRep() {
        return timeOfRep;
    }

    public void setTimeOfRep(Long timeOfRep) {
        this.timeOfRep = timeOfRep;
    }

    Long timeOfRep;

    public Exercise() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.reps);
        dest.writeValue(this.timeOfRep);
    }

    protected Exercise(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.reps = in.readString();
        this.timeOfRep = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel source) {
            return new Exercise(source);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
}
