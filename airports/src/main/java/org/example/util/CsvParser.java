package org.example.util;

import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    public static List<String> parseLine(String line) {
        List<String> tokens = new ArrayList<>();
        if (line == null || line.isEmpty()) {
            return tokens;
        }

        if (line.startsWith("\"") && line.endsWith("\"")) {
            line = line.substring(1, line.length() - 1);
        }
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        int length = line.length();
        for (int i = 0; i < length; i++) {
            char c = line.charAt(i);
            if (c == '"') {

                if (inQuotes && i + 1 < length && line.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        tokens.add(sb.toString());

        return tokens;
    }
}

