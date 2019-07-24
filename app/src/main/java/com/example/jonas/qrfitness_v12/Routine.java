package com.example.jonas.qrfitness_v12;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Jonas on 7/1/2017.
 */

public class Routine extends AppCompatActivity {
    EditText editDate, editRoutine, editSetNum, editReps, editWeight;
    Button btn_save_rest;
    Button btn_viewDb;
    DatabaseHelper myDb;

    // Used for storing data *****************
    public static final String preferences = "pref";
    public static final String saveIt = "saveKey";
    SharedPreferences sharedPreferences;
    //SharedPreferences.Editor editor = sharedPreferences.edit();

    // **************************************


    int current_set = 1; // Default value for first set and will increment as until exit routine
    //int timer_set =


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        // *************************************************************************
        //sharedPreferences = this.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        // *************************************************************************

        myDb = new DatabaseHelper(this);

        editDate = (EditText)findViewById(R.id.TV_Date);
        // Get current date to auto populate the date field when entering a set completed
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String date = df.format(cal.getTime());
        editDate.setText(date);

        editRoutine = (EditText)findViewById(R.id.TV_Routine);
        editSetNum = (EditText)findViewById(R.id.TV_SetNum);
        // Sets the current set number
        editSetNum.setText(String.valueOf(current_set));

        editReps = (EditText)findViewById(R.id.TV_Reps);
        editWeight = (EditText)findViewById(R.id.TV_Weight);
        btn_save_rest = (Button)findViewById(R.id.button_save_rest);
        btn_viewDb = (Button)findViewById(R.id.view_db_data_button);
        SaveAndRest();
        viewAllDb();
    }


    // Called when Save and Rest button in routine is selected
    // This class will firstly save the current set info in the user's SQLite database then increment
    // the set number and finally the alert dialog is called seeded with the CountDownTimer count
    public  void SaveAndRest(){
        btn_save_rest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Display timer -----------------------------------------------------------
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Routine.this); // Used for rest timer
                        View mView = getLayoutInflater().inflate(R.layout.activity_rest_timer, null);
                        final EditText time = mView.findViewById(R.id.restTimerDisplay);
                        Button start = mView.findViewById(R.id.btn_rest_timer_start);

                        // Clear contents timer so user can enter alternative value
                        time.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view){
                                time.setText("");
                            }
                        });

                        //SharedPreferences.Editor editor = sharedPreferences.edit();
                        //editor.putInt(saveIt,15);
                        //editor.commit();

                        // Show last used rest time *********************************************
                        //sharedPreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);
                        //if (sharedPreferences.contains(saveIt)){
                         //   Integer num = sharedPreferences.getInt(saveIt, 0);
                         //   time.setText(num);
                        //}

                        // **********************************************************************

                        // Show dialog
                        alertDialog.setView(mView);
                        final AlertDialog dialog = alertDialog.create();
                        dialog.show();

                        // Save and rest button pushed
                        start.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) { // When start button is pushed

                            // Store rest time ***************************************
                            //int saved = Integer.parseInt(time.getText().toString());
                            //SharedPreferences.Editor editor = sharedPreferences.edit();
                            //editor.putInt(saveIt, saved);
                            //editor.commit();
                            // *********************************************************

                            // Close keypad used to enter rest time if start button has been pushed
                            // if already open
                            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(time.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                            // Get rest time entered by user
                            Long num = Long.parseLong(time.getText().toString());
                            int rest = num.intValue();

                            // See if current rest time is same as previous. If not, store new rest time
                            // in memory.
                            //SharedPreferences mPrefs = getSharedPreferences("label", 0); // Get
                            //int mInt = mPrefs.getInt("tag", 0); // 0 = value if var not found



                            num *= 1000;
                                CountDownTimer timer = new CountDownTimer(num,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String timer = String.valueOf(millisUntilFinished/1000);
                                        time.setText(timer);
                                    }
                                    @Override
                                    public void onFinish() {
                                        dialog.dismiss();
                                    }
                                };
                                timer.start();
                            }
                        });
                        // End Display Timer -------------------------------------------------------


                        // Increment current set counter
                        current_set++;
                        editSetNum.setText(String.valueOf(current_set));
                        // Insert current set in users SQLite db
                        boolean isInserted = myDb.insertTableData(editDate.getText().toString(),
                                editRoutine.getText().toString(),
                                editSetNum.getText().toString(),
                                editReps.getText().toString(),
                                editWeight.getText().toString());
                        // Display if data added successfully to database or not
                        if(isInserted = true)
                            Toast.makeText(Routine.this, "Data Inserted Successfully",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Routine.this, "Data Insert FAILED!!!",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    // Called when view all db button is selected
    public void viewAllDb(){
        btn_viewDb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor db = myDb.getAllData();
                        if(db.getCount() == 0){
                            //Show message
                            showMessage("Error","No Data in DB");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(db.moveToNext()){
                                buffer.append("Id:" + db.getString(0) + "\n");
                                buffer.append("Date: " + db.getString(1) + "\n");
                                buffer.append("Routine: " + db.getString(2) + "\n");
                                buffer.append("Number of Sets: " + db.getString(3) + "\n");
                                buffer.append("Reps: " + db.getString(4) + "\n");
                                buffer.append("Weight: " + db.getString(5) + "\n\n");
                        }
                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );

    }


    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
