package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query("""
            select count(l) > 0 
            from Course c 
            join User u on c.courseAuthor.id = u.id
            join Lesson l on l.course.id = c.id
            where l.fileName = :fileName
            and u.id = :userId
            """)
    boolean hasCredential(String fileName, Long userId);
}
