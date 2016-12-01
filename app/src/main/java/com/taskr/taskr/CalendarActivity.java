package com.taskr.taskr;

import android.content.Intent;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewLoader;
import com.taskr.taskr.models.Brain;
import com.taskr.taskr.models.OfflineDatabase;
import com.taskr.taskr.models.Schedule;
import com.taskr.taskr.models.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener, WeekViewLoader {

    private WeekView mWeekView;
    private OfflineDatabase offlineDatabase;
    private Brain brain;
    private ArrayList<WeekViewEvent> events = new ArrayList<>();
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setTitle("Schedule 1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        brain = new Brain(1.0f, 1.0f, 1.0f, 1.0f);
        offlineDatabase = new OfflineDatabase();

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
        events.clear();
        ArrayList<Task> tasks = offlineDatabase.getAutomaticTasks();
        Date start = new Date();
        start.setYear(2016);
        Date end = start;
        end.setDate(3);
        Schedule schedule = brain.autoSchedule(start, end, tasks);
        ArrayList<Task> tasks1 = schedule.getTasks();
        for (Task task : tasks1) {
            events.add(new WeekViewEvent(task.getId(), task.getName(), task.getStartDate().getYear(), task.getStartDate().getMonth(), task.getStartDate().getDay(), task.getStartDate().getHours(), task.getStartDate().getMinutes(), task.getEndDate().getYear(), task.getEndDate().getMonth(), task.getEndDate().getDay(), task.getEndDate().getHours(), task.getEndDate().getMinutes()));
        }
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        System.out.println("onMonthChange");
        List<WeekViewEvent> events1 = new ArrayList<>();
        if (i > 1) {
            //(long id, String name, int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute)
            events1.add(new WeekViewEvent(1, "Sleep", 2016, 12, 4, 22, 00, 2016, 12, 5, 6, 15));
            events1.add(new WeekViewEvent(2, "Study for Physics final Part 1", 2016, 12, 1, 14, 0, 2016, 12, 1, 15, 0));
            events1.add(new WeekViewEvent(3, "Study for Physics final Part 2", 2016, 12, 3, 14, 0, 2016, 12, 3, 15, 0));
            events1.add(new WeekViewEvent(4, "Study for Physics final Part 3", 2016, 12, 5, 14, 0, 2016, 12, 5, 15, 0));
        }
        i++;
        return events1;
    }

    public WeekViewEvent convert(Task task) {
        return new WeekViewEvent(task.getId(), task.getName(), task.getStartDate().getYear(), task.getStartDate().getMonth(), task.getStartDate().getDay(), task.getStartDate().getHours(), task.getStartDate().getMinutes(), task.getEndDate().getYear(), task.getEndDate().getMonth(), task.getEndDate().getDay(), task.getEndDate().getHours(), task.getEndDate().getMinutes());
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
