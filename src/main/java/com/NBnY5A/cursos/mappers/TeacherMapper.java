package com.NBnY5A.cursos.mappers;

import com.NBnY5A.cursos.dtos.request.CreateTeacherRequestDTO;
import com.NBnY5A.cursos.dtos.TeacherCourseInfoDTO;
import com.NBnY5A.cursos.dtos.response.TeacherListResponseDTO;
import com.NBnY5A.cursos.entities.Course;
import com.NBnY5A.cursos.entities.Teacher;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TeacherMapper {

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    Teacher convertDtoToEntity(CreateTeacherRequestDTO dto);

    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "course", target = "courseList")
    TeacherListResponseDTO toListDto(Teacher teacher);

    @Mapping(source = "name", target = "courseName")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "active", target = "isActive")
    TeacherCourseInfoDTO toCourseInfoDto(Course course);
}
