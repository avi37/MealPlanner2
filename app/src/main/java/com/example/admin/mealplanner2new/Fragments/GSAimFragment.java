package com.example.admin.mealplanner2new.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Models.HealthTypes;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;


public class GSAimFragment extends Fragment {

    View view_main;
    Button button_next;
    private RecyclerView rvList;
    private ArrayList<HealthTypes> healthTypesArrayList = new ArrayList<>();
    private ArrayList<Integer> selectedList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsaim, container, false);

        button_next = view_main.findViewById(R.id.gsAim_btn_next);
        button_next.setVisibility(View.GONE);
        rvList = view_main.findViewById(R.id.rvList);

        // Reset list
        if (healthTypesArrayList != null) {
            healthTypesArrayList.clear();
        }

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean isChecked = false;

                for (int i = 0; i < healthTypesArrayList.size(); i++) {

                    if (healthTypesArrayList.get(i).isChecked()) {
                        isChecked = true;
                        break;
                    }
                }


                if (isChecked) {

                    Fragment someFragment = new GSBodyDetailsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.content_get_started, someFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {
                    Toast.makeText(getActivity(), "Select one category", Toast.LENGTH_LONG).show();
                }


            }
        });


        return view_main;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        // Dummy Data

        final HealthTypes healthTypes1 = new HealthTypes();
        healthTypes1.setCategoryName("Body Composition");
        healthTypes1.setId("1");
        healthTypes1.setHealthName("Loose Fat and Gain Muscle");

        final HealthTypes healthTypes2 = new HealthTypes();
        healthTypes2.setCategoryName("Body Composition");
        healthTypes2.setId("1");
        healthTypes2.setHealthName("Gain Weight");


        final HealthTypes healthTypes3 = new HealthTypes();
        healthTypes3.setCategoryName("Health");
        healthTypes3.setId("2");
        healthTypes3.setHealthName("Total Health");


        final HealthTypes healthTypes4 = new HealthTypes();
        healthTypes4.setCategoryName("Health");
        healthTypes4.setId("2");
        healthTypes4.setHealthName("Cognitive Health");

        healthTypesArrayList.add(healthTypes1);
        healthTypesArrayList.add(healthTypes2);
        healthTypesArrayList.add(healthTypes3);
        healthTypesArrayList.add(healthTypes4);


        rvList.setAdapter(new HealthAdapter(getActivity(), healthTypesArrayList));


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.MyViewHolder> {

        Context context;
        private ArrayList<HealthTypes> healthTypes;


        public HealthAdapter(Context context, ArrayList<HealthTypes> healthTypes) {
            this.healthTypes = healthTypes;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gs_aim_fragment, parent, false);
            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {


            if (position > 0 && healthTypes.get(position).getCategoryName().equals(healthTypes.get(position - 1).getCategoryName())) {
                holder.tvCatName.setVisibility(View.GONE);
            } else {
                holder.tvCatName.setVisibility(View.VISIBLE);
            }


            if (healthTypes.get(position).isChecked()) {
                holder.simpleCheckedTextView.setChecked(true);
                holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
            } else {
                holder.simpleCheckedTextView.setChecked(false);
                holder.simpleCheckedTextView.setCheckMarkDrawable(null);

            }


            holder.tvCatName.setText(healthTypes.get(position).getCategoryName());
            holder.simpleCheckedTextView.setText(healthTypes.get(position).getHealthName());

            // perform on Click Event Listener on CheckedTextView


        }

        @Override
        public int getItemCount() {
            return healthTypes.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            CheckedTextView simpleCheckedTextView;
            TextView tvCatName;

            MyViewHolder(View view) {
                super(view);
                simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.tvHealthType);
                tvCatName = view.findViewById(R.id.tvCategoryName);


                simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean value = simpleCheckedTextView.isChecked();


                        for (int i = 0; i < healthTypesArrayList.size(); i++) {
                            healthTypesArrayList.get(i).setChecked(false);

                        }


                        if (value) {
                            // set check mark drawable and set checked property to false
                            //  holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.check_ic);
                            simpleCheckedTextView.setChecked(false);
                            healthTypes.get(getAdapterPosition()).setChecked(false);
                            simpleCheckedTextView.setCheckMarkDrawable(null);
                        } else {
                            // set check mark drawable and set checked property to true

                            //  holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.check);
                            simpleCheckedTextView.setChecked(true);
                            healthTypes.get(getAdapterPosition()).setChecked(true);
                            simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);


                        }


                        notifyDataSetChanged();


                        boolean isChecked = false;

                        for (int i = 0; i < healthTypes.size(); i++) {

                            if (healthTypes.get(i).isChecked()) {
                                isChecked = true;
                                break;
                            }
                        }

                        if (isChecked) {
                            button_next.setVisibility(View.VISIBLE);
                        } else {
                            button_next.setVisibility(View.GONE);
                        }

                    }
                });

            }


        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);


    }
}
