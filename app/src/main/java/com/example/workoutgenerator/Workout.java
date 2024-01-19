package com.example.workoutgenerator;

import org.parceler.Parcel;

@Parcel
 public class Workout {
    private String prepareTime;
    private  String workTime;
    private  String restTime;
    private  String numOfCycles;
    private String numOfSets;
    private  String restBetweenSetsTime;
    private String strings;

    public Workout(){}

    public Workout(String prepareTime, String workTime,
                   String restTime, String numOfCycles, String numOfSets, String restBetweenSetsTime)
    {
        this.prepareTime = prepareTime;
        this.workTime = workTime;
        this.restTime = restTime;
        this.numOfCycles = numOfCycles;
        this.numOfSets = numOfSets;
        this.restBetweenSetsTime = restBetweenSetsTime;
    }

    public Workout(String prepareTime, String workTime,
                   String restTime, String numOfCycles, String numOfSets, String restBetweenSetsTime, String strings)
    {
        this.prepareTime = prepareTime;
        this.workTime = workTime;
        this.restTime = restTime;
        this.numOfCycles = numOfCycles;
        this.numOfSets = numOfSets;
        this.restBetweenSetsTime = restBetweenSetsTime;
        this.strings = strings;
    }

    public String getStrings() {
        return strings;
    }

    public void setStrings(String strings) {
        this.strings = strings;
    }

    public String getNumOfCycles() {
        return numOfCycles;
    }

    public void setNumOfCycles(String numOfCycles) {
        this.numOfCycles = numOfCycles;
    }

    public String getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(String prepareTime) {
        this.prepareTime = prepareTime;
    }

    public String getNumOfSets() {
        return numOfSets;
    }

    public void setNumOfSets(String numOfSets) {
        this.numOfSets = numOfSets;
    }

    public String getRestBetweenSetsTime() {
        return restBetweenSetsTime;
    }

    public void setRestBetweenSetsTime(String restBetweenSetsTime) {
        this.restBetweenSetsTime = restBetweenSetsTime;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    @Override
    public String toString() {
        return "אימון: " +
                "חימום = '" + prepareTime + '\'' +
                ", עבודה = '" + workTime + '\'' +
                ", מנוחה = '" + restTime + '\'' +
                ", מחזורים = '" + numOfCycles + '\'' +
                ", סטים = '" + numOfSets + '\'' +
                ", מנוחה בין סטים = '" + restBetweenSetsTime + '\'' ;
    }
}
