package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.model.Difficult;
import ru.rtstudy.educplatformsecurity.model.constant.DifficultLevel;
import ru.rtstudy.educplatformsecurity.repository.DifficultRepository;
import ru.rtstudy.educplatformsecurity.service.DifficultService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DifficultServiceImpl implements DifficultService {

    private final DifficultRepository difficultRepository;

    @Override
    public Optional<Difficult> findById(Long id) {
        return difficultRepository.findById(id);
    }

    @Override
    public Difficult getDifficultByDifficultName(DifficultLevel difficultLevel) {
        return difficultRepository.getDifficultByDifficultName(difficultLevel);
    }
}
