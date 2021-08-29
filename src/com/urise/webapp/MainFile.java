package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {

    private static void walkFiles(File file, int offset) {
        File[] fileList = Objects.requireNonNull(file.listFiles());
        for (File fl : fileList) {
            if (fl.isDirectory()) {
                //System.out.println("DIR  - " + "....".repeat(offset) + fl.getName());
                System.out.println("DIR  - " + "...." + fl.getName());
                walkFiles(fl, ++offset);
                --offset;
            } else {
                //System.out.println("FILE - " + "....".repeat(offset) + fl.getName());
                System.out.println("FILE - " + "...." + fl.getName());
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("./src/com/urise/webapp");
        walkFiles(file, 0);

        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
