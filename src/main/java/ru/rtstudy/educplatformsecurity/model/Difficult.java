package ru.rtstudy.educplatformsecurity.model;

import jakarta.persistence.*;
import liquibase.change.DatabaseChangeNote;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rtstudy.educplatformsecurity.model.constant.DifficultLevel;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity(name = "Difficult")
@Table(name = "difficult")
public class Difficult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "difficult")
    private DifficultLevel difficult;

    @OneToMany(mappedBy = "difficult")
    private Set<Course> course = new HashSet<>();
}
