package com.example.jonas.qrfitness_v12;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    public Button calendar;
    public Button QRCodeScanner;
    public Button Routines;
    //DatabaseHelper myDb;

    public void init(){



        calendar = (Button)findViewById(R.id.button_calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cal = new Intent(MainActivity.this, calendar.class);
                startActivity(cal);
            }
        });


        QRCodeScanner = (Button)findViewById(R.id.button_QR);
        QRCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent QR = new Intent(MainActivity.this, QRCodeScanner.class);
                startActivity(QR);
            }
        });

        Routines = (Button)findViewById(R.id.button_routines);
        Routines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rout = new Intent(MainActivity.this, Routine.class);
                startActivity(rout);
            }
        });

    }

    /*
    // Used for calendar date picker
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Call database constructor to create new database
        setContentView(R.layout.activity_main);
        //myDb = new DatabaseHelper(this);
        init();
    }

}
