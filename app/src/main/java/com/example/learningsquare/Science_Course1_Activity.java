package com.example.learningsquare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Science_Course1_Activity extends AppCompatActivity {

    TextView describe1;
    TextView question1;
    TextView clock;
    EditText userAnswer;
    Button quest;
    Button seeAnswer;
    Timer timer;
    TimerTask timertask;
    double time = 0.0;
    int rounded;
    int seconds;
    int minutes;
    int hours;
    String timertext="1";
    int question_number = 0; //to find the question number in the test in each course
    String[] question_string_in_db = {"Q1","Q2","Q3","Q4"}; //used to save answer related to each question in database
    String[] answer_string_in_db = {"A1","A2","A3","A4"}; //used to COMPARE answer related to each question in database

    public static final String courseNumber = " ";//send course number to math course 1 activity


    FirebaseFirestore db= FirebaseFirestore.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_course1);

        ///////////read course number from mathematic course activity
        Intent intent=getIntent();
        String course_number = intent.getStringExtra(Science_CoursesActivity.courseNumber);
        DocumentReference noteRef= db.collection("Science").document(course_number);


        /////////////////////


        describe1 = (TextView) findViewById(R.id.textview_description1);
        question1 = (TextView) findViewById(R.id.textview_q1);

        quest = (Button) findViewById(R.id.Question_button);
        seeAnswer = (Button) findViewById(R.id.answer_button);
        userAnswer = (EditText) findViewById(R.id.edittext_userAnswer);
        clock = (TextView) findViewById(R.id.textview_timer);
        timer = new Timer();
        Date currentTime= Calendar.getInstance().getTime();



        noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String desc=documentSnapshot.getString("Description");

                    describe1.setText(desc);

                    quest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            question1.setVisibility(View.VISIBLE);
                            question1.setText(documentSnapshot.getString("Q1"));
                            userAnswer.setVisibility(View.VISIBLE);
                            seeAnswer.setVisibility(View.VISIBLE);
                            clock.setVisibility(View.VISIBLE);

                            timertask = new TimerTask() {
                                @Override
                                public void run() {
                                    time++;
                                    rounded = (int) Math.round(time);
                                    seconds = ((rounded % 86400)%3600)%60;
                                    minutes = ((rounded % 86400)%3600)/60;
                                    hours = ((rounded % 86400) / 3600);
                                    timertext=String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds);
                                    clock.setText(timertext);

                                }
                            };
                            timer.scheduleAtFixedRate(timertask,0,1000);
                        }
                    });

                    seeAnswer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String email="";
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                email = user.getEmail();
                            }



                            Map<String, String> usersAnswer=new HashMap<>();

                            if(question_number<=3)
                            {

                                /////////// save answer in database /////////
                                String right_answer = documentSnapshot.getString(answer_string_in_db[question_number]);
                                String true_false = "";
                                if(userAnswer.getText().toString().equals(right_answer))
                                {
                                    true_false = "Right";
                                }
                                else
                                {
                                    true_false = "Wrong";
                                }


                                usersAnswer.put(currentTime.toString() + "Timer:"+clock.getText().toString(), userAnswer.getText().toString()+" "+ true_false);

                                db.collection("Users").document(email).collection("Science").document(course_number).collection("Questions").document(question_string_in_db[question_number]).set(usersAnswer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("save result tag.", "DocumentSnapshot successfully written!");
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("save result tag.", "Error writing document", e);
                                            }
                                        });

                                if (question_number==3)
                                {
                                    Intent course1Intent = new Intent(Science_Course1_Activity.this,Science_result1_Activity.class);
                                    course1Intent.putExtra(courseNumber,course_number);
                                    startActivity(course1Intent);
                                }
                                else
                                {
                                    /////////// load question from database ////////////
                                    question_number++;
                                    question1.setText(documentSnapshot.getString(question_string_in_db[question_number]));
                                    userAnswer.setText(""); //make the answer box empty
                                    userAnswer.requestFocus();
                                }

                            }



                        }
                    });
                }
                else{
                    Toast.makeText(Science_Course1_Activity.this, "Document does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Science_Course1_Activity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Back to Mathematics list
    @Override
    public void onBackPressed() {
        Intent scienceActivity = new Intent(this,Science_CoursesActivity.class);
        startActivity(scienceActivity);
    }
}