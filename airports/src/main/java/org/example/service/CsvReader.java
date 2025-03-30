package org.example.service;

import org.example.model.AirportRecord;
import org.example.util.CsvParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public static List<AirportRecord> readCsv(String filePath) {
        List<AirportRecord> records = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            int lineNumberCount = 0;
            while ((line = br.readLine()) != null) {
                lineNumberCount++;

                List<String> tokens = CsvParser.parseLine(line);
                System.out.println("Строка " + lineNumberCount + ": " + tokens);
                if (tokens.size() < 1) continue;


                String lineNumberStr = tokens.get(0).trim();
                System.out.println("Номер строки (lineNumber): " + lineNumberStr);


                String[] fields = new String[tokens.size() - 1];
                for (int i = 1; i < tokens.size(); i++) {
                    fields[i - 1] = tokens.get(i).trim();
                }

                records.add(new AirportRecord(lineNumberStr, fields));
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения CSV файла: " + e.getMessage());
            System.exit(1);
        }
        return records;
    }
}

