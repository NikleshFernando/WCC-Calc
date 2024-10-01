package org.spm.wcccalculatorspring.service;

import org.spm.wcccalculatorspring.model.WCCProject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
public class WCCFileHandler {

    WCCProject project = new WCCProject();
    public WCCProject analyzeProject(String projectKey, String projectPath) {

        project.setProjectKey(projectKey);
        project.setSourcePath(projectPath);

        File dir = new File(projectPath);
        readFiles(dir, project);

        return project;
    }

    // Traverse directories and find all Java files
    private void readFiles(File directory, WCCProject project) {
        File[] files = directory.listFiles();
        if (Objects.nonNull(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    readFiles(file, project); // Recursively handle subdirectories
                } else if (file.getName().endsWith(".java")) {
                    int wccValue = WCCCalculator.calculateWCC(file);
                    project.addFileWCC(file.getName(), wccValue);
                    project.setWccValue(wccValue);
                }
            }
        }
    }
    public int  getWccValue(String projectKey, String projectPath) {
        project.setProjectKey(projectKey);
        project.setSourcePath(projectPath);

        File dir = new File(projectPath);
        readFiles(dir, project);

        return project.getWccValue();
    }
}



