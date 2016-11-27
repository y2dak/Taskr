package com.taskr.taskr.models;

import java.util.ArrayList;

public class Schedule {
    private String name;
    private ArrayList<Task> taskList = new ArrayList<>();
    private int[][] timeslots = new int[7][2400];
    // Should have 7 float[] arrays, one for each day of the week
    // Each minute initialized to "zero", meaning that time is free.
    // timeslots[1][1050]   ->   Monday at 10:30 AM (Using military time)
    private int[][] taskDays = new int[20][7];
    // For this schedule, these are the active days for each task
    // taskDays[2][1] = 1   ->   Task 2 is active on Monday
    // Currently supports 20 Tasks		Can be increased
    private String notes;

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasks() {
        return taskList;
    }

    public boolean isTimeslotFree(int dayOfWeek, float startTime, float duration) {
        if (startTime + duration <= 2400) {
            // Case: Task doesn't go past midnight
            for (int x = (int) startTime; x < startTime + duration; x = x + 1) {
                if (timeslots[dayOfWeek][x] == 1) {
                    return false;
                }
            }
        } else {
            for (int x = (int) startTime; x < 2400; x = x + 1) {
                if (timeslots[dayOfWeek][x] == 1) {
                    return false;
                }
            }
            for (int x = 0; x < duration - 2400 + startTime; x = x + 1) {
                if (dayOfWeek != 6) {
                    if (timeslots[dayOfWeek + 1][x] == 1) {
                        return false;
                    }
                } else {
                    if (timeslots[0][x] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isTaskDayActive(int tasknum, int day) {
        return taskDays[tasknum][day] == 1;
    }

    public String getNotes() {
        return notes;
    }

    public void setName(String text) {
        name = text;
    }

    public void fillTimeslot(int dayOfWeek, float startTime, float duration) {
        if (startTime + duration <= 2400) {
            // Case: Task doesn't go past midnight
            for (int x = (int) startTime; x < startTime + duration; x = x + 1) {
                timeslots[dayOfWeek][x] = 1;
            }
        } else {
            // Case: Task goes past midnight
            for (int x = (int) startTime; x < 2400; x = x + 1) {
                timeslots[dayOfWeek][x] = 1;
            }
            for (int x = 0; x < duration - 2400 + startTime; x = x + 1) {
                if (dayOfWeek != 6) {
                    // Case : Task goes past Sunday
                    timeslots[dayOfWeek + 1][x] = 1;
                } else {
                    // Case : Task doesn't go past Sunday
                    timeslots[0][x] = 1;
                }
            }
        }
    }

    public void clearTimeslot(int dayOfWeek, float startTime, float duration) {
        if (startTime + duration <= 2400) {
            // Case: Task doesn't go past midnight
            for (int x = (int) startTime; x < startTime + duration; x = x + 1) {
                timeslots[dayOfWeek][x] = 0;
            }
        } else {
            // Case: Task goes past midnight
            for (int x = (int) startTime; x < 2400; x = x + 1) {
                timeslots[dayOfWeek][x] = 0;
            }
            for (int x = 0; x < duration - 2400 + startTime; x = x + 1) {
                if (dayOfWeek != 6) {
                    // Case : Task goes past Sunday
                    timeslots[dayOfWeek + 1][x] = 0;
                } else {
                    // Case : Task doesn't go past Sunday
                    timeslots[0][x] = 0;
                }
            }
        }
    }

    public void clearSchedule() {
        for (int x = 0; x < 7; x = x + 1) {
            for (int y = 0; y < 2400; y = y + 1) {
                timeslots[x][y] = 0;
            }
        }
        for (int x = 0; x < 20; x = x + 1) {
            for (int y = 0; y < 7; y = y + 1) {
                taskDays[x][y] = 0;
            }
        }
        taskList.clear();
    }

    public void setTaskDay(int tasknum, int day) {
        float start = taskList.get(tasknum).getStart();
        float duration = taskList.get(tasknum).getDuration();
        taskDays[tasknum][day] = 1;
        fillTimeslot(day, start, duration);
    }

    public void clearTaskDay(int tasknum, int day) {
        float start = taskList.get(tasknum).getStart();
        float duration = taskList.get(tasknum).getDuration();
        taskDays[tasknum][day] = 0;
        clearTimeslot(day, start, duration);
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void removeTask(int tasknum) {
        float start = taskList.get(tasknum).getStart();
        float duration = taskList.get(tasknum).getDuration();
        for (int day = 0; day < 7; day++) {
            if (taskDays[tasknum][day] == 1) {
                clearTimeslot(day, start, duration);
            }
        }
        taskList.remove(tasknum);
        for (int x = tasknum; x < taskList.size(); x = x + 1) {
            taskDays[x] = taskDays[x + 1];
        }
        taskDays[taskList.size()] = new int[] {0, 0, 0, 0, 0, 0, 0};
    }

    public void setNotes(String text) {
        notes = text;
    }
}
