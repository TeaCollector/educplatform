package ru.rtstudy.educplatform.minioservice.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;

import java.util.UUID;

public class Util {

    public static String createUUID(FilePart file) {
        String uuid = UUID.randomUUID().toString().replace("-", "") + ".";
        uuid = file.filename().replaceFirst(".*\\.", uuid);
        return uuid;
    }

    public static String getMediaType(HttpHeaders headers) {
        return headers.getContentType() == null ?
                MediaType.APPLICATION_OCTET_STREAM_VALUE :
                headers.getContentType().toString();
    }
}
