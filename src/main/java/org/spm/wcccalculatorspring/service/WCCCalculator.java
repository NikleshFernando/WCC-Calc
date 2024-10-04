package org.spm.wcccalculatorspring.service;


import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WCCCalculator {

    private static final Pattern STRING_LITERAL = Pattern.compile("\".*?\"");
    private static final Pattern NUMERIC_VALUE = Pattern.compile("\\b\\d+\\b");
    private static final Pattern KEYWORDS = Pattern.compile("\\b(abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b");
    private static final Pattern OPERATORS = Pattern.compile("[+\\-*/%=&|<>!^~]+|\\.{1}");
    private static final Pattern DELIMITERS = Pattern.compile("[;,]");
    private static final Pattern METHOD_CALL = Pattern.compile("\\b\\w+\\(.*?\\)");

    // Main method to calculate WCC for a given Java file
    public static int calculateWCC(File javaFile) {
        try {
            int totalWCC = 0;
            List<String> lines = Files.readAllLines(javaFile.toPath());
            int nestingLevel = 0;

            for (String line : lines) {
                line = line.trim();
                if (!line.isEmpty()) {
                    int size = calculateSize(line);  // Calculate size based on tokens (S)
                    int wc = calculateControlStructureWeight(line);  // Calculate control structure weight (Wc)
                    int wn = calculateNestingWeight(line, nestingLevel);  // Calculate nesting weight (Wn)
                    int wi = calculateInheritanceWeight(line);  // Calculate inheritance weight (Wi)

                    int wcc = size * (wc + wn + wi);  // Calculate WCC for this line
                    totalWCC += wcc;

                    // Update nesting level based on the current line
                    nestingLevel = updateNestingLevel(line, nestingLevel);
                }
            }

            return totalWCC;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Calculate the size (S) of the statement based on tokens
    private static int calculateSize(String line) {
        int size = tokenCounter(STRING_LITERAL, line, 1) +
                tokenCounter(NUMERIC_VALUE, line, 1) +
                tokenCounter(KEYWORDS, line, 1) +
                tokenCounter(OPERATORS, line, 1) +
                tokenCounter(DELIMITERS, line, 1);
        return size;
    }

    // Calculate control structure weight (Wc)
    private static int calculateControlStructureWeight(String line) {
        if (line.contains("if") || line.contains("else")) return 1;
        if (line.contains("for") || line.contains("while") || line.contains("do")) return 2;
        if (line.contains("switch")) {
            // Calculate switch cases weight (assuming multiple cases inside a switch)
            return (int) line.chars().filter(ch -> ch == ':').count();  // Counting the colon (:) in cases
        }
        return 0;  // Sequential statement
    }

    // Calculate the nesting level weight (Wn)
    private static int calculateNestingWeight(String line, int currentNestingLevel) {
        return currentNestingLevel;
    }

    // Update the current nesting level based on the structure of the line
    private static int updateNestingLevel(String line, int currentLevel) {
        if (line.contains("{")) return currentLevel + 1;
        if (line.contains("}")) return currentLevel - 1;
        return currentLevel;
    }

    // Calculate inheritance level weight (Wi)
    private static int calculateInheritanceWeight(String line) {
        // Assuming no inheritance in this example, customize if needed
        return 0;
    }

    // Helper function to count tokens based on a given pattern
    private static int tokenCounter(Pattern pattern, String line, int weight) {
        int count = 0;
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            count += weight;
        }
        return count;
    }

    // Example method to find methods in the file (expand as needed)
    private static List<String> findMethods(File file) throws IOException {
        return Files.lines(file.toPath())
                .filter(line -> line.trim().startsWith("public") || line.trim().startsWith("private") || line.trim().startsWith("protected"))
                .collect(Collectors.toList());
    }

    // Example method to find variables in the file (expand as needed)
    private static List<String> findVariables(File file) throws IOException {
        return Files.lines(file.toPath())
                .filter(line -> line.trim().contains("int") || line.trim().contains("String") || line.trim().contains("boolean"))
                .collect(Collectors.toList());
    }
}
