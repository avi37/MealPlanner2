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

import com.example.admin.mealplanner2new.Common.PrefRegister;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Models.ModelCoachList;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public class GSAddCoachFragment extends Fragment {

    PrefRegister prefRegister;

    CoachListAPI coachListAPI;
    private static final String BASE_URL = "http://www.code-fuel.in/meal/api/auth/";


    View view_main;
    TextView textView_noFound;
    RecyclerView recyclerView_coachList;
    Button button_next;

    private ArrayList<ModelCoachList> coachArrayList = new ArrayList<>();

    String selected_coachId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefRegister = new PrefRegister(getContext());

        coachListAPI = getCoachListAPIService(BASE_URL);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsadd_coach, container, false);


        textView_noFound = view_main.findViewById(R.id.gs_coach_tv_noCoach);
        recyclerView_coachList = view_main.findViewById(R.id.gs_coach_recView);
        button_next = view_main.findViewById(R.id.gs_coach_btn_next);

        // Reset list
        if (coachArrayList != null) {
            coachArrayList.clear();
        }

        getCoachList();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = false;

                for (int i = 0; i < coachArrayList.size(); i++) {

                    if (coachArrayList.get(i).isChecked()) {

                        selected_coachId = coachArrayList.get(i).getcId();

                        isChecked = true;
                        break;
                    }
                }


                if (isChecked) {

                    prefRegister.setCoachId(selected_coachId);

                    Fragment someFragment = new SignUpFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.content_get_started, someFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {
                    Toast.makeText(getActivity(), "Please select one coach", Toast.LENGTH_LONG).show();

                }

            }
        });

        return view_main;
    }

    private void getCoachList() {

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView_coachList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        // Dummy Data

        final ModelCoachList healthTypes1 = new ModelCoachList();
        healthTypes1.setcId("1");
        healthTypes1.setcName("Name1");
        healthTypes1.setGym_name("Loose Fat and Gain Muscle");

        final ModelCoachList healthTypes2 = new ModelCoachList();
        healthTypes2.setcId("2");
        healthTypes2.setcName("Body Composition");
        healthTypes2.setGym_name("Gain Weight");


        final ModelCoachList healthTypes3 = new ModelCoachList();
        healthTypes3.setcId("3");
        healthTypes3.setcName("Health");
        healthTypes3.setGym_name("Total Health");


        final ModelCoachList healthTypes4 = new ModelCoachList();
        healthTypes4.setcId("4");
        healthTypes4.setcName("Health");
        healthTypes4.setGym_name("Cognitive Health");

        coachArrayList.add(healthTypes1);
        coachArrayList.add(healthTypes2);
        coachArrayList.add(healthTypes3);
        coachArrayList.add(healthTypes4);


        recyclerView_coachList.setAdapter(new HealthAdapter(getActivity(), coachArrayList));


    }


//------------------------------------------- APIs ----------------------------------------------//

    CoachListAPI getCoachListAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(CoachListAPI.class);
    }

    interface CoachListAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("coach")
        Call<ResCommon> get_coachList();
    }

//--------------------------------------- Adapter Class -----------------------------------------//

    public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.MyViewHolder> {

        Context context;
        private ArrayList<ModelCoachList> coachArrayList;


        public HealthAdapter(Context context, ArrayList<ModelCoachList> coachArrayList) {
            this.coachArrayList = coachArrayList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_coach_list, parent, false);
            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            if (coachArrayList.get(position).isChecked()) {
                holder.simpleCheckedTextView.setChecked(true);
                holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
            } else {
                holder.simpleCheckedTextView.setChecked(false);
                holder.simpleCheckedTextView.setCheckMarkDrawable(null);

            }


            holder.tv_gym.setText(coachArrayList.get(position).getcName());
            holder.simpleCheckedTextView.setText(coachArrayList.get(position).getGym_name());

            // perform on Click Event Listener on CheckedTextView


        }

        @Override
        public int getItemCount() {
            return coachArrayList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            CheckedTextView simpleCheckedTextView;
            TextView tv_gym;

            MyViewHolder(View view) {
                super(view);
                simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.row_addCoach_tv_name);
                tv_gym = view.findViewById(R.id.row_addCoach_tv_gym);


                simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean value = simpleCheckedTextView.isChecked();


                        for (int i = 0; i < coachArrayList.size(); i++) {
                            coachArrayList.get(i).setChecked(false);

                        }


                        if (value) {
                            // set check mark drawable and set checked property to false
                            //  holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.check_ic);
                            simpleCheckedTextView.setChecked(false);
                            coachArrayList.get(getAdapterPosition()).setChecked(false);
                            simpleCheckedTextView.setCheckMarkDrawable(null);
                        } else {
                            // set check mark drawable and set checked property to true

                            //  holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.check);
                            simpleCheckedTextView.setChecked(true);
                            coachArrayList.get(getAdapterPosition()).setChecked(true);
                            simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);


                        }


                        notifyDataSetChanged();


                        boolean isChecked = false;

                        for (int i = 0; i < coachArrayList.size(); i++) {

                            if (coachArrayList.get(i).isChecked()) {
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

}
