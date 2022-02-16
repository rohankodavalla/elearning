package com.example.learningsquare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowProgressActivity extends AppCompatActivity {

    LinearLayout show_my_progress_english_btn; //to request data from database
    LinearLayout show_my_progress_mathematics_btn; //to request data from database
    LinearLayout show_my_progress_science_btn; //to request data from database
    LinearLayout show_my_progress_programming_btn; //to request data from database

    FirebaseFirestore db= FirebaseFirestore.getInstance(); //define variable to access database
    String email; //used to find data related to the current user


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_progress_layout);

        //confirm user authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            email = user.getEmail();
        }

        //define buttons
        show_my_progress_english_btn = (LinearLayout) findViewById(R.id.english_course_progress_but);
        show_my_progress_programming_btn = (LinearLayout) findViewById(R.id.programming_course_progress_but);
        show_my_progress_mathematics_btn = (LinearLayout) findViewById(R.id.math_course_progress_but);
        show_my_progress_science_btn = (LinearLayout) findViewById(R.id.science_course_progress_but);



        //////////////////////////////////////////////////////


        /////////////////////buttons' functions

        //show progress diagram
        show_my_progress_english_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_intent = new Intent(ShowProgressActivity.this,Next_step.class);
                next_intent.putExtra("topic","English");
                startActivity(next_intent);

            }
        });


        //show science progress diagram
        show_my_progress_science_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_intent = new Intent(ShowProgressActivity.this,Next_step.class);
                next_intent.putExtra("topic","Science");
                startActivity(next_intent);

            }
        });


        //show progress diagram
        show_my_progress_mathematics_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_intent = new Intent(ShowProgressActivity.this,Next_step.class);
                next_intent.putExtra("topic","Math");
                startActivity(next_intent);

            }
        });


        //show progress diagram
        show_my_progress_programming_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent next_intent = new Intent(ShowProgressActivity.this,Next_step.class);
                next_intent.putExtra("topic","Programming");
                startActivity(next_intent);

            }
        });
    }

        ////////////////////end of buttons' functions



    //Back to home page
    @Override
    public void onBackPressed() {
        Intent homeActivity = new Intent(this,HomePageActivity.class);
        startActivity(homeActivity);
    }

}


