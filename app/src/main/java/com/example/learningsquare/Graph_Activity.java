package com.example.learningsquare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Graph_Activity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    int number_of_not_null_in_date_array = 0;
    int[] number_of_correct_answer_in_each_day;
    int total_correct_answers=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_layout);


        String sorted_correct_days[];
        sorted_correct_days = getIntent().getStringArrayExtra("sorted_correct_days");
        number_of_correct_answer_in_each_day = getIntent()
                .getIntArrayExtra("number_of_correct_answer_in_each_day");

        /////find percentage of correct answers
        for (int i=0; i<number_of_correct_answer_in_each_day.length; i++)
        {
            total_correct_answers += number_of_correct_answer_in_each_day[i];
        }
        /////end of find percentage of correct answers



        GraphView graphView = (GraphView) findViewById(R.id.graph_id);

        Date date[] = new Date[sorted_correct_days.length];
        DateFormat fmt = new SimpleDateFormat("MMM dd");


        for(int i=0; i<sorted_correct_days.length; i++)
        {
            if (sorted_correct_days[i] != null)
            {
                try {
                    date[i] = fmt.parse(sorted_correct_days[i].substring(4));
                    number_of_not_null_in_date_array++;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        series = new LineGraphSeries<DataPoint>();

        // set date label formatter
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(Graph_Activity.this, fmt));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(6);//6 is used because of the screen size


        //series = new LineGraphSeries<DataPoint>();
        DataPoint dataPoint = null;

        for(int j=0; j<number_of_not_null_in_date_array; j++)
        {
            dataPoint = new DataPoint(date[j]
                    ,((double)number_of_correct_answer_in_each_day[j]/(double)total_correct_answers)*100);
            series.appendData(dataPoint,true,number_of_not_null_in_date_array);
        }

        //will be used in future updates
        //graphView.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        //graphView.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        //graphView.getViewport().setScrollable(true);  // activate horizontal scrolling
        //graphView.getViewport().setScrollableY(true);  // activate vertical scrolling

        //set bounds manually
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(100);
        graphView.getViewport().setMinY(0);


        graphView.addSeries(series);

    }


}



