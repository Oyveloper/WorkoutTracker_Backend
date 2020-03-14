package com.oyvindmonsen.workout_tracker_api.model;

import java.util.ArrayList;

public class Workout {

    private String name;
    private String measurement;
    private ArrayList<String> progressions;
    private float maxRep;
    public ArrayList<WorkoutEntry> entries;

    public Workout() {
        entries = new ArrayList<WorkoutEntry>();
    }



    private void updateMaxRep() {
        float max = 0;
        for (WorkoutEntry entry : entries) {
            if (entry.getAmmount() > max) {
                max = entry.getAmmount();
            }

        }

        this.maxRep = max;
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

    public ArrayList<String> getProgressions() {
        return progressions;
    }

    public void setProgressions(ArrayList<String> progressions) {
        this.progressions = progressions;
    }

    public void addProgression(String progression) {

        this.progressions.add(progression);

    }

    public float getMaxRep() {
        return maxRep;
    }

    public void setMaxRep(float maxRep) {
        this.maxRep = maxRep;
    }

    public void addEntry(WorkoutEntry entry) {
        entries.add(entry);
        updateMaxRep();
    }
}

