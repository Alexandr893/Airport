package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.AirportRecord;
import org.example.model.OutputResult;
import org.example.model.SearchResult;
import org.example.service.CsvReader;
import org.example.service.QueryProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;



public class Main {

    private static final int INDEXED_COLUMN_ID = 2;


    private static final String DATA_FILE = "src/main/resources/home/test/airports.csv";
    private static final String INPUT_FILE = "src/temp/input1.txt";
    private static final String OUTPUT_FILE = "src/temp/result1.json";

    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();


            List<AirportRecord> records = CsvReader.readCsv(DATA_FILE);
            List<String> prefixes = Files.readAllLines(Paths.get(INPUT_FILE));

            long initTime = System.currentTimeMillis() - startTime;
            QueryProcessor processor = new QueryProcessor(records, INDEXED_COLUMN_ID);


            OutputResult outputResult = new OutputResult();
            outputResult.setInitTime(initTime);
            for (String prefix : prefixes) {
                prefix = prefix.trim();
                if (prefix.isEmpty()) {
                    continue;
                }
                long searchStart = System.currentTimeMillis();
                List<String> matchingLines = processor.search(prefix);
                long searchTime = System.currentTimeMillis() - searchStart;
                outputResult.getResult().add(new SearchResult(prefix, matchingLines, searchTime));
            }


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(outputResult);


            Files.write(Paths.get(OUTPUT_FILE), jsonOutput.getBytes());
            System.out.println("Результат записан в файл " + OUTPUT_FILE);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }
}