package com.NBnY5A.cursos.mappers;

import com.NBnY5A.cursos.dtos.CreateCourseRequestDTO;
import com.NBnY5A.cursos.entities.Course;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CourseMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "isActive", target = "active")
    @Mapping(source = "id_professor", target = "teacher.id")
    Course convertDtoToEntity(CreateCourseRequestDTO dto);
}
