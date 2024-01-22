package ru.rtstudy.educplatform.minioservice.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@Table(name = "file_metadata")
@AllArgsConstructor
public class FileMetadata {

    @Id
    private String id;

    @Column(name = "size")
    private long size;

    @Column(name = "content_type")
    private String contentType;
}