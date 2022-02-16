package com.example.learningsquare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class HomePageActivity extends AppCompatActivity {

    LinearLayout ChooseTheCourse;
    LinearLayout ShowTheProgress;
    LinearLayout TakeTheExam;
    Button logout;
    Button Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ChooseTheCourse = (LinearLayout) findViewById(R.id.choose_course_but);
        ShowTheProgress = (LinearLayout) findViewById(R.id.show_my_prog_but);
        TakeTheExam = (LinearLayout) findViewById(R.id.take_exam_but);
        logout = (Button) findViewById(R.id.Logout_button);
        Add=(Button) findViewById(R.id.Add_button);

        ChooseTheCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coursesIntent = new Intent(HomePageActivity.this,CoursePageActivity.class);
                startActivity(coursesIntent);
            }
        });

        ShowTheProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProgressIntent = new Intent(HomePageActivity.this,ShowProgressActivity.class);
                startActivity(ProgressIntent);
            }
        });

        TakeTheExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent examIntent = new Intent(HomePageActivity.this,TakeExamActivity.class);
                startActivity(examIntent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent Loginintent = new Intent(HomePageActivity.this,LoginActivity.class);
                startActivity(Loginintent);
            }
        });


    }


    //Exit application and releasing resources
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Hope to see you soon!", Toast.LENGTH_LONG).show();
        finishAffinity();
        System.exit(0);  // Releasing resources
    }
}