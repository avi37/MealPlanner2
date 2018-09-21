package com.example.admin.mealplanner2new.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "exercise", foreignKeys = @ForeignKey(entity = CategoryMaster.class,
        parentColumns = "cat_id",
        childColumns = "ref_cat_id",
        onDelete = CASCADE))
public class Exercise implements Parcelable {

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
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    String id;
    @ColumnInfo(name = "ex_name")
    String name;
    @ColumnInfo(name = "ex_rep")
    String reps;
    @ColumnInfo(name = "time")
    Long timeOfRep;
    @ColumnInfo(name = "ref_cat_id")
    private String ref_master_cat_id;

    public Exercise() {
    }


    protected Exercise(Parcel in) {
        this.id = in.readString();
        this.ref_master_cat_id = in.readString();
        this.name = in.readString();
        this.reps = in.readString();
        this.timeOfRep = (Long) in.readValue(Long.class.getClassLoader());
    }

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

    public String getRef_master_cat_id() {
        return ref_master_cat_id;
    }

    public void setRef_master_cat_id(String ref_master_cat_id) {
        this.ref_master_cat_id = ref_master_cat_id;
    }

    public Long getTimeOfRep() {
        return timeOfRep;
    }

    public void setTimeOfRep(Long timeOfRep) {
        this.timeOfRep = timeOfRep;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.ref_master_cat_id);
        dest.writeString(this.name);
        dest.writeString(this.reps);
        dest.writeValue(this.timeOfRep);
    }
}
