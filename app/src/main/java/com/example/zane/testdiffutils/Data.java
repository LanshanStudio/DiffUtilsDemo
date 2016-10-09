package com.example.zane.testdiffutils;

/**
 * Created by Zane on 16/10/8.
 * Email: zanebot96@gmail.com
 */

public class Data {

    private String name;
    private int age;

    public Data(String name, int age){
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
