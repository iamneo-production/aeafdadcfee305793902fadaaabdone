package com.examly.springapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
}
