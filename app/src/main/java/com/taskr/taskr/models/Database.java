package com.taskr.taskr.models;

import java.util.ArrayList;
import java.util.Date;

public class Database {
    private ArrayList<Task> taskList = new ArrayList<>();
    private ArrayList<Schedule> schedList = new ArrayList<>();

    public Task getTask(int tasknum) {
        return taskList.get(tasknum);
    }
    public ArrayList<Task> getTaskList() {
        return taskList;
    }
    public String getTaskName(int tasknum) {
        return taskList.get(tasknum).getName();
    }
    public float getTaskStart(int tasknum) {
        return taskList.get(tasknum).getStart();
    }
    public float getTaskDuration(int tasknum) {
        return taskList.get(tasknum).getDuration();
    }
    public float getTaskDesirability(int tasknum) { return taskList.get(tasknum).getDesirability(); }
    public Date getTaskUrgency(int tasknum) {
        return taskList.get(tasknum).getUrgency();
    }
    public float getTaskImportance(int tasknum) {
        return taskList.get(tasknum).getImportance();
    }
    public boolean getTaskManual(int tasknum) {
        return taskList.get(tasknum).getManual();
    }
    public float getTaskPriority(int tasknum) {
        return taskList.get(tasknum).getPriority();
    }
    public float getTaskCompletion( int tasknum ) { return taskList.get(tasknum).getCompletion(); }
    public String getTaskNotes(int tasknum) {
        return taskList.get(tasknum).getNotes();
    }

    public void setTaskNum(int tasknum, String name) {
        taskList.get(tasknum).setName(name);
    }
    public void setTaskStart(int tasknum, float time) {
        taskList.get(tasknum).setStart(time);
    }
    public void setTaskDuration(int tasknum, float time) { taskList.get(tasknum).setDuration(time); }
    public void setTaskDesirability(int tasknum, float time) { taskList.get(tasknum).setDesirability(time); }
    public void setTaskUrgency(int tasknum, Date dateTime) { taskList.get(tasknum).setUrgency(dateTime); }
    public void setTaskImportance(int tasknum, float value) { taskList.get(tasknum).setImportance(value); }
    public void setTaskManual(int tasknum, boolean flag) {
        taskList.get(tasknum).setManual(flag);
    }
    public void setTaskPriority(int tasknum, float value) { taskList.get(tasknum).setPriority(value); }
    public void setTaskCompletion( int tasknum, float value ) { taskList.get( tasknum ).setCompletion( value ); }
    public void setTaskNote(int tasknum, String text) {
        taskList.get(tasknum).setNote(text);
    }

    public void createTask() {
        taskList.add(new Task());
    }
    public void addTask( Task task ) { taskList.add( task ); }

    public Schedule getSchedule(int schednum) {
        return schedList.get(schednum);
    }
    public ArrayList<Schedule> getSchedList() {
        return schedList;
    }
    public String getSchedName(int schednum) {
        return schedList.get(schednum).getName();
    }
    public ArrayList<Task> getSchedTasks(int schednum) { return schedList.get(schednum).getTasks(); }
    public boolean isSchedTimeslotFree(int schednum, int dayOfWeek, float startTime, float duration) {
        return schedList.get(schednum).isTimeslotFree(dayOfWeek, startTime, duration); }
    public boolean isSchedTaskDayActive(int schednum, int tasknum, int day) { return schedList.get(schednum).isTaskDayActive(tasknum, day); }
    public String getSchedNotes(int schednum) {
        return schedList.get(schednum).getNotes();
    }

    public void setSchedName(int schednum, String text) {
        schedList.get(schednum).setName(text);
    }
    public void fillSchedTimeslot(int schednum, int dayOfWeek, float startTime, float duration) {
        schedList.get(schednum).fillTimeslot(dayOfWeek, startTime, duration); }
    public void clearSchedTimeslot(int schednum, int dayOfWeek, float startTime, float duration) {
        schedList.get(schednum).clearTimeslot(dayOfWeek, startTime, duration); }
    public void clearSched(int schednum) {
        schedList.get(schednum).clearSchedule();
    }
    public void setSchedTaskDay(int schednum, int tasknum, int day) { schedList.get(schednum).setTaskDay(tasknum, day); }
    public void clearSchedTaskDay(int schednum, int tasknum, int day) { schedList.get(schednum).clearTaskDay(tasknum, day); }
    public void addTaskToSched(int schednum, int tasknum) { schedList.get(schednum).addTask(taskList.get(tasknum)); }
    public void removeTaskFromSched(int schednum, int schedtasknum) {
        // schedtasknum uses the schedule's TaskList, not the Database's
        schedList.get(schednum).removeTask(schedtasknum); }
    public void setSchedNotes(int schednum, String text) {
        schedList.get(schednum).setNotes(text);
    }

    public void createSched() {
        schedList.add(new Schedule());
    }
    public void addSched( Schedule sched ) { schedList.add( sched ); }
}
