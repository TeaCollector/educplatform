package ru.rtstudy.educplatform.minioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtstudy.educplatform.minioservice.model.FileMetadata;

import java.util.Optional;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, String> {

}
