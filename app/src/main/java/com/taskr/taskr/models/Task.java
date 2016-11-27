package com.taskr.taskr.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class Task implements Parcelable {
    private String name;
    private float start;
    private float duration;
    private float desirability;
    private Date urgency;
    private float importance;
    private boolean filler;
    private float priority;
    private float completion;
    private String notes;

    public Task() {
        name = "";
        start = 0.0f;
        duration = 0.0f;
        desirability = 0.0f;
        urgency = new Date();
        importance = 0.0f;
        filler = false;
        priority = 0.0f;
        completion = 0.0f;
        notes = "";
    }

    public Task(final String name, final float duration, final float desirability, final Date urgency, final float importance, final boolean filler, final float completion, final String notes) {
        this.name = name;
        this.duration = duration;
        this.desirability = desirability;
        this.urgency = urgency;
        this.importance = importance;
        this.filler = filler;
        this.completion = completion;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public float getStart() {
        return start;
    }

    public float getDuration() {
        return duration;
    }

    public float getDesirability() {
        return desirability;
    }

    public Date getUrgency() {
        return urgency;
    }

    public float getImportance() {
        return importance;
    }

    public boolean getFiller() {
        return filler;
    }

    public float getPriority() {
        return priority;
    }

    public float getCompletion() { return completion; }
   
    public String getNotes() {
        return notes;
    }

    public void setName(String text) {
        name = text;
    }

    public void setStart(float time) {
        start = time;
    }

    public void setDuration(float time) {
        duration = time;
    }

    public void setDesirability(float value) {
        desirability = value;
    }

    public void setUrgency(Date dateTime) {
        urgency = dateTime;
    }

    public void setImportance(float value) {
        importance = value;
    }

    public void setFiller(boolean flag) {
        filler = flag;
    }

    public void setPriority(float value) {
        priority = value;
    }
   
    public void setCompletion(float value) {
        completion = value;
    }   

    public void setNote(String text) {
        notes = text;
    }

    public boolean areEqual(Task task2) {
        return getName().equals(task2.getName()) &&
                getStart() == task2.getStart() &&
                getDuration() == task2.getDuration() &&
                getDesirability() == task2.getDesirability() &&
                getUrgency() == task2.getUrgency() &&
                getImportance() == task2.getImportance() &&
                getFiller() == task2.getFiller() &&
                getPriority() == task2.getPriority() &&
                getCompletion() == task2.getCompletion() &&
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
        filler = in.readByte() != 0x00;
        priority = in.readFloat();
        completion = in.readFloat();
        notes = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(start);
        dest.writeFloat(duration);
        dest.writeFloat(desirability);
        dest.writeLong(urgency != null ? urgency.getTime() : -1L);
        dest.writeFloat(importance);
        dest.writeByte((byte) (filler ? 0x01 : 0x00));
        dest.writeFloat(priority);
        dest.writeFloat(completion);
        dest.writeString(notes);
    }

    @SuppressWarnings("unused")
    final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
