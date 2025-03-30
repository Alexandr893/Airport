package org.example.model;

public class AirportRecord {
    private String lineNumber;
    private String[] fields;

    public AirportRecord(String lineNumber, String[] fields) {
        this.lineNumber = lineNumber;
        this.fields = fields;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String[] getFields() {
        return fields;
    }
}
