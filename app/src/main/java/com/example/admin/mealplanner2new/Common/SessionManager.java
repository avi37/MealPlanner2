package com.example.admin.mealplanner2new.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Views.ChooseDashActivity;
import com.example.admin.mealplanner2new.Views.LoginActivity;
import com.example.admin.mealplanner2new.Views.DietMainNavigationActivity;

public class SessionManager {

    private static final String MAIN_PREF_NAME = "MainLoginPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_NAME = "name";
    private static final String KEY_U_ID = "u_id";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String IS_FIRST_PHOTO_UPLOAD = "first_photo";
    SharedPreferences mainPref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        this._context = context;
        mainPref = _context.getSharedPreferences(MAIN_PREF_NAME, PRIVATE_MODE);
        editor = mainPref.edit();
    }

    public void createLoginSession(String name, String access_token, String u_id) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_ACCESS_TOKEN, access_token);
        editor.putString(KEY_U_ID, u_id);

        editor.commit();
        Toast.makeText(_context, "Session Preference created", Toast.LENGTH_SHORT).show();
    }


    public boolean isFirstPhotoUploaded() {

        return mainPref.getBoolean(IS_FIRST_PHOTO_UPLOAD, false);

    }

    public void setFirstPhotoUploaded() {
        editor.putBoolean(IS_FIRST_PHOTO_UPLOAD, true).commit();
    }

    public String getUserName() {
        return mainPref.getString(KEY_NAME, null);
    }

    public String getAccessToken() {
        return mainPref.getString(KEY_ACCESS_TOKEN, null);
    }

    public String getKeyUId() {
        return mainPref.getString(KEY_U_ID, null);
    }

    public boolean checkLogin() {
        boolean status = false;

        if (this.isLoggedIn()) {
            status = true;
            // user is not logged in redirect him to Login Activity
            /*Intent i = new Intent(_context, ChooseDashActivity.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);*/

        }
        return status;
    }

    public void deleteSession() {
        editor.clear();
        editor.commit();
    }

    // Get Login State
    public boolean isLoggedIn() {
        return mainPref.getBoolean(IS_LOGIN, false);
    }


}
