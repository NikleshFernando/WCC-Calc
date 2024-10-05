package org.spm.wcccalculatorspring.controller;

import org.spm.wcccalculatorspring.model.WCCProject;
import org.spm.wcccalculatorspring.repo.WCCRepository;
import org.spm.wcccalculatorspring.service.WCCFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/wcc")
//@CrossOrigin(origins = "http://localhost:3000")  // Enable CORS for React frontend
public class WccController {

    @Autowired
    private WCCFileHandler wccFileHandler;

    @Autowired
    private WCCRepository wccRepository;

    // Endpoint to analyze a project path (POST request)
    @PostMapping("/analyze")
    public WCCProject analyzeProject(@RequestParam String projectKey, @RequestParam String projectPath) {
        return wccFileHandler.analyzeProject(projectKey, projectPath);
    }

    // Endpoint to retrieve WCC value of a project
//    @GetMapping("/result")
//    public int getProjectWCC(@RequestParam String projectKey, @RequestParam String projectPath) {
//        return wccFileHandler.getWccValue(projectKey, projectPath);
//    }

    @GetMapping("/result/{projectKey}")
    public Optional<WCCProject> getProjectWCC(@PathVariable String projectKey) {
        return wccRepository.findByProjectKey(projectKey);
    }
}



