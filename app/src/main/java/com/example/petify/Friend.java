package com.example.petify;

public class Friend {
    String id;
    String name;
    String phone;

    public Friend(){

    }

    public Friend(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Friend(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
