package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Course;
import com.examly.springapp.model.Lesson;
import com.examly.springapp.service.ApiService;

@RestController
@RequestMapping("/course")
public class ApiController {
    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/")
    public boolean createCourse(@RequestBody Course course) {
        return apiService.createCourse(course);
    }

    @PostMapping("/{courseId}/lesson")
    public boolean createLesson(@PathVariable int courseId, @RequestBody Lesson lesson) {
        return apiService.createLesson(courseId, lesson);
    }

    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable int courseId) {
        return apiService.getCourse(courseId);
    }

    @GetMapping("/")
    public List<Course> getAllCourses() {
        return apiService.getAllCourses();
    }

    @GetMapping("/lesson/{lessonId}")
    public Lesson getLessonWithCourseDetails(@PathVariable int lessonId) {
        return apiService.getLessonWithCourseDetails(lessonId);
    }
}
