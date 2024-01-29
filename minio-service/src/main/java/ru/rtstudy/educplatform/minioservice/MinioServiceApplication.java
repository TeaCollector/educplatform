package ru.rtstudy.educplatform.minioservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@OpenAPIDefinition(info = @Info(title = "Minio service", version = "1.0", description = "Documentation APIs v1.0"))
public class MinioServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinioServiceApplication.class, args);
    }

}
