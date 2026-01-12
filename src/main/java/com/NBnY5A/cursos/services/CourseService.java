package com.NBnY5A.cursos.services;

import com.NBnY5A.cursos.dtos.CourseCreatedResponseDTO;
import com.NBnY5A.cursos.dtos.CreateCourseRequestDTO;
import com.NBnY5A.cursos.repositories.CourseRepository;
import com.NBnY5A.cursos.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public CourseCreatedResponseDTO create(CreateCourseRequestDTO dto) {
        var teacher = teacherRepository.findById(dto.id_professor());

        if (teacher.isPresent()) {
            var course = dto.toEntity(teacher.get());

            var responseDTO = this.courseRepository.save(course);

            return new CourseCreatedResponseDTO("Curso criado com sucesso!", responseDTO.getId());
        }

        throw new RuntimeException("Erro ao tentar encontrar professor!");
    }
}
