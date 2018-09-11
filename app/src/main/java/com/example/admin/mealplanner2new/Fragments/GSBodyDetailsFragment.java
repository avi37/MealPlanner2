package com.example.admin.mealplanner2new.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.mealplanner2new.R;

import java.util.Arrays;


public class GSBodyDetailsFragment extends Fragment {

    View view_main;
    TextView textView_height, textView_age, textView_weight;
    Button button_next;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsbody_details, container, false);

        textView_height = view_main.findViewById(R.id.gsBD_tv_height);
        textView_age = view_main.findViewById(R.id.gsBD_tv_age);
        textView_weight = view_main.findViewById(R.id.gsBD_tv_weight);
        button_next = view_main.findViewById(R.id.gsBD_btn_next);


        textView_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog("height");
            }
        });

        textView_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog("age");
            }
        });

        textView_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog("weight");
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment someFragment = new GSLevelFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                transaction.replace(R.id.content_get_started, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });


        return view_main;
    }


    private void showConfirmDialog(String type) {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.layout_dialog_body_details, null);

        final TextView tvField = alertLayout.findViewById(R.id.GSBD_edit_tv_field);
        final EditText etField = alertLayout.findViewById(R.id.GSBD_edit_et_field);

        if (type.equals("height")) {
            tvField.setText("Height");
            etField.setText(textView_height.getText().toString());
        } else if (type.equals("age")) {
            tvField.setText("Age");
            etField.setText(textView_age.getText().toString());
        } else {
            tvField.setText("Weight");
            etField.setText(textView_weight.getText().toString());
        }


        etField.setSelection(etField.getText().length());

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(alertLayout)
                .setTitle("Confirm your meal")
                .setPositiveButton("Submit", null)   //Set to null. Will be overridden while the onclick
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                Button b1 = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                b1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        boolean wantToCloseDialog = false;

                        String nOp, recipe_name;

                        nOp = etField.getText().toString();
                        recipe_name = tvField.getText().toString();

                        if (nOp.equals("")) {
                            etField.setError("value required");
                            etField.requestFocus();
                        } else if (Integer.parseInt(nOp) > 15) {
                            etField.setError("Enter proper value");
                            etField.requestFocus();
                        } else if (recipe_name.equals("")) {
                            tvField.setError("Recipe name required");
                            tvField.requestFocus();
                        } else {
                            wantToCloseDialog = true;
                        }

                        //if both fields are validate
                        if (wantToCloseDialog) {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


}
