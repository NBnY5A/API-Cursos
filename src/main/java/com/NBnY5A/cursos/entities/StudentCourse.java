package com.NBnY5A.cursos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "student_course")
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @CreationTimestamp
    private LocalDateTime subscriptionDate;

    private LocalDateTime expirationDate;

    @PrePersist
    protected void setExpirationDate() {
        if (this.subscriptionDate == null) {
            this.subscriptionDate = LocalDateTime.now();
        }
        this.expirationDate = subscriptionDate.plusYears(1);
    }
}
