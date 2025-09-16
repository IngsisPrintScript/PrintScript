package com.ingsis.printscript.linter.utils;

import com.ingsis.printscript.linter.api.SourceRange;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SourceRangeCalculator {
    private SourceRangeCalculator() {}

    public static SourceRange findIdentifierRange(String sourceCode, String identifier) {
        if (sourceCode == null || identifier == null) {
            return null;
        }

        // Buscar el identificador como palabra completa
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(identifier) + "\\b");
        Matcher matcher = pattern.matcher(sourceCode);
        
        if (matcher.find()) {
            int startPos = matcher.start();
            int endPos = matcher.end();
            
            return calculateRangeFromPosition(sourceCode, startPos, endPos);
        }
        
        return null;
    }

    public static SourceRange findPrintlnRange(String sourceCode) {
        if (sourceCode == null) {
            return null;
        }

        // Buscar la palabra "println" seguida de paréntesis
        Pattern pattern = Pattern.compile("\\bprintln\\s*\\(");
        Matcher matcher = pattern.matcher(sourceCode);
        
        if (matcher.find()) {
            int startPos = matcher.start();
            // Buscar el paréntesis de cierre correspondiente
            int endPos = findMatchingParenthesis(sourceCode, matcher.end() - 1);
            
            if (endPos != -1) {
                return calculateRangeFromPosition(sourceCode, startPos, endPos + 1);
            }
        }
        
        return null;
    }


    private static SourceRange calculateRangeFromPosition(String sourceCode, int startPos, int endPos) {
        int startLine = 1;
        int startCol = 1;
        int endLine = 1;
        int endCol = 1;

        // Calcular línea y columna de inicio
        for (int i = 0; i < startPos && i < sourceCode.length(); i++) {
            if (sourceCode.charAt(i) == '\n') {
                startLine++;
                startCol = 1;
            } else {
                startCol++;
            }
        }

        // Calcular línea y columna de fin
        endLine = startLine;
        endCol = startCol;
        for (int i = startPos; i < endPos && i < sourceCode.length(); i++) {
            if (sourceCode.charAt(i) == '\n') {
                endLine++;
                endCol = 1;
            } else {
                endCol++;
            }
        }

        return new SourceRange(startLine, startCol, endLine, endCol);
    }

    private static int findMatchingParenthesis(String sourceCode, int openPos) {
        int depth = 1;
        for (int i = openPos + 1; i < sourceCode.length(); i++) {
            char c = sourceCode.charAt(i);
            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
}
