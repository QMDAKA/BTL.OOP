package com.example.quangminh.btl2.Entity;

import java.util.ArrayList;

/**
 * Created by Quang Minh on 4/15/2016.
 */
public class Classroom {
    private Long id;
    private String mslh;
    private String timeBegin;
    private String timeEnd;
    private  String address;

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    private  String day;

    private Teacher teacher;
    private Subject subject;
    private ArrayList<Mark> marks;
    private ArrayList<Student> students;
    private ArrayList<StudentAttendance> studentAttendances;

    public Classroom() {
        marks=new ArrayList<>();
        studentAttendances=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMslh() {
        return mslh;
    }

    public void setMslh(String mslh) {
        this.mslh = mslh;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<Mark> marks) {
        this.marks = marks;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<StudentAttendance> getStudentAttendances() {
        return studentAttendances;
    }

    public void setStudentAttendances(ArrayList<StudentAttendance> studentAttendances) {
        this.studentAttendances = studentAttendances;
    }
}
