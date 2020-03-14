package com.oyvindmonsen.workout_tracker_api.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "workout_entries")
@Entity
public class WorkoutEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userId;

    private String workoutName;
    private Date date;
    private String measurement;
    private boolean moreIsBetterSorting;
    private float ammount;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmmount() {
        return ammount;
    }

    public void setAmmount(float ammount) {
        this.ammount = ammount;
    }

    public boolean isMoreIsBetterSorting() {
        return moreIsBetterSorting;
    }

    public void setMoreIsBetterSorting(boolean moreIsBetterSorting) {
        this.moreIsBetterSorting = moreIsBetterSorting;
    }
}
