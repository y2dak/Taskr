package com.taskr.taskr.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.taskr.taskr.Globals;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yatinkaushal on 11/30/16.
 */

public class OfflineDatabase {
    private static final String TABLE_NAME = "tasks";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String START_MONTH = "start_month";
    private static final String START_DAY = "start_day";
    private static final String START_YEAR = "start_year";
    private static final String START_HOUR = "start_hour";
    private static final String START_MINUTES = "start_minutes";

    private static final String END_MONTH = "end_month";
    private static final String END_DAY = "end_day";
    private static final String END_YEAR = "end_year";
    private static final String END_HOUR = "end_hour";
    private static final String END_MINUTES = "end_minute";

    private static final String START = "start";
    private static final String DURATION = "duration";
    private static final String DESIRABILITY = "desirability";

    private static final String URGENCY_MONTH = "urgency_month";
    private static final String URGENCY_DAY = "urgency_day";
    private static final String URGENCY_YEAR = "urgency_year";
    private static final String URGENCY_HOUR = "urgency_hour";
    private static final String URGENCY_MINUTES = "urgency_minutes";

    private static final String IMPORTANCE = "importance";
    private static final String MANUAL = "manual";
    private static final String PRIORITY = "priority";
    private static final String COMPLETION = "completion";

    private static final String NOTES = "notes";


    SQLiteDatabase database;

    public OfflineDatabase(){
        database = SQLiteDatabase.openOrCreateDatabase(Globals.DATABASE_TASKS, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS tasks(id INT, name VARCHAR, start_month INT, start_day INT, start_year INT, start_hour INT, start_minutes INT" +
                ", end_month INT, end_day INT, end_year INT, end_hour INT, end_minute INT, start REAL, duration REAL, desirability REAL" +
                ", urgency_month INT, urgency_day INT, urgency_year INT, urgency_hour INT, urgency_minutes INT, importance REAL" +
                ", manual INT, priority REAL, completion REAL, notes VARCHAR);");
    }

    public long addAutoTask(Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, task.getId());
        contentValues.put(NAME, task.getName());

        contentValues.put(START_MONTH, 0);
        contentValues.put(START_DAY, 0);
        contentValues.put(START_YEAR, 0);
        contentValues.put(START_HOUR, 0);
        contentValues.put(START_MINUTES, 0);

        contentValues.put(END_MONTH, 0);
        contentValues.put(END_DAY, 0);
        contentValues.put(END_YEAR, 0);
        contentValues.put(END_HOUR, 0);
        contentValues.put(END_MINUTES, 0);

        contentValues.put(START, task.getStart());
        contentValues.put(DURATION, task.getDuration());
        contentValues.put(DESIRABILITY, task.getDesirability());

        contentValues.put(URGENCY_MONTH, task.getUrgency().getMonth());
        contentValues.put(URGENCY_DAY, task.getUrgency().getDay());
        contentValues.put(URGENCY_YEAR, task.getUrgency().getYear());
        contentValues.put(URGENCY_HOUR, task.getUrgency().getHours());
        contentValues.put(URGENCY_MINUTES, task.getUrgency().getMinutes());

        contentValues.put(IMPORTANCE, task.getImportance());
        contentValues.put(MANUAL, 0);
        contentValues.put(PRIORITY, task.getPriority());
        contentValues.put(COMPLETION, task.getCompletion());
        contentValues.put(NOTES, task.getNotes());

        return database.insert(TABLE_NAME, null, contentValues);
//        database.execSQL("INSERT INTO task VALUES('" + task.getId() + "','" + task.getName() +
//                "', 0,0,0,0,0,0,0,0,0,0, '" + task.getStart() + "','" + task.getDuration() + "','" +
//                task.getDesirability() + "','" + task.getUrgency().getMonth() + "','" + task.getUrgency().getDay() + "','" + task.getUrgency().getYear() + "','" +
//                task.getUrgency().getHours() + "','" + task.getUrgency().getMinutes() + "','" + task.getImportance() + "','" + (task.getManual() ? 1 : 0) + "','" + task.getPriority() + "','" +
//                task.getCompletion() + "','" + task.getNotes() + "');");
    }

    public long addManualTask(Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, task.getId());
        contentValues.put(NAME, task.getName());

        contentValues.put(START_MONTH, task.getStartDate().getMonth());
        contentValues.put(START_DAY, task.getStartDate().getDay());
        contentValues.put(START_YEAR, task.getStartDate().getYear());
        contentValues.put(START_HOUR, task.getStartDate().getHours());
        contentValues.put(START_MINUTES, task.getStartDate().getMinutes());

        contentValues.put(END_MONTH, task.getEndDate().getMonth());
        contentValues.put(END_DAY, task.getEndDate().getDay());
        contentValues.put(END_YEAR, task.getEndDate().getYear());
        contentValues.put(END_HOUR, task.getEndDate().getHours());
        contentValues.put(END_MINUTES, task.getEndDate().getMinutes());

        contentValues.put(START, task.getStart());
        contentValues.put(DURATION, task.getDuration());
        contentValues.put(DESIRABILITY, task.getDesirability());

        contentValues.put(URGENCY_MONTH, 0);
        contentValues.put(URGENCY_DAY, 0);
        contentValues.put(URGENCY_YEAR, 0);
        contentValues.put(URGENCY_HOUR, 0);
        contentValues.put(URGENCY_MINUTES, 0);

        contentValues.put(IMPORTANCE, task.getImportance());
        contentValues.put(MANUAL, 1);
        contentValues.put(PRIORITY, task.getPriority());
        contentValues.put(COMPLETION, task.getCompletion());
        contentValues.put(NOTES, task.getNotes());

        return database.insert(TABLE_NAME, null, contentValues);
//        database.execSQL("INSERT INTO task VALUES('" + task.getId() + "','" + task.getName() +
//                task.getStartDate().getMonth() + "','" + task.getStartDate().getDay() + "','" + task.getStartDate().getYear()  + "','" + task.getStartDate().getHours()  + "','" + task.getStartDate().getMinutes() + "','" +
//                task.getEndDate().getMonth()  + "','" + task.getEndDate().getDay()  + "','" + task.getEndDate().getYear()  + "','" + task.getEndDate().getHours()  + "','" + task.getEndDate().getMinutes()  + "','" +
//                task.getStart() + "','" + task.getDuration() + "','" +
//                task.getDesirability() + "',0,0,0,0,0,'" + task.getImportance() + "','" + (task.getManual() ? 1 : 0) + "','" + task.getPriority() + "','" +
//                task.getCompletion() + "','" + task.getNotes() + "');");
    }

    public Task getTask(String id) {
        Task task = new Task();
        Cursor c = database.rawQuery("SELECT * FROM tasks WHERE id = '" + id + "'", null);
        if(c.moveToFirst())
        {
            c.getString(1);
        }
        else
        {
            Log.e("DB Error", "Invalid id");
        }
        return task;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM tasks", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                Task task = new Task();
                task.setId(c.getInt(0));
                task.setName(c.getString(1));
                Date startDate = new Date(c.getInt(4), c.getInt(2), c.getInt(3), c.getInt(5), c.getInt(6));
                task.setStartDate(startDate);
                Date endDate = new Date(c.getInt(9), c.getInt(7), c.getInt(8), c.getInt(10), c.getInt(11));
                task.setEndDate(endDate);
                task.setStart(c.getFloat(12));
                task.setDuration(c.getFloat(13));
                task.setDesirability(c.getFloat(14));
                Date urgencyDate = new Date(c.getInt(17), c.getInt(15), c.getInt(16), c.getInt(18), c.getInt(19));
                task.setUrgency(urgencyDate);
                task.setImportance(c.getFloat(20));
                task.setManual(c.getInt(21) == 1);
                task.setPriority(c.getFloat(22));
                task.setCompletion(c.getFloat(23));
                task.setNote(c.getString(24));
                tasks.add(task);
                c.moveToNext();
            }
        }
        return tasks;
    }

    public ArrayList<Task> getManualTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM tasks", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                Task task = new Task();
                task.setId(c.getInt(0));
                task.setName(c.getString(1));
                Date startDate = new Date(c.getInt(4), c.getInt(2), c.getInt(3), c.getInt(5), c.getInt(6));
                task.setStartDate(startDate);
                Date endDate = new Date(c.getInt(9), c.getInt(7), c.getInt(8), c.getInt(10), c.getInt(11));
                task.setEndDate(endDate);
                task.setStart(c.getFloat(12));
                task.setDuration(c.getFloat(13));
                task.setDesirability(c.getFloat(14));
                Date urgencyDate = new Date(c.getInt(17), c.getInt(15), c.getInt(16), c.getInt(18), c.getInt(19));
                task.setUrgency(urgencyDate);
                task.setImportance(c.getFloat(20));
                task.setManual(c.getInt(21) == 1);
                task.setPriority(c.getFloat(22));
                task.setCompletion(c.getFloat(23));
                task.setNote(c.getString(24));
                if (c.getInt(21) == 1) {
                    tasks.add(task);
                }
                c.moveToNext();
            }
        }
        return tasks;
    }

    public ArrayList<Task> getAutomaticTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM tasks", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast() && (c.getInt(21) == 0)) {
                Task task = new Task();
                task.setId(c.getInt(0));
                task.setName(c.getString(1));
                Date startDate = new Date(c.getInt(4), c.getInt(2), c.getInt(3), c.getInt(5), c.getInt(6));
                task.setStartDate(startDate);
                Date endDate = new Date(c.getInt(9), c.getInt(7), c.getInt(8), c.getInt(10), c.getInt(11));
                task.setEndDate(endDate);
                task.setStart(c.getFloat(12));
                task.setDuration(c.getFloat(13));
                task.setDesirability(c.getFloat(14));
                Date urgencyDate = new Date(c.getInt(17), c.getInt(15), c.getInt(16), c.getInt(18), c.getInt(19));
                task.setUrgency(urgencyDate);
                task.setImportance(c.getFloat(20));
                task.setManual(c.getInt(21) == 1);
                task.setPriority(c.getFloat(22));
                task.setCompletion(c.getFloat(23));
                task.setNote(c.getString(24));
                tasks.add(task);
                c.moveToNext();
            }
        }
        return tasks;
    }

    public void deleteTask(int id) {
        database.delete(TABLE_NAME, ID + " = " + id, null);
    }
}
