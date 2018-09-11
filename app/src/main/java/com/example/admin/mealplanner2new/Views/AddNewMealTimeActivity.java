package com.example.admin.mealplanner2new.Views;

import android.app.TimePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.mealplanner2new.R;

import java.util.Calendar;

public class AddNewMealTimeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText editText_name;
    TextView textView_selectedTime;
    Button button_editTime, button_save;

    String name, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal_time);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.addNewMealTime_progressbar);
        editText_name = findViewById(R.id.addNewMealTime_et_name);
        textView_selectedTime = findViewById(R.id.addNewMealTime_tv_time);
        button_editTime = findViewById(R.id.addNewMealTime_btn_editTime);
        button_save = findViewById(R.id.addNewMealTime_btn_save);


        button_editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                methodSaveData();

            }
        });

    }

    private void methodSaveData() {
        name = editText_name.getText().toString();
        time = textView_selectedTime.getText().toString();

        if (name.equals("")) {
            editText_name.setError("Name required");
            editText_name.requestFocus();

        } else {

            Toast.makeText(this, "Name: " + name + "\nTime: " + time, Toast.LENGTH_LONG).show();
        }

    }

    private void showTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView_selectedTime.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
