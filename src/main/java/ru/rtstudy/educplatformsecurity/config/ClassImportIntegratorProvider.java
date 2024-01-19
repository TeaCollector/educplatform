package ru.rtstudy.educplatformsecurity.config;

import io.hypersistence.utils.hibernate.type.util.ClassImportIntegrator;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import ru.rtstudy.educplatformsecurity.dto.response.*;

import java.util.List;

public class ClassImportIntegratorProvider implements IntegratorProvider {

    @Override
    public List<Integrator> getIntegrators() {
        return List.of(
                new ClassImportIntegrator(
                        List.of(CourseShortDescriptionDto.class,
                                CourseLongDescriptionDto.class,
                                LessonDto.class,
                                TaskDto.class,
                                AllStudentAnswers.class)
                )
        );
    }
}
