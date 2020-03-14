package com.example.petify;

public class Pet {
    private int id;
    private String name;
    private String about;
    private int dailyMeals;
    private int dailyWalks;

    public Pet(int id, String name, String about, int dailyMeals, int dailyWalks) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.dailyMeals = dailyMeals;
        this.dailyWalks = dailyWalks;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

}
