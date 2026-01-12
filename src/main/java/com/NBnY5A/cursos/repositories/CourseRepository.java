package com.NBnY5A.cursos.repositories;

import com.NBnY5A.cursos.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
