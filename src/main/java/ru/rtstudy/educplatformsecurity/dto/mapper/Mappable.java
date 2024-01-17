package ru.rtstudy.educplatformsecurity.dto.mapper;

import java.util.List;

public interface Mappable<E, D> {

    E toEntity(D dto);

    D toDto(E entity);
}
