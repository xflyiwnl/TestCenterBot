package me.xflyiwnl.testcenter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AuthUtil {

    private static File file;

    public static void setupFile() {
        file = new File("auth-key.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(file.getAbsolutePath());
    }

    public static String getKey() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));;
            String line;
            while ((line = reader.readLine()) != null) {
                return line;
            }
            reader.close();
            return "";
        } catch (Exception exception) {
            return "";
        }
    }

}
