package ru.rtstudy.educplatformsecurity.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rtstudy.educplatformsecurity.model.constant.CreateUpdateTime;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Category")
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "category")
    private Set<Course> courses = new HashSet<>();

    @Embedded
    private CreateUpdateTime time;
}
