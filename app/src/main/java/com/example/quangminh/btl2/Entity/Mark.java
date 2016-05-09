package com.example.quangminh.btl2.Entity;

/**
 * Created by Quang Minh on 4/15/2016.
 */
public class Mark {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Double diemCuoiKy;
    private Double diemGiuaKy;
    private Double diemTongKet;
    private Classroom classroom;
    private Student student;

    public Double getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(Double diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    public Double getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(Double diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }

    public Double getDiemTongKet() {
        return diemTongKet;
    }

    public void setTongDiem(Double tongDiem) {
        this.diemTongKet = tongDiem;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Mark() {
    }
}
