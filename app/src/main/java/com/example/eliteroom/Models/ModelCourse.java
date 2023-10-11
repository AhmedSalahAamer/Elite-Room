package com.example.eliteroom.Models;

import java.io.Serializable;

public class ModelCourse implements Serializable {

    private String quizGrade;
    private String attendanceGrade;
    private String courseId;
    private String courseName;
    private String doctorId;
    private String projectGrade;

    public ModelCourse() {
    }

    public ModelCourse(String quizGrade, String attendanceGrade, String courseId, String courseName, String doctorId, String projectGrade) {
        this.quizGrade = quizGrade;
        this.attendanceGrade = attendanceGrade;
        this.courseId = courseId;
        this.courseName = courseName;
        this.doctorId = doctorId;
        this.projectGrade = projectGrade;
    }


    public String getQuizGrade() {
        return quizGrade;
    }

    public void setQuizGrade(String quizGrade) {
        this.quizGrade = quizGrade;
    }

    public String getAttendanceGrade() {
        return attendanceGrade;
    }

    public void setAttendanceGrade(String attendanceGrade) {
        this.attendanceGrade = attendanceGrade;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getProjectGrade() {
        return projectGrade;
    }

    public void setProjectGrade(String projectGrade) {
        this.projectGrade = projectGrade;
    }
}
