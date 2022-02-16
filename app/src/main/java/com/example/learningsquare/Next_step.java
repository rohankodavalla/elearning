package com.example.learningsquare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Next_step extends AppCompatActivity {

    Button show_graph_btn; //to plot the graph

    FirebaseFirestore db= FirebaseFirestore.getInstance(); //define variable to access database
    String email; //used to find data related to the current user
    String[] question_string_in_db = {"Q1","Q2","Q3","Q4"}; //used to save answer related to each question in database
    String[] lesson_string_in_db = {"Lesson1","Lesson2","Lesson3","Lesson4","Lesson5","Lesson6"}; //used to save lesson related to each topic in database
    String data_received_from_database = "";
    int counter_date=0;

    String result []=new String[lesson_string_in_db.length * question_string_in_db.length];//extract right or wrong answers from db
    String date_and_timer []=new String[lesson_string_in_db.length * question_string_in_db.length];//extract date from db
    String correct_timer []=new String[lesson_string_in_db.length * question_string_in_db.length];//extract timer from db
    String correct_time []=new String[lesson_string_in_db.length * question_string_in_db.length];//extract time from db
    String output []=new String[lesson_string_in_db.length * question_string_in_db.length];//to display output
    String correct_days[] = new String[lesson_string_in_db.length * question_string_in_db.length];// save the days which the user gave the right answers
    String sorted_correct_days[] = new String[correct_days.length];//to sort correct days array
    int number_of_correct_answer_in_each_day[] = new int[lesson_string_in_db.length * question_string_in_db.length];
    TextView show_progress_textview; //to show received data from database

    //int counter = 0;
    int last_index;
//    String check="";
//    int lesson_counter = 0;
//    int correct_answer_counter = 0;
//    boolean handle_exception = false;
//    String returned_value = "";
//    String correct_answers[];

    int db_counter=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_step);

        String received_topic = getIntent().getStringExtra("topic");

        //define show graph button
        show_graph_btn = (Button) findViewById(R.id.show_graph_btn);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //confirm user authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            email = user.getEmail();
        }
        ///////////////////////////////////////////////////////////////////////////////////////////

        show_progress_textview = (TextView) findViewById(R.id.show_data_textview);
        show_progress_textview.setText("Loading Data...!");

        for (int i=0; i<lesson_string_in_db.length; i++)
        {

            for(int j=0; j<question_string_in_db.length; j++)
            {
                //receive and save data in date_and_timer array
                call_from_db(received_topic,lesson_string_in_db[i], question_string_in_db[j]);
            }

        }








        //show progress diagram
        show_graph_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////////
                Intent graph_intent = new Intent(Next_step.this,Graph_Activity.class);
                graph_intent.putExtra("sorted_correct_days", sorted_correct_days);
                graph_intent.putExtra("number_of_correct_answer_in_each_day", number_of_correct_answer_in_each_day);
                startActivity(graph_intent);
                /////////////////

            }
        });



    }










    //call data from database
    public void call_from_db(String topic, String lesson_string_in_db, String question_string_in_db)
    {
        db.collection("Users").document(email).collection(topic)
                .document(lesson_string_in_db).collection("Questions")
                .document(question_string_in_db).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();


                            if (doc.exists()) {
                                // Extract result (right or wrong)
                                //data_received_from_database = doc.getData().toString();
                                date_and_timer[db_counter]= doc.getData().toString();
                                if (date_and_timer[date_and_timer.length-1] != null)
                                    //display_results();
                                    process_data();//process data received form database and prepare it to display to user
                            }

                            else
                            {
                                Toast.makeText(Next_step.this, "Error1", Toast.LENGTH_SHORT).show();
                                Log.d("Document", "No data");
                            }
                            db_counter++;
                        }
                        else
                        {
                            Toast.makeText(Next_step.this, "Error2", Toast.LENGTH_SHORT).show();
                            Log.d("Document", "Not successful");
                        }
                    }
                });
    }









    //process data received form database and prepare it to display to user
    public void process_data()
    {
        for (int p=0; p<date_and_timer.length; p++)
        {
            data_received_from_database = date_and_timer[p];
            last_index = data_received_from_database.indexOf("}");
            result[p] = data_received_from_database.substring(last_index - 5, last_index);

            /////////////////////////////////////////////////////////////////
            // Extract date
            date_and_timer[p]= data_received_from_database;
            //counter++;

            if ((p+1)==lesson_string_in_db.length*question_string_in_db.length)
            {
                for (int n=0;n<result.length;n++){
                    if (result[n].equals("Right")){
                        correct_days[counter_date]=date_and_timer[n].substring(1,11);
                        last_index = data_received_from_database.indexOf("EST");
                        correct_time[counter_date]=date_and_timer[n].substring(last_index-9,last_index-1);
                        last_index = data_received_from_database.indexOf("Timer");
                        correct_timer[counter_date]=date_and_timer[n].substring(last_index+6, last_index+14);

                        counter_date++;
                    }
                }

            }

        }

        int move = 0;
        ///////////////////sort correct days
        for(int m=0; m<correct_days.length; m++)
        {
            if (correct_days[m] != null)
            {
                sorted_correct_days[move] = correct_days[m];
                move++;
            }
        }

        correct_days = sorted_correct_days.clone();

        for(int m=0; m<correct_days.length; m++)
        {
            if (correct_days[m] != null)
            {
                sorted_correct_days[m] = correct_days[m].substring(8,10);
            }
        }



        String temp="";
        boolean sorted = false;

        while(!sorted) {
            sorted = true;
            for (int i = 0; i < sorted_correct_days.length - 1; i++)
            {
                if (sorted_correct_days[i+1] != null)
                {
                    if ( Integer.valueOf(sorted_correct_days[i]) > Integer.valueOf(sorted_correct_days[i+1]))
                    {
                        //sort sorted_correct_days (stored dates)
                        temp = sorted_correct_days[i];
                        sorted_correct_days[i] = sorted_correct_days[i+1];
                        sorted_correct_days[i+1] = temp;
                        //sort correct_days (stored number of correct answers in that day)
                        temp = correct_days[i];
                        correct_days[i] = correct_days[i+1];
                        correct_days[i+1] = temp;

                        sorted = false;
                    }
                }

            }
        }
        for (int i=0; i<sorted_correct_days.length; i++)
        {
            sorted_correct_days[i] = null;
        }
        //////////////////end of sorting

        //////////counting correct answers in each day
        int count_correct_answers = 0;
        int move_through_array = 0;
        String temp_compare = correct_days[0];
        for (int i=0; i<correct_days.length; i++)
        {
            if (correct_days[i] != null)
            {
                if (correct_days[i+1] != null)
                {
                    if (correct_days[i+1].equals(temp_compare))
                    {
                        count_correct_answers++;
                    }
                    else
                    {
                        temp_compare = correct_days[i+1];
                        number_of_correct_answer_in_each_day[move_through_array] = count_correct_answers+1;
                        sorted_correct_days[move_through_array] = correct_days[i];
                        move_through_array++;
                        count_correct_answers = 0;
                    }
                }
                else
                {
                    number_of_correct_answer_in_each_day[move_through_array] = count_correct_answers+1;
                    sorted_correct_days[move_through_array] = correct_days[i];
                    move_through_array++;
                    count_correct_answers = 0;
                }
            }
        }
        /////////end of counting correct answers in each day

        display_results();
    }




    public void display_results()
    {
        //process_data();//process data received form database and prepare it to display to user

        output = sorted_correct_days;//choose data that you want to display in textview

        String x="";
        for (int k=0;k<output.length;k++){
            if (output[k] != null)
            {
                x+="\nYou answerd "+number_of_correct_answer_in_each_day[k]+" questions corectly on "
                        +output[k];
                show_progress_textview.setText(x);
            }
        }

    }


    //Back to home page
    @Override
    public void onBackPressed() {
        Intent homeActivity = new Intent(this,HomePageActivity.class);
        startActivity(homeActivity);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////

}


