package com.example.quangminh.btl2.Entity;

import java.util.ArrayList;

/**
 * Created by Quang Minh on 4/15/2016.
 */
public class Student {
    private Long id;
    private int mssv;
    private String name;
    private String grade;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    private ArrayList<Classroom> classrooms;
    private ArrayList<Mark> marks;

    public Student() {
        classrooms=new ArrayList<>();
        marks=new ArrayList<>();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMssv() {
        return mssv;
    }

    public void setMssv(int mssv) {
        this.mssv = mssv;
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

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<Mark> marks) {
        this.marks = marks;
    }
}
