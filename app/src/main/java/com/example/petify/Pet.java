package com.example.petify;

import java.io.Serializable;

public class Pet implements Serializable {

    private Long id;
    private String name;
    private String owner;
    private String about;
    private int dailyMeals;
    private int dailyWalks;
    private int mealsGiven = 0;
    private int walksDone = 0;
    private Long lastReset = System.currentTimeMillis();

    public Pet() {

    }

    public Pet(Long id, String owner, String name, int dailyMeals, int dailyWalks) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.dailyMeals = dailyMeals;
        this.dailyWalks = dailyWalks;
    }

    public Pet(Long id, String owner, String name, String about, int dailyMeals, int dailyWalks, int mealsGiven, int walksDone, Long lastReset) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.about = about;
        this.dailyMeals = dailyMeals;
        this.dailyWalks = dailyWalks;
        this.mealsGiven = mealsGiven;
        this.walksDone = walksDone;
        this.lastReset = lastReset;
    }

    public int getMealsGiven() {
        return mealsGiven;
    }

    public void setMealsGiven(int mealsGiven) {
        this.mealsGiven = mealsGiven;
    }

    public int getWalksDone() {
        return walksDone;
    }

    public void setWalksDone(int walksDone) {
        this.walksDone = walksDone;
    }

    public Long getLastReset() {
        return lastReset;
    }

    public void setLastReset(Long lastReset) {
        this.lastReset = lastReset;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getDailyMeals() {
        return dailyMeals;
    }

    public void setDailyMeals(int dailyMeals) {
        this.dailyMeals = dailyMeals;
    }

    public int getDailyWalks() {
        return dailyWalks;
    }

    public void setDailyWalks(int dailyWalks) {
        this.dailyWalks = dailyWalks;
    }

    public String getOwner() {
        return owner;
    }
}
