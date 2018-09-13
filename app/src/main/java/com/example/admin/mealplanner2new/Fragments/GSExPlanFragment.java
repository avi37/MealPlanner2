package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Common.PrefRegister;
import com.example.admin.mealplanner2new.R;


public class GSExPlanFragment extends Fragment implements View.OnClickListener {

    PrefRegister prefRegister;

    View view_main;
    TextView textView_minutes;
    TextView tv_mon, tv_tue, tv_wed, tv_thu, tv_fri, tv_sat, tv_sun;
    AppCompatSeekBar seekBar_minutes;
    Button button_next;

    String week_minutes, selectedDays;
    boolean monSelected = false, tueSelected = false, wedSelected = false, thuSelected = false, friSelected = false, satSelected = false, sunSelected = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsex_plan, container, false);

        prefRegister = new PrefRegister(getContext());

        textView_minutes = view_main.findViewById(R.id.gsExPlan_tv_minutes);
        seekBar_minutes = view_main.findViewById(R.id.gsExPlan_seekBar_min);
        tv_mon = view_main.findViewById(R.id.gsExPlan_tv_mon);
        tv_tue = view_main.findViewById(R.id.gsExPlan_tv_tue);
        tv_wed = view_main.findViewById(R.id.gsExPlan_tv_wed);
        tv_thu = view_main.findViewById(R.id.gsExPlan_tv_thu);
        tv_fri = view_main.findViewById(R.id.gsExPlan_tv_fri);
        tv_sat = view_main.findViewById(R.id.gsExPlan_tv_sat);
        tv_sun = view_main.findViewById(R.id.gsExPlan_tv_sun);
        button_next = view_main.findViewById(R.id.gsExPlan_btn_next);

        textView_minutes.setText("45");

        tv_mon.setOnClickListener(this);
        tv_tue.setOnClickListener(this);
        tv_wed.setOnClickListener(this);
        tv_thu.setOnClickListener(this);
        tv_fri.setOnClickListener(this);
        tv_sat.setOnClickListener(this);
        tv_sun.setOnClickListener(this);
        seekBar_minutes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                week_minutes = String.valueOf(progress);

                textView_minutes.setText("Time per week: " + progress + " min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedDays = getSelectedDays();
                prefRegister.setExPlan(week_minutes, selectedDays);

                Fragment someFragment = new SignUpFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                transaction.replace(R.id.content_get_started, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view_main;
    }

    private String getSelectedDays() {
        String str_days = "";

        if (monSelected) {
            str_days += "monday,";
        }

        if (tueSelected) {
            str_days += "tuesday,";
        }

        if (wedSelected) {
            str_days += "wednesday,";
        }

        if (thuSelected) {
            str_days += "thursday,";
        }

        if (friSelected) {
            str_days += "friday,";
        }

        if (satSelected) {
            str_days += "saturday,";
        }

        if (sunSelected) {
            str_days += "sunday,";
        }

        return str_days;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.gsExPlan_tv_mon:
                setDaySelectd("mon");
                break;

            case R.id.gsExPlan_tv_tue:
                setDaySelectd("tue");
                break;

            case R.id.gsExPlan_tv_wed:
                setDaySelectd("wed");
                break;

            case R.id.gsExPlan_tv_thu:
                setDaySelectd("thu");
                break;

            case R.id.gsExPlan_tv_fri:
                setDaySelectd("fri");
                break;

            case R.id.gsExPlan_tv_sat:
                setDaySelectd("sat");
                break;

            case R.id.gsExPlan_tv_sun:
                setDaySelectd("sun");
                break;

        }

    }

    private void setDaySelectd(String day) {

        switch (day) {

            case "mon":
                if (!monSelected) {
                    monSelected = true;
                    tv_mon.setTextColor(getResources().getColor(R.color.white));
                    tv_mon.setBackgroundResource(R.drawable.bg_circle_blue);
                } else {
                    monSelected = false;
                    tv_mon.setTextColor(getResources().getColor(R.color.font_grey));
                    tv_mon.setBackgroundResource(R.drawable.bg_circle);
                }
                break;

            case "tue":
                if (!tueSelected) {
                    tueSelected = true;
                    tv_tue.setTextColor(getResources().getColor(R.color.white));
                    tv_tue.setBackgroundResource(R.drawable.bg_circle_blue);
                } else {
                    tueSelected = false;
                    tv_tue.setTextColor(getResources().getColor(R.color.font_grey));
                    tv_tue.setBackgroundResource(R.drawable.bg_circle);
                }
                break;

            case "wed":
                if (!wedSelected) {
                    wedSelected = true;
                    tv_wed.setTextColor(getResources().getColor(R.color.white));
                    tv_wed.setBackgroundResource(R.drawable.bg_circle_blue);
                } else {
                    wedSelected = false;
                    tv_wed.setTextColor(getResources().getColor(R.color.font_grey));
                    tv_wed.setBackgroundResource(R.drawable.bg_circle);
                }
                break;

            case "thu":
                if (!thuSelected) {
                    thuSelected = true;
                    tv_thu.setTextColor(getResources().getColor(R.color.white));
                    tv_thu.setBackgroundResource(R.drawable.bg_circle_blue);
                } else {
                    thuSelected = false;
                    tv_thu.setTextColor(getResources().getColor(R.color.font_grey));
                    tv_thu.setBackgroundResource(R.drawable.bg_circle);
                }
                break;

            case "fri":
                if (!friSelected) {
                    friSelected = true;
                    tv_fri.setTextColor(getResources().getColor(R.color.white));
                    tv_fri.setBackgroundResource(R.drawable.bg_circle_blue);
                } else {
                    friSelected = false;
                    tv_fri.setTextColor(getResources().getColor(R.color.font_grey));
                    tv_fri.setBackgroundResource(R.drawable.bg_circle);
                }
                break;

            case "sat":
                if (!satSelected) {
                    satSelected = true;
                    tv_sat.setTextColor(getResources().getColor(R.color.white));
                    tv_sat.setBackground(getResources().getDrawable(R.drawable.bg_circle_blue));
                } else {
                    satSelected = false;
                    tv_sat.setTextColor(getResources().getColor(R.color.font_grey));
                    tv_sat.setBackground(getResources().getDrawable(R.drawable.bg_circle));
                }
                break;

            case "sun":
                if (!sunSelected) {
                    sunSelected = true;
                    tv_sun.setTextColor(getResources().getColor(R.color.white));
                    tv_sun.setBackground(getResources().getDrawable(R.drawable.bg_circle_blue));
                } else {
                    sunSelected = false;
                    tv_sun.setTextColor(getResources().getColor(R.color.font_grey));
                    tv_sun.setBackground(getResources().getDrawable(R.drawable.bg_circle));
                }
                break;

        }

    }


}
