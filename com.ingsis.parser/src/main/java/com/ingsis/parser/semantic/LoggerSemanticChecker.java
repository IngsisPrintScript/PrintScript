/*
 * My Project
 */

package com.ingsis.parser.semantic;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.Interpretable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoggerSemanticChecker implements SafeIterator<Interpretable>, Logger {

    private final SafeIterator<Interpretable> delegate;
    private final PrintWriter logWriter;
    private final String loggerName;

    private long nodeCount = 0;
    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public LoggerSemanticChecker(SafeIterator<Interpretable> checker, String logFilePath)
            throws IOException {
        this.delegate = Objects.requireNonNull(checker);
        this.loggerName = "LoggerSemanticChecker[" + Integer.toHexString(checker.hashCode()) + "]";
        this.logWriter = new PrintWriter(new FileWriter(logFilePath, true));

        log(Level.INFO, "=== SEMANTIC CHECKER TRACE STARTED ===");
        log(Level.INFO, "Logging every semantically processed node");
    }

    // ──────────────────────────────
    // SafeIterator<Interpretable> method
    // ──────────────────────────────
    @Override
    public SafeIterationResult<Interpretable> next() {
        SafeIterationResult<Interpretable> result = delegate.next();

        if (result.isCorrect()) {
            Interpretable node = result.iterationResult();
            nodeCount++;
            log(
                    Level.INFO,
                    String.format(
                            "SemanticNode [%3d]  Type: %-20s  →  %s",
                            nodeCount, getNodeTypeName(node), safeToString(node)));
        } else {
            log(Level.ERROR, "Semantic error detected: " + result.error());
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
        return true; // Always log during semantic tracing
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
    // PUBLIC + @Override log method (your requirement)
    // ──────────────────────────────
    @Override
    public void log(Level level, String message) {
        String timestamp = LocalDateTime.now().format(DTF);
        String line = String.format("[%s] %-7s %s%n", timestamp, level.name(), message);
        logWriter.write(line);
        logWriter.flush();
    }

    // ──────────────────────────────
    // Helper: safe toString + node type
    // ──────────────────────────────
    private String getNodeTypeName(Interpretable node) {
        String name = node.getClass().getSimpleName();
        if (name.endsWith("Node") || name.endsWith("Expr") || name.endsWith("Decl")) {
            return name.replaceAll("Node$", "").replaceAll("Expr$", "").replaceAll("Decl$", "");
        }
        return name;
    }

    private String safeToString(Object obj) {
        if (obj == null) return "<null>";
        String s = obj.toString();
        if (s.length() > 150) {
            s = s.substring(0, 147) + "...";
        }
        return s.replace("\n", "⏎").replace("\r", "").replace("\t", "→");
    }

    // ──────────────────────────────
    // Clean shutdown
    // ──────────────────────────────
    public void close() {
        log(Level.INFO, "=== SEMANTIC CHECKER TRACE ENDED === Total nodes processed: " + nodeCount);
        logWriter.close();
    }
}
