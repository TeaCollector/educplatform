package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Lesson;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("""
            select new LessonDtoResponse(l.id, l.title, l.fileName, l.description, l.course.id) 
            from Lesson l 
            where l.id = :id
            """)
    Optional<LessonDtoResponse> getLessonById(@Param(value = "id") Long id);


    @Modifying
    @Query("""
            update Lesson l
            set l.fileName = '********' 
            where l.fileName = :fileName
            """)
    void deleteFile(String fileName);

    @Query("""
            select count(*) > 0
            from Lesson l join Course c on l.course.id = c.id 
            join UserCourse uc on c.id = uc.course.id
            where l.id = :lessonId
            and uc.user.id = :userId
            """)
    Boolean whetherOnCourse(Long lessonId, Long userId);
}
