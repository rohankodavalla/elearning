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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TakeExamActivity extends AppCompatActivity {
    TextView describe1;
    TextView question1;
    TextView result;
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
    String[] lesson_string_in_db = {"Lesson1","Lesson2","Lesson3","Lesson4","Lesson5","Lesson6"};
    String[] topic_string_in_db = {"English","Mathematics","Science","Programming"};
    public static final String courseNumber = " ";//send course number to math course 1 activity


    int random_number_lesson;
    String lesson;
    int random_number_topic;
    String topic;
    int random_number_question;
    String question;
    int done=0;



    FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_exam);

        describe1 = (TextView) findViewById(R.id.textview_description1);
        question1 = (TextView) findViewById(R.id.textview_q1);

        quest = (Button) findViewById(R.id.Question_button);
        seeAnswer = (Button) findViewById(R.id.answer_button);
        userAnswer = (EditText) findViewById(R.id.edittext_userAnswer);
        result = (TextView) findViewById(R.id.textview_timer);
        timer = new Timer();
        Date currentTime = Calendar.getInstance().getTime();

        next_question();




}

    public void next_question()
    {
        if (done<10)
        {
            random_number_lesson = new Random().nextInt(6);
            lesson = lesson_string_in_db[random_number_lesson];
            random_number_topic = new Random().nextInt(4);
            topic = topic_string_in_db[random_number_topic];
            random_number_question = new Random().nextInt(4);
            question = question_string_in_db[random_number_question];

            db_reference();

        }
        else
        {
            onBackPressed();
        }
    }



    public void db_reference()
    {
        DocumentReference noteRef = db.collection(topic).document(lesson);

        noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {


                    question1.setVisibility(View.VISIBLE);
                    question1.setText(documentSnapshot.getString(question));
                    userAnswer.setVisibility(View.VISIBLE);
                    seeAnswer.setVisibility(View.VISIBLE);
                    result.setVisibility(View.VISIBLE);




                    seeAnswer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (documentSnapshot.getString(answer_string_in_db[random_number_question]).equals(userAnswer.getText().toString())){
                                result.setText("your answer is right");
                            }
                            else{

                                result.setText("The correct answer is {"+ documentSnapshot.getString(answer_string_in_db[random_number_question])+"}.\n So your answer is wrong");
                            }
                            done++;
                            next_question();
                            userAnswer.setText("");
                        }
                    });
                } else {
                    //  Toast.makeText(TakeExamActivity.this
                    //        , "Document does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TakeExamActivity.this, "Error", Toast.LENGTH_SHORT).show();
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