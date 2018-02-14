package com.ppbf.helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class File {

    public static ArrayList<String> readFromFile(String filename) throws FileNotFoundException {
        FileReader reader = new FileReader(filename);
        Scanner scanner = new Scanner(reader);

        ArrayList<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        scanner.close();

        return lines;
    }
}
