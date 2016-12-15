package com.taskr.taskr.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Random;

public class Task implements Parcelable {
    private String name;
    private Date startDate;
    private Date endDate;
    private float start;
    private float duration;
    private float desirability;
    private Date urgency;
    private float importance;
    private boolean manual;
    private float priority;
    private float completion;
    private String notes;
    private int id;

    public Task() {
        this.name = "";
        this.start = 0.0f;
        this.startDate = new Date(0L);
        this.endDate = new Date(0L);
        this.duration = 0.0f;
        this.desirability = 0.0f;
        this.urgency = new Date();
        this.importance = 0.0f;
        this.manual = false;
        this.priority = 0.0f;
        this.completion = 0.0f;
        this.notes = "";
        this.id = new Random().nextInt();
    }

    public Task(Task o) {
        this.name = o.name;
        this.startDate = new Date(o.startDate.getTime());
        this.endDate = new Date(o.endDate.getTime());
        this.start = o.start;
        this.duration = o.duration;
        this.desirability = o.desirability;
        this.urgency = new Date(o.urgency.getTime());
        this.importance = o.importance;
        this.manual = o.manual;
        this.priority = o.priority;
        this.completion = o.completion;
        this.notes = o.notes;
        this.id = new Random().nextInt();
    }

    public Task(final String name, final float duration, final float desirability, final Date urgency, final float importance, final boolean manual, final float completion, final String notes) {
        this.name = name;
        this.duration = duration;
        this.desirability = desirability;
        this.urgency = urgency;
        this.endDate = new Date(0L);
        this.startDate = new Date(0L);
        this.importance = importance;
        this.manual = manual;
        this.completion = completion;
        this.notes = notes;
        this.id = new Random().nextInt();
    }

    public Task(String name, Date startDate, Date endDate, boolean manual, Float completion, String notes) {
        this.name = name;
        this.startDate = new Date(startDate.getTime());
        this.endDate = new Date(endDate.getTime());
        this.urgency = new Date(0L);
        this.manual = manual;
        this.completion = completion;
        this.notes = notes;
        this.id = new Random().nextInt();
    }

    public String getName() { return name; }
    public float getStart() { return start; }
    public float getDuration() { return duration; }
    public float getDesirability() { return desirability; }
    public Date getUrgency() { return urgency; }
    public float getImportance() { return importance; }
    public boolean getManual() { return manual; }
    public float getPriority() { return priority; }
    public float getCompletion() { return completion; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public String getNotes() { return notes; }

    public void setName(String text) { name = text; }
    public void setStart(float time) { start = time; }
    public void setDuration(float time) { duration = time; }
    public void setDesirability(float value) { desirability = value; }
    public void setUrgency(Date dateTime) { urgency = dateTime; }
    public void setImportance(float value) { importance = value; }
    public void setManual(boolean flag) { manual = flag; }
    public void setPriority(float value) { priority = value; }
    public void setCompletion(float value) { completion = value; }
    public void setNote(String text) { notes = text; }
    public boolean areEqual(Task task2) {
        return getName().equals(task2.getName()) &&
                getStart() == task2.getStart() &&
                getDuration() == task2.getDuration() &&
                getDesirability() == task2.getDesirability() &&
                getUrgency() == task2.getUrgency() &&
                getImportance() == task2.getImportance() &&
                getManual() == task2.getManual() &&
                getPriority() == task2.getPriority() &&
                getCompletion() == task2.getCompletion() &&
                getStartDate() == task2.getStartDate() &&
                getEndDate() == task2.getEndDate() &&
                getNotes().equals(task2.getNotes());
    }

    Task(Parcel in) {
        name = in.readString();
        start = in.readFloat();
        duration = in.readFloat();
        desirability = in.readFloat();
        long tmpUrgency = in.readLong();
        urgency = tmpUrgency != -1 ? new Date(tmpUrgency) : null;
        importance = in.readFloat();
        manual = in.readByte() != 0x00;
        priority = in.readFloat();
        completion = in.readFloat();
        notes = in.readString();
        tmpUrgency = in.readLong();
        startDate = tmpUrgency != -1 ? new Date(tmpUrgency) : null;
        tmpUrgency = in.readLong();
        endDate = tmpUrgency != -1 ? new Date(tmpUrgency) : null;
        id = in.readInt();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(start);
        dest.writeFloat(duration);
        dest.writeFloat(desirability);
        dest.writeLong(urgency != null ? urgency.getTime() : -1L);
        dest.writeFloat(importance);
        dest.writeByte((byte) (manual ? 0x01 : 0x00));
        dest.writeFloat(priority);
        dest.writeFloat(completion);
        dest.writeString(notes);
        dest.writeLong(startDate != null ? startDate.getTime() : -1L);
        dest.writeLong(endDate != null ? endDate.getTime() : -1L);
        dest.writeInt(id);
    }

    @SuppressWarnings("unused")
    final static Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) { return new Task(in); }

        @Override
        public Task[] newArray(int size) { return new Task[size]; }
    };


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
