package org.spm.wcccalculatorspring.controller;

import org.spm.wcccalculatorspring.model.WCCProject;
import org.spm.wcccalculatorspring.service.WCCFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wcc")
@CrossOrigin(origins = "http://localhost:3000")  // Enable CORS for React frontend
public class WccController {

    @Autowired
    private WCCFileHandler wccFileHandler;

    // Endpoint to analyze a project path (GET request)
    @PostMapping("/analyze")
    public WCCProject analyzeProject(@RequestParam String projectKey, @RequestParam String projectPath) {
        return wccFileHandler.analyzeProject(projectKey, projectPath);
    }

    // Endpoint to retrieve all project analysis results
    @GetMapping("/result/")
    public int getProjectData(@RequestParam String projectKey, @RequestParam String projectPath){
        return wccFileHandler.getWccValue(projectKey, projectPath);
    }
}


