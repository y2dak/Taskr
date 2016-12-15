package com.taskr.taskr;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewLoader;
import com.taskr.taskr.models.Brain;
import com.taskr.taskr.models.Database;
import com.taskr.taskr.models.Schedule;
import com.taskr.taskr.models.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener, WeekViewLoader {

    private WeekView mWeekView;
    private Database database;
    private Brain brain;
    private ArrayList<WeekViewEvent> events = new ArrayList<>();
    private int i = 0;
    private ArrayList<Task> splits = new ArrayList<>();
    private int day = 1;
    private int hour = 12;
    private ArrayList<WeekViewEvent> events1 = new ArrayList<>();
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setTitle("Schedule 1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        brain = new Brain(1.0f, 1.0f, 1.0f, 1.0f);
        database = new Database();

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        mWeekView.setWeekViewLoader(this);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        i = 0;
        events1.clear();
        Date date = new Date();
        date.setDate(date.getDate() + 5);
        System.out.println("Date: " + date.toString());
        Schedule schedule = brain.autoSchedule(new Date(), date, database.getAllTasks());
        ArrayList<Task> tasks = schedule.getTasks();
        System.out.println("size: " + tasks.size());
        for (Task task : tasks) {
            events1.add(convert(task));
        }
        mWeekView.notifyDatasetChanged();
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        if (i < 2) {
            i++;
            return new ArrayList<>();
        }
        return events1;
    }

    public WeekViewEvent convert(Task task) {
        return new WeekViewEvent(task.getId(), task.getName(), task.getStartDate().getYear()+1900, task.getStartDate().getMonth()+1, task.getStartDate().getDate(), task.getStartDate().getHours(), task.getStartDate().getMinutes(), task.getEndDate().getYear()+1900, task.getEndDate().getMonth()+1, task.getEndDate().getDate(), task.getEndDate().getHours(), task.getEndDate().getMinutes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_edit:
                startActivity(new Intent(CalendarActivity.this, MainActivity.class));
                break;
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add:
                startActivity(new Intent(CalendarActivity.this, MainActivity.class));
                break;
        }
        return false;
    }

    @Override
    public double toWeekViewPeriodIndex(Calendar instance) {
        return 0;
    }

    @Override
    public List<? extends WeekViewEvent> onLoad(int periodIndex) {
        return null;
    }
}
