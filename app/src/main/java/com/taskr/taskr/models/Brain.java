package com.taskr.taskr.models;

/**
 * Created by Kenan Millet on 11/27/2016.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Brain {
    private float desWeight;
    private float impWeight;
    private float durWeight;
    private float urgWeight;

    private float timeRemaining(Task t) {
        return (float) (((double)(t.getUrgency().getTime() - new Date().getTime()))/(60.0*60.0*1000.0));
    }
    private float timeNeeded(Task t) {
        return (1.0f - t.getCompletion()) * t.getDuration();
    }

    private double getDesirabilityContribution(Task t) {
        if(t.getFiller()) return -1.0;
        final float desMul = 1.0f;
        final float desExp = 1.0f;
        return Math.pow(desMul * desWeight * t.getDesirability(),desExp);
    }
    private double getImportanceContribution(Task t) {
        if(t.getFiller()) return -1.0;
        final float impMul = 1.0f;
        final float impExp = 1.0f;
        return Math.pow(impMul * impWeight * t.getImportance(),impExp);
    }
    private double getTimeContribution(Task t, float availability) {
        if(t.getFiller()) return -1.0;
        final float timeMul = 1.0f;
        final float timeExp = 1.0f;
        return Math.pow(timeMul * timeNeeded(t) * durWeight / (timeRemaining(t) * urgWeight / availability) ,timeExp);
    }

    private float getPriority(int index, ArrayList<Task> tasks) {
        Task t = tasks.get(index);
        if(t.getFiller()) return Float.MAX_VALUE;

        return (float)(
                getDesirabilityContribution(t) *
                getImportanceContribution(t) *
                getTimeContribution(t, getTimeAvailability(index, tasks))
        );
    }

    private float getTimeAvailability(int index, ArrayList<Task> tasks) {
        float totalOccupancy = 0.0f;
        ArrayList<Task> tasklist = new ArrayList(tasks);
        Collections.sort(tasklist, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) { return o1.getUrgency().compareTo(o2.getUrgency()); }
        });
        List<Task> tlist = tasklist.subList(0, index);
        for(Task t : tasklist) { totalOccupancy += timeNeeded(t); }

        return timeRemaining(tasks.get(index)) - totalOccupancy;
    }
}
