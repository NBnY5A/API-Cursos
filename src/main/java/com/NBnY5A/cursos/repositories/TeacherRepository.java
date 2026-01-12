package com.NBnY5A.cursos.repositories;

import com.NBnY5A.cursos.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
