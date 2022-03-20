package com.geekbrains.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class AbstractTest {

    protected String getResourceAsString(String resource) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(resource);
        Scanner in = new Scanner(inputStream);
        StringBuilder s = new StringBuilder();
        while (in.hasNextLine()) {
            s.append(in.nextLine());
        }
        return s.toString();
    }

    protected File getFile(String resource) throws IOException {
        return new File(getClass().getResource(resource).getFile());
    }

}
