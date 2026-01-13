package com.NBnY5A.cursos.services;

import com.NBnY5A.cursos.controllers.UpdateCourseRequestDTO;
import com.NBnY5A.cursos.dtos.CourseCreatedResponseDTO;
import com.NBnY5A.cursos.dtos.CourseListResponseDTO;
import com.NBnY5A.cursos.dtos.CreateCourseRequestDTO;
import com.NBnY5A.cursos.entities.Course;
import com.NBnY5A.cursos.repositories.CourseRepository;
import com.NBnY5A.cursos.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<CourseListResponseDTO> fetchAllCourses() {
        var listOfCourses = courseRepository.findAll();

        return listOfCourses.stream().map(
                course -> new CourseListResponseDTO(
                        course.getName(),
                        course.getCategory(),
                        course.getActive(),
                        course.getTeacher().getFirstName(),
                        course.getTeacher().getEmail()
                )
        ).toList();
    }

    public List<CourseListResponseDTO> fetchCoursesWithFilter(MultiValueMap<String, String> params) {
        if (params.containsKey("name")) {
            String nameOfCourse = params.getFirst("name");
            var listOfCourses = courseRepository.findCourseByName(nameOfCourse);

            if (listOfCourses.isPresent()) {
                return getCourseListResponseDTOS(listOfCourses);

            }
        } else if (params.containsKey("category")) {
            String category = params.getFirst("category");

            var listOfCourses = courseRepository.findCourseByCategory(category);

            if (listOfCourses.isPresent()) {
                return getCourseListResponseDTOS(listOfCourses);
            }


        }

        throw new RuntimeException("Erro ao tentar buscar cursos!");
    }

    @Transactional
    public void updateCourseById(String courseId, UpdateCourseRequestDTO dto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Não foi possível achar o curso pelo id informado!"));

        if (dto.name() != null) {
            course.setName(dto.name());
        }

        if (dto.category() != null) {
            course.setCategory(dto.category());
        }

        if (dto.teacherId() != null) {
            var teacher = teacherRepository.findById(dto.teacherId());

            teacher.ifPresent(course::setTeacher);
        }

        courseRepository.save(course);
    }

    public void deleteCourseById(String id) {
        courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Não foi possível achar o curso pelo id informado!"));

        courseRepository.deleteById(id);
    }

    private static @NonNull List<CourseListResponseDTO> getCourseListResponseDTOS(Optional<List<Course>> listOfCourses) {
        List<CourseListResponseDTO> responseList = new ArrayList<>();
        for (Course course : listOfCourses.get()) {
            var mappedCourse = new CourseListResponseDTO(
                    course.getName(),
                    course.getCategory(),
                    course.getActive(),
                    course.getTeacher().getFirstName(),
                    course.getTeacher().getEmail()
            );
            responseList.add(mappedCourse);
        }
        return responseList;
    }
}
