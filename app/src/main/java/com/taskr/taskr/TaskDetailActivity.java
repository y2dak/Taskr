package com.taskr.taskr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.taskr.taskr.models.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView taskName;
    private TextView taskHours;
    private TextView taskType;
    private TextView taskImportance;
    private TextView taskDesirability;
    private TextView taskStart;
    private TextView taskEnd;
    private TextView taskNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Task task = getIntent().getParcelableExtra(Globals.TASK);
        taskName = (TextView) findViewById(R.id.taskName);
        taskHours = (TextView) findViewById(R.id.taskHours);
        taskType = (TextView) findViewById(R.id.taskType);
        taskImportance = (TextView) findViewById(R.id.taskImportance);
        taskDesirability = (TextView) findViewById(R.id.taskDesirability);
        taskStart = (TextView) findViewById(R.id.taskStart);
        taskEnd = (TextView) findViewById(R.id.taskEnd);
        taskNotes = (TextView) findViewById(R.id.taskNotes);

        DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm", Locale.US);

        if (task.getManual()) {
            taskHours.setVisibility(View.GONE);
            taskImportance.setVisibility(View.GONE);
            taskDesirability.setVisibility(View.GONE);

            taskStart.setText("Start date: " + df.format(task.getStartDate()));
            taskEnd.setText("End date: " + df.format(task.getEndDate().toString()));
            taskType.setText("Manual");
        } else {
            taskStart.setVisibility(View.GONE);
            taskEnd.setText("Deadline: " + df.format(task.getUrgency()));
            taskType.setText("Automatic");
            taskImportance.setText("Importance: " + task.getImportance());
            taskDesirability.setText("Desirability: " + task.getDesirability());
        }
        taskName.setText(task.getName());
        taskHours.setText(((int) task.getDuration()) + " hours to complete");
        taskNotes.setText("Notes:\n\n" + task.getNotes());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return false;
    }

}
