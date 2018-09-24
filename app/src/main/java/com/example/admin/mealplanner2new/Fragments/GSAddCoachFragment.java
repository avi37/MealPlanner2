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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Adapters.BookAutoCompleteAdapter;
import com.example.admin.mealplanner2new.Common.PrefRegister;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Models.CityList;
import com.example.admin.mealplanner2new.Models.CitySendModel;
import com.example.admin.mealplanner2new.Models.ModelCoachList;
import com.example.admin.mealplanner2new.Models.ResCoachList;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public class GSAddCoachFragment extends Fragment {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    PrefRegister prefRegister;
    CoachListAPI coachListAPI;
    View view_main;
    Spinner spinner_country, spinner_state;
    TextView textView_noFound;
    RecyclerView recyclerView_coachList;
    ProgressBar progressBar;
    Button button_next;
    MyAdapter myAdapter;
    String selected_coachId;

    private AutoCompleteTextView edtSearchCity;
    private TextView tvSelectedCity;
    private GetCityName getCityName;
    private ArrayList<ModelCoachList> coachArrayList = new ArrayList<>();
    private String selectedCountry, selectedState, selectedCity = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefRegister = new PrefRegister(getContext());

        coachListAPI = getCoachListAPIService(BASE_URL);

        getCityName = RetrofitClient.getClient(BASE_URL).create(GetCityName.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsadd_coach, container, false);


        spinner_country = view_main.findViewById(R.id.gs_coach_spinner_country);
        spinner_state = view_main.findViewById(R.id.gs_coach_spinner_state);
        edtSearchCity = view_main.findViewById(R.id.gs_coach_spinner_city);
        textView_noFound = view_main.findViewById(R.id.gs_coach_tv_noCoach);
        recyclerView_coachList = view_main.findViewById(R.id.gs_coach_recView);
        button_next = view_main.findViewById(R.id.gs_coach_btn_next);
        progressBar = view_main.findViewById(R.id.gs_coach_progressBar);
        tvSelectedCity = view_main.findViewById(R.id.tvSelectedCity);

        //------ Default State set in STATE_SPINNER ------//
        spinner_state.setSelection(11);


        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                progressBar.setVisibility(View.VISIBLE);

                final CitySendModel citySendModel = new CitySendModel(spinner_state.getSelectedItem().toString());


                getCityName.searchCity(citySendModel).cancel();

                getCityName.searchCity(citySendModel).enqueue(new Callback<ArrayList<CityList>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CityList>> call, Response<ArrayList<CityList>> response) {

                        progressBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {

                            final ArrayList<CityList> cityListArrayList = response.body();
                            edtSearchCity.setAdapter(new BookAutoCompleteAdapter(getActivity(), cityListArrayList));

                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<CityList>> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edtSearchCity.setThreshold(3);


        edtSearchCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object item = parent.getItemAtPosition(position);
                if (item instanceof CityList) {
                    CityList cityList = (CityList) item;
                    selectedCity = cityList.getCity();
                    tvSelectedCity.setVisibility(View.VISIBLE);
                    tvSelectedCity.setText(cityList.getCity());
                    edtSearchCity.setText(cityList.getCity());
                    edtSearchCity.setSelection(selectedCity.length());

                    getCoachList();

                }

            }
        });


        tvSelectedCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCity = null;
                tvSelectedCity.setVisibility(View.GONE);

                edtSearchCity.setText("");
                coachArrayList.clear();
                myAdapter.notifyDataSetChanged();

            }
        });


        // Reset list
        if (coachArrayList != null) {
            coachArrayList.clear();
        }


        if (selectedCity != null) {
            getCoachList();
        }


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
                    selectedCountry = spinner_country.getSelectedItem().toString();
                    selectedState = spinner_state.getSelectedItem().toString();

                    prefRegister.setSchedule("0");
                    prefRegister.setCoachDetails(selected_coachId);

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

        progressBar.setVisibility(View.VISIBLE);

        recyclerView_coachList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        coachListAPI.get_coachList(selectedCity).enqueue(new Callback<List<ResCoachList>>() {
            @Override
            public void onResponse(Call<List<ResCoachList>> call, Response<List<ResCoachList>> response) {
                coachArrayList.clear();
                if (response.isSuccessful()) {

                    if (response.body() != null) {


                        if (response.body().size() > 0) {

                            for (int i = 0; i < response.body().size(); i++) {
                                final ModelCoachList modelCoachList = new ModelCoachList();

                                modelCoachList.setcId(response.body().get(i).getId());
                                modelCoachList.setcName(response.body().get(i).getName());
                                modelCoachList.setGym_name(response.body().get(i).getGym_name());

                                coachArrayList.add(modelCoachList);
                            }

                            myAdapter = new MyAdapter(getActivity(), coachArrayList);
                            recyclerView_coachList.setAdapter(myAdapter);

                            myAdapter.notifyDataSetChanged();


                            progressBar.setVisibility(View.INVISIBLE);


                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            textView_noFound.setVisibility(View.VISIBLE);
                        }

                    }

                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Error in getting response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ResCoachList>> call, Throwable t) {
                coachArrayList.clear();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }


//------------------------------------------- APIs ----------------------------------------------//

    CoachListAPI getCoachListAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(CoachListAPI.class);
    }

    interface CoachListAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("coache")
        @FormUrlEncoded
        Call<List<ResCoachList>> get_coachList(@Field("city") String city);
    }

    interface GetCityName {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("pincode")
        Call<ArrayList<CityList>> searchCity(@Body CitySendModel citySendModel);


    }

//--------------------------------------- Adapter Class -----------------------------------------//

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        Context context;
        private ArrayList<ModelCoachList> coachArrayList;


        public MyAdapter(Context context, ArrayList<ModelCoachList> coachArrayList) {
            this.coachArrayList = coachArrayList;
            this.context = context;
        }

        @NonNull
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


            holder.tv_gym.setText(coachArrayList.get(position).getGym_name());
            holder.simpleCheckedTextView.setText(coachArrayList.get(position).getcName());

            // perform on Click Event Listener on CheckedTextView


        }

        @Override
        public int getItemCount() {
            return coachArrayList.size();
        }


        //----- Holder Class -----//

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
