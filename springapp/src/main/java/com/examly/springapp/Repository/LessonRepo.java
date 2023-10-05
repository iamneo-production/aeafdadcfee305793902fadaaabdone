package com.examly.springapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Lesson;

@Repository
public interface LessonRepo extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByCourse_CourseId(int courseId);
}