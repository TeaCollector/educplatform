package ru.rtstudy.educplatformsecurity.model;


import jakarta.persistence.*;
import lombok.*;
import ru.rtstudy.educplatformsecurity.model.constant.CreateUpdateTime;

@Table(name = "tasks")
@Entity(name = "Task")
@Setter
@Getter
@ToString(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @Column(name = "description")
    private String description;

    @Embedded
    private CreateUpdateTime time;

    @PreRemove
    private void preRemove() {

    }

}
