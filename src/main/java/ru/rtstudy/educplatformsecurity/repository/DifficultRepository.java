package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.model.Difficult;
import ru.rtstudy.educplatformsecurity.model.constant.DifficultLevel;

public interface DifficultRepository extends JpaRepository<Difficult, Byte> {

    @Query("""
            select d from Difficult d where d.difficultLevel = :difficult
            """)
    Difficult getDifficultByDifficultName(DifficultLevel difficult);
}
