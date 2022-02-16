package com.example.learningsquare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Programming_result1_Activity extends AppCompatActivity {

    TextView database_answer;
    TextView User_answer;
    TextView right_wrong;
    String email;
    int place1;
    int place;
    Button exit;
    String[] answer_string_in_db = {"A1","A2","A3","A4"}; //used to COMPARE answer related to each question in database
    String[] question_string_in_db = {"Q1","Q2","Q3","Q4"}; //used to save answer related to each question in database
    String user_answer="";
    String Answer = "";
    FirebaseFirestore db= FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_result1);

        ///////////read course number from mathematic course activity
        Intent intent=getIntent();
        String course_number = intent.getStringExtra(Programming_coursesActivity.courseNumber);
        DocumentReference noteRef= db.collection("Programming").document(course_number);

        /////////////////////

        database_answer = (TextView) findViewById(R.id.textview_a1);
        User_answer = (TextView) findViewById(R.id.textview_useranswer);
        right_wrong = (TextView) findViewById(R.id.right_wrong);
        exit = (Button) findViewById(R.id.exit_button);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Programming_coursesActivity.class));
            }
        });

        noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    //read the correct answer

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {

                        email = user.getEmail();
                        for (int j=0;j<4;j++)
                        {
                            int finalJ = j;
                            db.collection("Users").document(email).collection("Programming")
                                    .document(course_number).collection("Questions")
                                    .document(question_string_in_db[j]).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot doc = task.getResult();

                                        user_answer = doc.getData().toString();

                                        place = user_answer.indexOf("}");
                                        place1 = user_answer.indexOf("=");
                                        Answer = Answer+"The correct answer is {"+documentSnapshot.getString(answer_string_in_db[finalJ])
                                                + "} " + "\nand your answer is {"+ user_answer.substring(place1+1,place-5)
                                                + "}\nso your answer is "+user_answer.substring(place-5, place)+"\n\n ";

                                        database_answer.setText(Answer);
                                    }
                                }
                            });
                        }


                    }
                }
                else{
                    Toast.makeText(Programming_result1_Activity.this, "Document does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Programming_result1_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d("Programming_tag", e.toString());
            }


        });
    }

    //End of a course, back to Mathematics
    @Override
    public void onBackPressed() {
        Intent programActivity = new Intent(this,Programming_coursesActivity.class);
        startActivity(programActivity);
    }
}