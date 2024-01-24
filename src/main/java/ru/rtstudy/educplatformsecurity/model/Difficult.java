package ru.rtstudy.educplatformsecurity.model;

import jakarta.persistence.*;
import lombok.*;
import ru.rtstudy.educplatformsecurity.model.constant.DifficultLevel;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Difficult")
@Table(name = "difficult")
public class Difficult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @Enumerated(value = EnumType.STRING)
    @Column(name = "difficult")
    private DifficultLevel difficultLevel;

    @OneToMany(mappedBy = "difficult")
    private Set<Course> course = new HashSet<>();
}
