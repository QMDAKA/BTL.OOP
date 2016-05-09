package com.example.quangminh.btl2.Entity;

import java.util.ArrayList;

/**
 * Created by Quang Minh on 4/15/2016.
 */
public class Subject {
    private Long id;
    private String mhp;
    private String name;
    private ArrayList<Classroom> classrooms;

    public Subject() {
        classrooms=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMhp() {
        return mhp;
    }

    public void setMhp(String mhp) {
        this.mhp = mhp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(ArrayList<Classroom> classrooms) {
        this.classrooms = classrooms;
    }
}
