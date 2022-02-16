package com.example.learningsquare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Science_CoursesActivity extends AppCompatActivity {

    //here we define keys to identify which course is chosen, so when we want to write on data base
    //we use this keys to write data in specific collection and document

    LinearLayout course1;
    LinearLayout course2;
    LinearLayout course3;
    LinearLayout course4;
    LinearLayout course5;
    LinearLayout course6;

    public static final String courseNumber = " ";//send course number to math course 1 activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_courses);

        course1 = (LinearLayout) findViewById(R.id.science_c1_but);
        course2 = (LinearLayout) findViewById(R.id.science_c2_but);
        course3 = (LinearLayout) findViewById(R.id.science_c3_but);
        course4 = (LinearLayout) findViewById(R.id.science_c4_but);
        course5 = (LinearLayout) findViewById(R.id.science_c5_but);
        course6 = (LinearLayout) findViewById(R.id.science_c6_but);

        course1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent course1Intent = new Intent(Science_CoursesActivity.this,Science_Course1_Activity.class);
                course1Intent.putExtra(courseNumber,"Lesson1");
                startActivity(course1Intent);
            }
        });

        course2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent course1Intent = new Intent(Science_CoursesActivity.this,Science_Course1_Activity.class);
                course1Intent.putExtra(courseNumber,"Lesson2");
                startActivity(course1Intent);
            }
        });

        course3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent course1Intent = new Intent(Science_CoursesActivity.this,Science_Course1_Activity.class);
                course1Intent.putExtra(courseNumber,"Lesson3");
                startActivity(course1Intent);
            }
        });

        course4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent course1Intent = new Intent(Science_CoursesActivity.this,Science_Course1_Activity.class);
                course1Intent.putExtra(courseNumber,"Lesson4");
                startActivity(course1Intent);
            }
        });

        course5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent course1Intent = new Intent(Science_CoursesActivity.this,Science_Course1_Activity.class);
                course1Intent.putExtra(courseNumber,"Lesson5");
                startActivity(course1Intent);
            }
        });

        course6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent course1Intent = new Intent(Science_CoursesActivity.this,Science_Course1_Activity.class);
                course1Intent.putExtra(courseNumber,"Lesson6");
                startActivity(course1Intent);
            }
        });

    }

    //Back to Courses
    @Override
    public void onBackPressed() {
        Intent courseActivity = new Intent(this,CoursePageActivity.class);
        startActivity(courseActivity);
    }
}