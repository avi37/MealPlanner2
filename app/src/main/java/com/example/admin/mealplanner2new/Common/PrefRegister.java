package com.example.admin.mealplanner2new.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefRegister {

    SharedPreferences regPref;
    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String REGISTER_PREF_NAME = "RegisterPref";

    private static final String WORKOUT_PLACE = "workout_place";
    private static final String GENDER = "gender";
    private static final String AIM = "aim";
    private static final String HEIGHT = "height";
    private static final String AGE = "age";
    private static final String WEIGHT = "weight";
    private static final String TRAINING_LEVEL = "training_level";
    private static final String PAST_EX_EX = "past_ex_ex";
    private static final String SCHEDULE = "schedule";
    private static final String COACH_ID = "coach_id";
    private static final String WEEK_MINUTES = "week_minutes";
    private static final String EX_DAYS = "ex_days";


    public PrefRegister(Context context) {
        this._context = context;
        regPref = _context.getSharedPreferences(REGISTER_PREF_NAME, PRIVATE_MODE);
        editor = regPref.edit();
    }


    public void setWorkoutPlace(String place) {
        editor.putString(WORKOUT_PLACE, place);
        editor.commit();
    }

    public void setGender(String gender) {
        editor.putString(GENDER, gender);
        editor.commit();
    }

    public void setAim(String aim) {
        editor.putString(AIM, aim);
        editor.commit();
    }

    public void setBodyDetails(String height, String age, String weight) {
        editor.putString(HEIGHT, height);
        editor.putString(AGE, age);
        editor.putString(WEIGHT, weight);
        editor.commit();
    }

    public void setLevel(String level) {
        editor.putString(TRAINING_LEVEL, level);
        editor.commit();
    }

    public void setPastEx(String past_ex) {
        editor.putString(PAST_EX_EX, past_ex);
        editor.commit();
    }

    public void setSchedule(String schedule) {
        editor.putString(SCHEDULE, schedule);
        editor.commit();
    }

    public void setCoachId(String coachId) {
        editor.putString(COACH_ID, coachId);
        editor.commit();
    }

    public void setExPlan(String minute, String days) {
        editor.putString(WEEK_MINUTES, minute);
        editor.putString(EX_DAYS, days);
        editor.commit();
    }


    public String getWorkoutPlace() {
        return regPref.getString(WORKOUT_PLACE, null);

    }

    public String getGENDER() {
        return regPref.getString(GENDER, null);
    }

    public String getAIM() {
        return regPref.getString(AIM, null);
    }

    public String getHEIGHT() {
        return regPref.getString(HEIGHT, null);
    }

    public String getAGE() {
        return regPref.getString(AGE, null);
    }

    public String getWEIGHT() {
        return regPref.getString(WEIGHT, null);
    }

    public String getTrainingLevel() {
        return regPref.getString(TRAINING_LEVEL, null);
    }

    public String getPastExEx() {
        return regPref.getString(PAST_EX_EX, null);
    }

    public String getSCHEDULE() {
        return regPref.getString(SCHEDULE, null);
    }

    public String getWeekMinutes() {
        return regPref.getString(WEEK_MINUTES, null);
    }

    public String getExDays() {
        return regPref.getString(EX_DAYS, null);
    }

    public String getCoachId() {
        return regPref.getString(COACH_ID, null);
    }

    public void deleteRegisterPref() {
        editor.clear();
        editor.commit();
    }

}
