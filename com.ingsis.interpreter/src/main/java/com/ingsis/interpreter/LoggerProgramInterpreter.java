/*
 * My Project
 */

package com.ingsis.interpreter;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LoggerProgramInterpreter implements SafeIterator<String>, Logger {

    private final SafeIterator<String> interpreter;
    private final PrintWriter logWriter;
    private final String loggerName;

    private long stepCount = 0;
    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public LoggerProgramInterpreter(SafeIterator<String> interpreter, String logFilePath)
            throws IOException {
        this.interpreter = interpreter;
        this.loggerName =
                "LoggerProgramInterpreter[" + Integer.toHexString(interpreter.hashCode()) + "]";
        this.logWriter = new PrintWriter(new FileWriter(logFilePath, true));

        log(Level.INFO, "=== PROGRAM INTERPRETER TRACE STARTED ===");
        log(Level.INFO, "Logging every interpretation step (output or error)");
    }

    // ──────────────────────────────
    // SafeIterator<String> method
    // ──────────────────────────────
    @Override
    public SafeIterationResult<String> next() {
        SafeIterationResult<String> result = interpreter.next();

        if (result.isCorrect()) {
            String output = result.iterationResult();
            stepCount++;
            log(Level.INFO, String.format("Step [%3d]  Output: %s", stepCount, safeOutput(output)));
        } else {
            log(Level.ERROR, String.format("Interpretation failed: %s", result.error()));
            log(Level.INFO, "Interpretation halted due to error");
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
        return true; // Always log during interpretation tracing
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
    // PUBLIC + @Override log method
    // ──────────────────────────────
    @Override
    public void log(Level level, String message) {
        String timestamp = LocalDateTime.now().format(DTF);
        String line = String.format("[%s] %-7s %s%n", timestamp, level.name(), message);
        logWriter.write(line);
        logWriter.flush();
    }

    // ──────────────────────────────
    // Helper: format output safely
    // ──────────────────────────────
    private String safeOutput(String output) {
        if (output == null) return "<null>";
        String s = output.replace("\n", "⏎").replace("\r", "").replace("\t", "→");
        if (s.length() > 150) {
            s = s.substring(0, 147) + "...";
        }
        return s;
    }

    // ──────────────────────────────
    // Clean shutdown
    // ──────────────────────────────
    public void close() {
        log(Level.INFO, "=== PROGRAM INTERPRETER TRACE ENDED === Total steps: " + stepCount);
        logWriter.close();
    }
}
