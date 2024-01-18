package ru.rtstudy.educplatformsecurity.model;

import jakarta.persistence.*;
import lombok.*;
import ru.rtstudy.educplatformsecurity.model.constant.CreateUpdateTime;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Entity(name = "Category")
@Table(name = "categories")
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();

    @Embedded
    private CreateUpdateTime time;
}
