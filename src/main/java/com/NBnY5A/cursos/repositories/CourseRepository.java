package com.NBnY5A.cursos.repositories;

import com.NBnY5A.cursos.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<List<Course>> findCourseByName(String name);
    Optional<List<Course>> findCourseByCategory(String category);
}
