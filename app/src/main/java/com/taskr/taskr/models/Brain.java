package com.taskr.taskr.models;

/**
 * Created by Kenan Millet on 11/27/2016.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Brain {
    private float desWeight;
    private float impWeight;
    private float durWeight;
    private float urgWeight;

    public float getPriority(int index, ArrayList<Task> tasks)
    {
        final float desMul = 1.0f;
        final float desExp = 1.0f;
        final float impMul = 1.0f;
        final float impExp = 1.0f;
        final float timeMul = 1.0f;
        final float timeExp = 1.0f;

        Task t = tasks.get(index);

        return Math.pow(desMul * desWeight * t.getDesirability(),desExp)
                * Math.pow(impMul * impWeight * t.getImportance(),impExp)
                * Math.pow(timeMul * (1.0f - t.getCompletion()) * t.getDuration() * durWeight
                / (
                (((float)(t.getUrgency().getTime() - new Date().getTime()))/(60.0f*60.0f*1000.0f)) * urgWeight
                / getTimeAvailability(index, tasks)
                ),timeExp);
    }

    public float getTimeAvailability(int index, ArrayList<Task> tasks)
    {
        float total = 0.0f;
        ArrayList<Task> tasklist = new ArrayList(tasks);
        long cTime = new Date().getTime();
        Collections.sort(tasklist, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getUrgency().compareTo(o2.getUrgency());
            }
        });
        List<Task> tlist = tasklist.subList(0, index);
        for(Task t : tasklist) { total += (1.0f - t.getCompletion()) * t.getDuration(); }

        return total;
    }
}