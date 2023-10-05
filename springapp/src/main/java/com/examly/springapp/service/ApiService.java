package com.examly.springapp.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.Repository.CourseRepo;
import com.examly.springapp.Repository.LessonRepo;
import com.examly.springapp.model.Course;
import com.examly.springapp.model.Lesson;

@Service
public class ApiService {
    private final CourseRepo courseRepo;
    private final LessonRepo lessonRepo;

    @Autowired
    public ApiService(CourseRepo courseRepo, LessonRepo lessonRepo) {
        this.courseRepo = courseRepo;
        this.lessonRepo = lessonRepo;
    }

    public boolean createCourse(Course course) {
        courseRepo.save(course);
        return true;
    }

    public boolean createLesson(int courseId, Lesson lesson) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        lesson.setCourse(course);
        lessonRepo.save(lesson);
        return true;
    }

    public Course getCourse(int courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public Lesson getLessonWithCourseDetails(int lessonId) {
        return lessonRepo.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
    }
}
