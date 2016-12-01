package com.taskr.taskr.models;

import java.util.ArrayList;
import java.util.Date;

public class Schedule {
    private String name;
    private ArrayList<Task> taskList = new ArrayList<>();
    private int[][] timeslots;
    // Should have 7 int[2400] arrays, one for each day of the week
    // Each minute initialized to "zero", meaning that time is free.
    // timeslots[1][1050]   ->   Monday at 10:30 AM (Using military time)
    // If the schedule is not weekly: timeslots[24][1200]   ->   Day 24, 12 PM (Noon)
    private ArrayList<Boolean[]> taskDays;
    // For this schedule, these are the active days for each task
    // taskDays.get(2)[1] = 1   ->   Task 2 is active on Monday
    private Date startDate;
    private Date endDate;
    private String notes;

    public Schedule() {
        timeslots = new int[7][2400];
        taskDays = new ArrayList();
        startDate.setYear(0);                // Use the Year 0 as "No start date indicated"
    }
    public Schedule(String text) {
        name = text;
        timeslots = new int[7][2400];
        taskDays = new ArrayList();
        startDate.setYear(0);                // Use the Year 0 as "No start date Indicated"
    }
    public Schedule(Date start, Date end) {
        startDate = start;
        endDate = end;
        int days = (int)(start.getTime() - end.getTime())/(1000 * 60 * 60 * 24);
        timeslots = new int[days][2400];
        taskDays = new ArrayList();
    }
    public Schedule(String text, Date start, Date end) {
        name = text;
        startDate = start;
        endDate = end;
        int days = (int)(start.getTime() - end.getTime())/(1000 * 60 * 60 * 24);
        timeslots = new int[days][2400];
        taskDays = new ArrayList();
    }

    public String getName() {
        return name;
    }
    public ArrayList<Task> getTasks() {
        return taskList;
    }
    public boolean isTimeslotFree(int day, float startTime, float duration) {
        if (startTime + duration < 2400) {
            // Case: Task doesn't go past midnight
            for (int x = (int) startTime; x < startTime + duration; x = x + 1) {
                if (timeslots[day][x] == 1) {
                    return false; } }
        } else {
            // Case: Task goes past midnight (Task spans across two days)
            // Checking the timeslot for this day
            for (int x = (int) startTime; x < 2400; x = x + 1) {
                if (timeslots[day][x] == 1) {
                    return false; } }
            // Checking the timeslot for the next day
            if( startDate.getYear() != 0) {
                // Case: startDate was given. The schedule is not a weekly schedule.
                for (int x = 0; x < duration - 2400 + startTime; x = x + 1) {
                    if (timeslots[day + 1][x] == 1) {
                        return false; } }
            } else {
                // Case: startDate was not given. The schedule is a weekly schedule.
                for (int x = 0; x < duration - 2400 + startTime; x = x + 1) {
                    if (day != 6) {
                        if (timeslots[day + 1][x] == 1) {
                            return false; }
                    } else {
                        if (timeslots[0][x] == 1) {
                            return false; } } }
            }
        }
        return true;
    }
    public boolean isTaskDayActive(int tasknum, int day) {
        return taskDays.get( tasknum )[day];
    }
    public String getNotes() {
        return notes;
    }

    public void setName(String text) {
        name = text;
    }
    public void fillTimeslot(int day, float startTime, float duration) {
        if (startTime + duration < 2400) {
            // Case: Task doesn't go past midnight
            for (int x = (int) startTime; x < startTime + duration; x = x + 1) {
                timeslots[day][x] = 1; }
        } else {
            // Case: Task goes past midnight (Task spans across two days)
            // Checking the timeslot for this day
            for (int x = (int) startTime; x < 2400; x = x + 1) {
                timeslots[day][x] = 1; }
            // Checking the timeslot for the next day
            if( startDate.getYear() != 0) {
                // Case: startDate was given. The schedule is not a weekly schedule.
                for (int x = 0; x < duration - 2400 + startTime; x = x + 1) {
                    timeslots[day + 1][x] = 1; }
            } else {
                // Case: startDate was not given. The schedule is a weekly schedule.
                for (int x = 0; x < duration - 2400 + startTime; x = x + 1) {
                    if (day != 6) {
                        timeslots[day + 1][x] = 1;
                    } else {
                        timeslots[0][x] = 1; } }
            }
        }
    }
    public void clearTimeslot(int day, float startTime, float duration) {
        if (startTime + duration < 2400) {
            // Case: Task doesn't go past midnight
            for (int x = (int) startTime; x < startTime + duration; x = x + 1) {
                timeslots[day][x] = 0; }
        } else {
            // Case: Task goes past midnight (Task spans across two days)
            // Checking the timeslot for this day
            for (int x = (int) startTime; x < 2400; x = x + 1) {
                timeslots[day][x] = 0; }
            // Checking the timeslot for the next day
            if( startDate.getYear() != 0) {
                // Case: startDate was given. The schedule is not a weekly schedule.
                for (int x = 0; x < duration - 2400 + startTime; x = x + 1) {
                    timeslots[day + 1][x] = 0; }
            } else {
                // Case: startDate was not given. The schedule is a weekly schedule.
                for (int x = 0; x < duration - 2400 + startTime; x = x + 1) {
                    if (day != 6) {
                        timeslots[day + 1][x] = 0;
                    } else {
                        timeslots[0][x] = 0; } }
            }
        }
    }
    public void clearSchedule() {
        for (int x = 0; x < 7; x = x + 1) {
            for (int y = 0; y < 2400; y = y + 1) {
                timeslots[x][y] = 0; } }
        for (int x = 0; x < 20; x = x + 1) {
            for (int y = 0; y < 7; y = y + 1) {
                taskDays.get(x)[y] = false; } }
        taskList.clear();
    }
    public void setTaskDay(int tasknum, int day) {
        float start = taskList.get(tasknum).getStart();
        float duration = taskList.get(tasknum).getDuration();
        taskDays.get(tasknum)[day] = true;
        fillTimeslot(day, start, duration);
    }
    public void clearTaskDay(int tasknum, int day) {
        float start = taskList.get(tasknum).getStart();
        float duration = taskList.get(tasknum).getDuration();
        taskDays.get(tasknum)[day] = false;
        clearTimeslot(day, start, duration);
    }
    public void addTask(Task task) {
        taskList.add(task);
        taskDays.add(new Boolean[] {false, false, false, false, false, false, false} );
    }
    public void removeTask(int tasknum) {
        float start = taskList.get(tasknum).getStart();
        float duration = taskList.get(tasknum).getDuration();
        for (int day = 0; day < 7; day++) {
            if (taskDays.get(tasknum)[day]) {
                clearTimeslot(day, start, duration); } }
        taskList.remove(tasknum);
        for (int x = tasknum; x < taskList.size(); x = x + 1) {
            taskDays.set( x, taskDays.get(x + 1) );
        }
        taskDays.set( taskList.size(), new Boolean[] {false, false, false, false, false, false, false} );
    }
    public void setNotes(String text) {
        notes = text;
    }
}
