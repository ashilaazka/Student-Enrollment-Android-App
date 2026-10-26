package com.example.midexam;


public class StudentModel {

    private String studentId;
    private String studentName;
    private boolean enrolled;

    public StudentModel(String studentId, String studentName, boolean enrolled) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.enrolled = enrolled;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isEnrolled() {
        return enrolled;
    }

    public void setEnrolled(boolean enrolled) {
        this.enrolled = enrolled;
    }
}

