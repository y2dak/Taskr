package com.taskr.taskr.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yatinkaushal on 12/10/16.
 */

public class Schedule {
    private String name;
    private Date start;
    private Date end;
    public ArrayList<Task> tasks = new ArrayList<>();
    private String notes;

    public Schedule(String name, Date start, Date end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isTimeSlotAvailable(Date start, Date end) {
        for (Task task : tasks) {
            if (start.compareTo(task.getEndDate()) <= 0 && end.compareTo(task.getStartDate()) >= 0) {
                return false;
            } else if (task.getStartDate().compareTo(end) <= 0 && task.getEndDate().compareTo(start) >= 0) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getNotes() {
        return notes;
    }
}
