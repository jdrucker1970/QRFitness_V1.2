package com.example.jonas.qrfitness_v12;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;


/**
 * Created by Jonas on 6/24/2017.
 */

public class calendar extends AppCompatActivity {

    private static final String TAG = "Calendar";

    private CalendarView mCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day){
                String date = (month+1) + "/" + day + "/" + year;
                Log.d(TAG, "onSelectedDayChange: mm/dd/yyyy: " + date);
            }
        });
    }
}

