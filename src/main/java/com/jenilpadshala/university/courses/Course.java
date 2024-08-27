package com.jenilpadshala.university.courses;

public class Course {
    private String courseId;
    private String courseTitle;
    private String description;

    public Course() {
    }

    public Course(String courseId, String courseTitle, String description) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.description = description;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
