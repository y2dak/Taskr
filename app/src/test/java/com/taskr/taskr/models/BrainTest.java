package com.taskr.taskr.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kenan Millet on 12/13/2016.
 */
public class BrainTest {
    @Test
    public void autoSchedule() throws Exception {
        Brain brain = new Brain(1, 1, 1, 1);
        Date now = new Date();
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new Task("Desirable Stuff", 13.5f, 10, brain.addToDate(now, Calendar.HOUR_OF_DAY, 20), 1, false, 0, "This stuff is super fun."));
        tasks.add(new Task("Important Stuff", 10, 1, brain.addToDate(now, Calendar.HOUR_OF_DAY, 11), 10, false, 0, "This stuff is important."));
        Schedule s = brain.autoSchedule(now, brain.addToDate(now, Calendar.DATE, 3), tasks);
        s.print();
    }

}