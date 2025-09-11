package com.ingsis.printscript.linter.api;

public record Violation(String ruleId, String message, SourceRange range) {}
