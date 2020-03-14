package com.oyvindmonsen.workout_tracker_api.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;

public class Workout {

    private String name;
    private String measurement;
    private boolean moreIsBetterSorting;
    private ArrayList<WorkoutEntry> entries;


    public Workout() {

        this.entries = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public float getMaxRep() {

        Comparator<Float> sorting = this.moreIsBetterSorting
                ? (c1, c2) ->  Math.round(c1 - c2)
                : (c1, c2) ->  Math.round(c2 - c1);

        return this.entries.stream().map(e -> e.getAmmount()).max(sorting).get();
    }

    public boolean isMoreIsBetterSorting() {
        return moreIsBetterSorting;
    }

    public void setMoreIsBetterSorting(boolean moreIsBetterSorting) {
        this.moreIsBetterSorting = moreIsBetterSorting;
    }

    public void addEntry(WorkoutEntry entry) {
        entries.add(entry);
    }

    public ArrayList<WorkoutEntry> getEntries() {
        return entries;
    }
}

