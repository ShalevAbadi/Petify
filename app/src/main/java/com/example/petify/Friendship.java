package com.example.petify;

public class Friendship {
    private Friend friend1;
    private Friend friend2;

    public Friendship(){

    }

    public Friendship(Friend friend1, Friend friend2) {
        this.friend1 = friend1;
        this.friend2 = friend2;
    }

    public boolean isFriend1(String id) {
        return friend1.getId().trim().equals(id);
    }

    public boolean isFriend2(String id) {
        return friend2.getId().trim().equals(id);
    }

    public Friend getFriend1(){
        return friend1;
    }

    public Friend getFriend2(){
        return friend2;
    }

}
