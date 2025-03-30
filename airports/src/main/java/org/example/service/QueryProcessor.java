package org.example.service;

import org.example.model.AirportRecord;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class QueryProcessor {
    private final List<AirportRecord> records;
    private final int indexedColumn;
    public QueryProcessor(List<AirportRecord> records, int indexedColumn) {
        this.records = records;
        this.indexedColumn = indexedColumn;
    }


    private String cleanField(String value) {
        if (value == null) {
            return "";
        }
        value = value.trim();

        value = value.replace("\"\"", "\"");

        if (value.startsWith("\"") && value.endsWith("\"") && value.length() > 1) {
            value = value.substring(1, value.length() - 1);
        }
        return value;
    }

    public List<String> search(String prefix) {
        final String normPrefix = prefix.trim().toLowerCase();

        List<AirportRecord> matches = records.stream()
                .filter(record -> record.getFields().length >= indexedColumn &&
                        cleanField(record.getFields()[indexedColumn - 1]).toLowerCase().startsWith(normPrefix))
                .collect(Collectors.toList());

        boolean numeric = matches.stream().allMatch(r -> {
            try {
                Double.parseDouble(cleanField(r.getFields()[indexedColumn - 1]));
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        });

        Comparator<AirportRecord> comp = numeric ?
                Comparator.comparingDouble(r -> Double.parseDouble(cleanField(r.getFields()[indexedColumn - 1])))
                : Comparator.comparing(r -> cleanField(r.getFields()[indexedColumn - 1]));

        matches.sort(comp);

        return matches.stream()
                .map(AirportRecord::getLineNumber)
                .collect(Collectors.toList());
    }
}
