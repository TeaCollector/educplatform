package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtstudy.educplatformsecurity.model.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
