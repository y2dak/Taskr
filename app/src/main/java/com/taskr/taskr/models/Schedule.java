package com.taskr.taskr.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        if(start.before(this.start) || end.after(this.end)) return false;
        for (Task task : tasks) {
            if (start.before(task.getEndDate()) && end.after(task.getStartDate())) {
                return false;
            } else if (task.getStartDate().before(end) && task.getEndDate().after(start)) {
                return false;
            } else if (task.getStartDate().equals(start) || task.getEndDate().equals(end)) {
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

    public void print() {
        System.out.print("----------\n|Schedule|\n----------\n");
        System.out.print(name + ":\n\t" + notes + "\n\n");
        ArrayList<Task> tasks = new ArrayList<>(this.tasks);
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.getStartDate().before(o2.getStartDate())) return -1;
                if(o1.getStartDate().after(o2.getStartDate())) return 1;
                return 0;
            }
        });
        for(Task t : tasks)
        {
            System.out.print("\t[TASK] " + t.getName() + " (" + t.getCompletion() * 100 + "% complete)" + ":\n");
            System.out.print("\t|\tStart: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(t.getStartDate()) + "\t\tEnd: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(t.getEndDate()) + "\n");
            System.out.print("\t|\tPriority: " + t.getPriority() + "\n");
            System.out.print("\t|\t\t Imp: " + t.getImportance() + "\n");
            System.out.print("\t|\t\t Des: " + t.getDesirability() + "\n");
            System.out.print("\t|\t\t Urg: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(t.getUrgency()) + "\n");
            System.out.print("\t|\t\t Dur: " + t.getDuration() + "\n\t|\n");
            System.out.print("\t|\tNotes: " + t.getNotes() + "\n");
        }
    }
}
