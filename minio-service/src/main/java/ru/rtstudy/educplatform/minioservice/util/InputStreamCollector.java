package ru.rtstudy.educplatform.minioservice.util;

import org.apache.commons.compress.utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamCollector {

    ByteArrayOutputStream targetStream = new ByteArrayOutputStream();

    public InputStreamCollector collectInputStream(InputStream input) {
        try {
            IOUtils.copy(input, targetStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public InputStream getStream() {
        return new ByteArrayInputStream(targetStream.toByteArray());
    }
}
