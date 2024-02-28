package ru.rtstudy.educplatform.minioservice.api.responsebuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class S3ResponseBuilderTest {

    @Test
    void upload() {
    }

    @Test
    void uploadStream() {
    }

    @Test
    void deleteFile() {
    }

    @Test
    void download() {
    }

    @DisplayName("Checking to extract correct extension")
    @Test
    void extractContentType() {
        String fileName = "ojpw3429vfpisfvs.mp4";
        Matcher matcher = Pattern.compile("(.*)\\.(.*)").matcher(fileName);
        matcher.find();
        assertEquals("mp4", matcher.group(2));
    }

    @DisplayName("Correct sum of host and key success")
    @Test
    void createReference() {
        String key = "klsjdflkj.mp4";
        String host = "http://158.160.149.227:8082/object/";
        assertEquals("http://158.160.149.227:8082/object/klsjdflkj.mp4", host + key);
    }
}