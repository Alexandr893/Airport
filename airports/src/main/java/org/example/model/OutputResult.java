package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class OutputResult {
    private long initTime;
    private List<SearchResult> result = new ArrayList<>();

    public long getInitTime() {
        return initTime;
    }

    public void setInitTime(long initTime) {
        this.initTime = initTime;
    }

    public List<SearchResult> getResult() {
        return result;
    }
}
