package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtstudy.educplatformsecurity.model.UserCourse;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {


}
