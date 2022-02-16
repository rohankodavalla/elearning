package com.example.learningsquare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CoursePageActivity extends AppCompatActivity {

    LinearLayout english;
    LinearLayout programming;
    LinearLayout mathematics;
    LinearLayout science;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);

        english = (LinearLayout) findViewById(R.id.english_course_but);
        science = (LinearLayout) findViewById(R.id.science_course_but);
        mathematics = (LinearLayout) findViewById(R.id.math_course_but);
        programming = (LinearLayout) findViewById(R.id.programming_course_but);

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent englishIntent = new Intent(CoursePageActivity.this, English_coursesActivity.class);
                startActivity(englishIntent);
            }
        });

        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scienceIntent = new Intent(CoursePageActivity.this, Science_CoursesActivity.class);
                startActivity(scienceIntent);
            }
        });

        mathematics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mathematicsIntent = new Intent(CoursePageActivity.this, Mathematic_CoursesActivity.class);
                startActivity(mathematicsIntent);
            }
        });

        programming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent programmingIntent = new Intent(CoursePageActivity.this, Programming_coursesActivity.class);
                startActivity(programmingIntent);
            }
        });


    }

    //Back to home page
    @Override
    public void onBackPressed() {
        Intent homeActivity = new Intent(this,HomePageActivity.class);
        startActivity(homeActivity);
    }

}