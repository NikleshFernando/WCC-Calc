package org.spm.wcccalculatorspring.model;

import java.util.HashMap;
import java.util.Map;

public class WCCProject {

    private String projectKey;
    private String sourcePath;
    private Map<String, Integer> fileWCCMap = new HashMap<>();
    private int wccValue;

    // Getter and Setter for projectKey
    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    // Getter and Setter for sourcePath
    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    // Method to add a file's WCC value to the map
    public void addFileWCC(String fileName, int wcc) {
        this.fileWCCMap.put(fileName, wcc);
    }

    // Getter for fileWCCMap
    public Map<String, Integer> getFileWCCMap() {
        return fileWCCMap;
    }

    public int getWccValue() {
        return wccValue;
    }
    public void setWccValue(int wccValue) {
        this.wccValue = wccValue;
    }
}

