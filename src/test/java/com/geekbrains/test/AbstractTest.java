package com.geekbrains.test;

import java.io.IOException;
import java.io.InputStream;

public class AbstractTest {

    protected String getResourceAsString(String resource) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(resource);
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes);
    }

}
