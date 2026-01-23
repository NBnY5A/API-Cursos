package com.NBnY5A.cursos.repositories;

import com.NBnY5A.cursos.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
