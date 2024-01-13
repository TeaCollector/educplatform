package ru.rtstudy.educplatformsecurity.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rtstudy.educplatformsecurity.model.constant.CreateUpdateTime;

import java.time.LocalDateTime;

@Table(name = "tasks")
@Entity(name = "Task")
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Embedded
    private CreateUpdateTime time;
}
