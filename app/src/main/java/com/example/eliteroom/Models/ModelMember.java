package com.example.eliteroom.Models;

import java.io.Serializable;

public class ModelMember implements Serializable {

    private String memberId;
    private String courseName;
    private String courseId;

    private String email;
    private String name;
    private int attendGrade;
    private int quizGrade;
    private int projectGrade;


    public ModelMember() {    }

    public ModelMember(String memberId, String courseName, String courseId, String email, String name, int attendGrade, int quizGrade, int projectGrade) {
        this.memberId = memberId;
        this.courseName = courseName;
        this.courseId = courseId;
        this.email = email;
        this.name = name;
        this.attendGrade = attendGrade;
        this.quizGrade = quizGrade;
        this.projectGrade = projectGrade;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getAttendGrade() {
        return attendGrade;
    }

    public void setAttendGrade(int attendGrade) {
        this.attendGrade = attendGrade;
    }

    public int getQuizGrade() {
        return quizGrade;
    }

    public void setQuizGrade(int quizGrade) {
        this.quizGrade = quizGrade;
    }

    public int getProjectGrade() {
        return projectGrade;
    }

    public void setProjectGrade(int projectGrade) {
        this.projectGrade = projectGrade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
