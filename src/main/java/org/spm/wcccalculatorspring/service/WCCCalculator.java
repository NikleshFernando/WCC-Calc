package org.spm.wcccalculatorspring.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WCCCalculator {
    public static int calculateWCC(File javaFile) {
        try {
            List<String> methods = findMethods(javaFile);
            List<String> variables = findVariables(javaFile);

            int methodComplexity = methods.size() * 5;  // Example: 5 points per method
            int variableComplexity = variables.size() * 2;  // Example: 2 points per variable
            int cyclomaticComplexity = methods.size();

            return methodComplexity + variableComplexity + cyclomaticComplexity;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static List<String> findMethods(File file) throws IOException {
        return Files.lines(file.toPath())
                .filter(line -> line.trim().startsWith("public") || line.trim().startsWith("private") || line.trim().startsWith("protected"))
                .collect(Collectors.toList());
    }

    private static List<String> findVariables(File file) throws IOException {
        return Files.lines(file.toPath())
                .filter(line -> line.trim().contains("int") || line.trim().contains("String") || line.trim().contains("boolean"))
                .collect(Collectors.toList());
    }

}
