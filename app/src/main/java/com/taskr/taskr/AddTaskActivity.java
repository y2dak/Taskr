package com.taskr.taskr;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.taskr.taskr.models.Task;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText hoursNeeded;
    private SeekBar importanceBar;
    private SeekBar desirabilityBar;

    private String date;
    private int month;
    private int day;
    private int year;

    private String time;
    private int hour;
    private int minute;

    private EditText notes;
    private CheckBox fillerCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskName = (EditText) findViewById(R.id.taskName);
        hoursNeeded = (EditText) findViewById(R.id.taskTime);
        notes = (EditText) findViewById(R.id.notes);
        fillerCheck = (CheckBox) findViewById(R.id.checkBox);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskName.getText().toString().trim().isEmpty() ||
                        hoursNeeded.getText().toString().trim().isEmpty() ||
                        notes.getText().toString().trim().isEmpty() ||
                        date == null ||
                        time == null) {
                    Snackbar.make(view, "Cannot leave field(s) empty", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
//                    Toast.makeText(AddTaskActivity.this, "Task Added!", Toast.LENGTH_SHORT).show();
                    Task task = new Task(taskName.getText().toString(), Float.valueOf(hoursNeeded.getText().toString()), Float.valueOf(desirabilityBar.getProgress()), new Date(month, day, year, hour, minute), Float.valueOf(importanceBar.getProgress()), fillerCheck.isChecked(), notes.getText().toString());
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Spinner spinner = (Spinner) findViewById(R.id.taskPriority);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.task_priority, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

        final TextView desirabilityHeading = (TextView) findViewById(R.id.taskDesirabilityHeading);
        desirabilityBar = (SeekBar) findViewById(R.id.taskDesirability);

        desirabilityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress + 1;
                desirabilityHeading.setText("Desirability: " + progressChanged);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                desirabilityHeading.setText("Desirability: " + progressChanged);
            }
        });
        final TextView importanceHeading = (TextView) findViewById(R.id.taskImportanceHeading);
        importanceBar = (SeekBar) findViewById(R.id.taskImportance);

        importanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress + 1;
                importanceHeading.setText("Importance: " + progressChanged);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                importanceHeading.setText("Importance: " + progressChanged);
            }
        });

//        final TextView urgencyHeading = (TextView) findViewById(R.id.taskUrgencyHeading);
//        urgencySeeker = (SeekBar) findViewById(R.id.taskUrgency);
//
//        urgencySeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            int progressChanged = 0;
//
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
//                progressChanged = progress + 1;
//                urgencyHeading.setText("Urgency: " + progressChanged);
//            }
//
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//            }
//
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                urgencyHeading.setText("Urgency: " + progressChanged);
//            }
//        });
//
//        Switch switch1 = (Switch) findViewById(R.id.switch1);
//        switch1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggleUrgency();
//            }
//        });


        final TextView dateTxt = (TextView) findViewById(R.id.dateTxt);
        final TextView timeTxt = (TextView) findViewById(R.id.timeTxt);

        Button dateBtn = (Button) findViewById(R.id.dateBtn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dateDialog = new AlertDialog.Builder(AddTaskActivity.this);
                final View dateView = getLayoutInflater().inflate(R.layout.date_picker, null, false);
                dateDialog.setView(dateView);
                dateDialog.setTitle("Due Date");
                dateDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatePicker dp = (DatePicker) dateView.findViewById(R.id.datePicker);
                        month = dp.getMonth()+1;
                        day = dp.getDayOfMonth();
                        year = dp.getYear();
                        date = month + "/" + dp.getDayOfMonth() + "/" + dp.getYear();
                        dateTxt.setText(date);

                    }
                });
                dateDialog.show();
            }
        });

        Button timeBtn = (Button) findViewById(R.id.timebtn);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder timeDialog = new AlertDialog.Builder(AddTaskActivity.this);
                final View timeView = getLayoutInflater().inflate(R.layout.time_picker, null, false);
                timeDialog.setView(timeView);
                timeDialog.setTitle("Due Time");
                timeDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TimePicker tp = (TimePicker) timeView.findViewById(R.id.timePicker);
                        hour = tp.getHour();
                        minute = tp.getMinute();
                        time = tp.getHour() + ":"+ (tp.getMinute() < 10 ? "0"+tp.getMinute() : tp.getMinute());
                        timeTxt.setText(time);
                    }
                });
                timeDialog.show();
            }
        });
    }

//    private void toggleUrgency() {
//        if (findViewById(R.id.dateBtn).getVisibility() == View.VISIBLE) {
//            findViewById(R.id.dateBtn).setVisibility(View.INVISIBLE);
//            findViewById(R.id.timebtn).setVisibility(View.INVISIBLE);
//            findViewById(R.id.dateTxt).setVisibility(View.INVISIBLE);
//            findViewById(R.id.timeTxt).setVisibility(View.INVISIBLE);
//            findViewById(R.id.taskUrgency).setVisibility(View.VISIBLE);
//            ((TextView)findViewById(R.id.taskUrgencyHeading)).setText("Urgency: " + new Integer(1 + ((SeekBar)findViewById(R.id.taskUrgency)).getProgress()));
//        } else {
//            findViewById(R.id.dateBtn).setVisibility(View.VISIBLE);
//            findViewById(R.id.timebtn).setVisibility(View.VISIBLE);
//            findViewById(R.id.dateTxt).setVisibility(View.VISIBLE);
//            findViewById(R.id.timeTxt).setVisibility(View.VISIBLE);
//            findViewById(R.id.taskUrgency).setVisibility(View.INVISIBLE);
//            ((TextView)findViewById(R.id.taskUrgencyHeading)).setText("Urgency:");
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return false;
    }

}
