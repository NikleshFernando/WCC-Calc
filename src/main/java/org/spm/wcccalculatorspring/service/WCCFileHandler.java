package org.spm.wcccalculatorspring.service;

import org.spm.wcccalculatorspring.model.WCCProject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service  
public class WCCFileHandler {

    public WCCProject analyzeProject(String projectKey, String projectPath) {
        WCCProject project = new WCCProject();
        project.setProjectKey(projectKey);
        project.setSourcePath(projectPath);

        File dir = new File(projectPath);
        readFiles(dir, project);  // Traverse directory and calculate WCC for each file

        return project;  // Return the complete project analysis
    }

    // Traverse directories and find all Java files, calculating their WCC values
    private void readFiles(File directory, WCCProject project) {
        File[] files = directory.listFiles();
        if (Objects.nonNull(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    readFiles(file, project);  // Recursively handle subdirectories
                } else if (file.getName().endsWith(".java")) {
                    int wccValue = WCCCalculator.calculateWCC(file);  // Calculate WCC for the Java file
                    project.addFileWCC(file.getName(), wccValue);  // Add individual file WCC to project
                    project.setWccValue(project.getWccValue() + wccValue);  // Accumulate total WCC for the project
                }
            }
        }
    }

    // Method to retrieve WCC value for a project by analyzing its path
    public int getWccValue(String projectKey, String projectPath) {
        WCCProject project = analyzeProject(projectKey, projectPath);  // Reuse the analyzeProject method
        return project.getWccValue();  // Return the accumulated WCC value for the project
    }
}



