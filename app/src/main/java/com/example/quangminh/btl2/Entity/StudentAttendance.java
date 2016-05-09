package com.example.quangminh.btl2.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Quang Minh on 4/15/2016.
 */
public class StudentAttendance {
    private Long id;
    private Date date;
    private Classroom classroom;
    private ArrayList<Student> students;

    public StudentAttendance() {
        students=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
