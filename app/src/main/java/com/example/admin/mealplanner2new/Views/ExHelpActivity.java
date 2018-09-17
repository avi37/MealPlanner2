package com.example.admin.mealplanner2new.Views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.mealplanner2new.R;

public class ExHelpActivity extends AppCompatActivity {

    RecyclerView recyclerView_questions;

    AdapterQueList adapterQueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_help);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView_questions = findViewById(R.id.exHelp_recView);

        setAllQuestions();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void setAllQuestions() {
        String[] indexes = new String[10];
        String[] questions = new String[10];

        for (int i = 0; i < 10; i++) {
            indexes[i] = String.valueOf(i + 1);
            questions[i] = "Question " + String.valueOf(i + 1) + " text here ...";
        }

        adapterQueList = new AdapterQueList(indexes, questions);

        recyclerView_questions.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView_questions.setAdapter(adapterQueList);

        //adapterQueList.notifyDataSetChanged();
    }


//----------------------------------- Adapter Class -----------------------------------------//

    public class AdapterQueList extends RecyclerView.Adapter<AdapterQueList.ViewHolder> {

        private String[] indexArray;
        private String[] questionArray;


        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView_index, textView_question;

            ViewHolder(View v) {
                super(v);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ExHelpActivity.this, ExQueItemActivity.class));
                    }
                });

                textView_index = (TextView) v.findViewById(R.id.row_listHelp_tv_index);
                textView_question = (TextView) v.findViewById(R.id.row_listHelp_tv_que);
            }

            TextView getTextView_index() {
                return textView_index;
            }

            TextView getTextView_question() {
                return textView_question;
            }

        }

        AdapterQueList(String[] indexArray, String[] questionArray) {
            this.indexArray = indexArray;
            this.questionArray = questionArray;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_list_help, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.getTextView_index().setText(indexArray[position]);
            viewHolder.getTextView_question().setText(questionArray[position]);
        }

        @Override
        public int getItemCount() {
            return indexArray.length;
        }


    }


}



