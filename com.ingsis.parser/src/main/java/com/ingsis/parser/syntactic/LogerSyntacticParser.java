/*
 * My Project
 */

package com.ingsis.parser.syntactic;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.Checkable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogerSyntacticParser implements SafeIterator<Checkable>, Logger {

    private final SafeIterator<Checkable> delegate;
    private final PrintWriter logWriter;
    private final String loggerName;

    private long nodeCount = 0;
    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public LogerSyntacticParser(SafeIterator<Checkable> parser, String logFilePath)
            throws IOException {
        this.delegate = Objects.requireNonNull(parser);
        this.loggerName = "LogerSyntacticParser[" + Integer.toHexString(parser.hashCode()) + "]";
        this.logWriter = new PrintWriter(new FileWriter(logFilePath, true));

        log(Level.INFO, "=== SYNTACTIC PARSER TRACE STARTED ===");
        log(Level.INFO, "Logging every parsed node as it is produced");
    }

    // ──────────────────────────────
    // SafeIterator<Checkable> method
    // ──────────────────────────────
    @Override
    public SafeIterationResult<Checkable> next() {
        SafeIterationResult<Checkable> result = delegate.next();

        if (result.isCorrect()) {
            Checkable node = result.iterationResult();
            nodeCount++;
            logParsedNode(nodeCount, node);
        } else {
            log(Level.WARNING, "Parsing failed with error: " + result.error());
        }

        return result;
    }

    // ──────────────────────────────
    // System.Logger required methods
    // ──────────────────────────────
    @Override
    public String getName() {
        return loggerName;
    }

    @Override
    public boolean isLoggable(Level level) {
        return true; // Always log during tracing
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        log(level, thrown != null ? msg + " → " + thrown : msg);
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        String msg = params.length == 0 ? format : String.format(format, params);
        log(level, msg);
    }

    // ──────────────────────────────
    // Internal file logger with timestamp
    // ──────────────────────────────
    @Override
    public void log(Level level, String message) {
        String timestamp = LocalDateTime.now().format(DTF);
        String line = String.format("[%s] %-7s %s%n", timestamp, level.name(), message);
        logWriter.write(line);
        logWriter.flush();
    }

    // ──────────────────────────────
    // Pretty print each Checkable node
    // ──────────────────────────────
    private void logParsedNode(long index, Checkable node) {
        String typeName = node.getClass().getSimpleName();
        String ruleName = getRuleName(node); // Try to extract meaningful name

        String details = node.toString();
        if (details.length() > 120) {
            details = details.substring(0, 117) + "...";
        }

        log(
                Level.INFO,
                String.format(
                        "Node [%3d]  %-20s  →  %s  |  %s", index, ruleName, typeName, details));
    }

    // Try to extract a human-readable rule name from the node
    private String getRuleName(Checkable node) {
        // Common patterns — adjust to your actual AST node classes
        String className = node.getClass().getSimpleName();

        if (className.endsWith("Node")
                || className.endsWith("Expr")
                || className.endsWith("Stmt")) {
            return className
                    .replaceAll("Node$", "")
                    .replaceAll("Expr$", "")
                    .replaceAll("Stmt$", "");
        }
        if (className.contains("Binary") || className.contains("Unary")) {
            return className;
        }
        return className;
    }

    // ──────────────────────────────
    // Clean shutdown
    // ──────────────────────────────
    public void close() {
        log(Level.INFO, "=== SYNTACTIC PARSER TRACE ENDED === Total nodes parsed: " + nodeCount);
        logWriter.close();
    }
}
