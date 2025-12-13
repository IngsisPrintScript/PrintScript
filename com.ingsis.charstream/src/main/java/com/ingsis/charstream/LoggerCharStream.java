/*
 * My Project
 */

package com.ingsis.charstream;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.metachar.MetaChar;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoggerCharStream implements SafeIterator<MetaChar>, Logger {

    private final SafeIterator<MetaChar> delegate;
    private final PrintWriter logWriter;
    private final String loggerName;
    private final IterationResultFactory iterationResultFactory;

    private long charCount = 0;
    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public LoggerCharStream(
            SafeIterator<MetaChar> charStream,
            String logFilePath,
            IterationResultFactory iterationResultFactory)
            throws IOException {
        this.delegate = Objects.requireNonNull(charStream);
        this.loggerName = "LoggerCharStream[" + Integer.toHexString(charStream.hashCode()) + "]";
        this.logWriter = new PrintWriter(new FileWriter(logFilePath, true));
        this.iterationResultFactory = iterationResultFactory;
    }

    private LoggerCharStream(
            SafeIterator<MetaChar> charStream,
            PrintWriter logWriter,
            String loggerName,
            IterationResultFactory iterationResultFactory) {
        this.delegate = charStream;
        this.logWriter = logWriter;
        this.loggerName = loggerName;
        this.iterationResultFactory = iterationResultFactory;
    }

    // ──────────────────────────────
    // SafeIterator<MetaChar> method
    // ──────────────────────────────
    @Override
    public SafeIterationResult<MetaChar> next() {
        SafeIterationResult<MetaChar> result = delegate.next();

        if (result.isCorrect()) {
            MetaChar mc = result.iterationResult();
            charCount++;
            logCharacter(charCount, mc);
            return iterationResultFactory.createCorrectResult(
                    result.iterationResult(),
                    new LoggerCharStream(
                            result.nextIterator(), logWriter, loggerName, iterationResultFactory));
        }

        log(Level.INFO, "End of input stream reached (no more characters)");
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
        return true; // Always log when using LoggerCharStream — it's for tracing!
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        log(level, (thrown != null ? msg + " :: " + thrown : msg));
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        String msg = params.length == 0 ? format : String.format(format, params);
        log(level, msg);
    }

    // ──────────────────────────────
    // Internal logging to file with timestamp
    // ──────────────────────────────
    @Override
    public void log(Level level, String message) {
        String timestamp = LocalDateTime.now().format(DTF);
        String line = String.format("[%s] %-6s %s%n", timestamp, level.name(), message);
        logWriter.write(line);
        logWriter.flush();
    }

    // ──────────────────────────────
    // Pretty-print each MetaChar
    // ──────────────────────────────
    private void logCharacter(long index, MetaChar mc) {
        char c = mc.character();
        String repr;

        if (c == '\n') repr = "\\n (newline)";
        else if (c == '\r') repr = "\\r (carriage return)";
        else if (c == '\t') repr = "\\t (tab)";
        else if (c == ' ') repr = "' ' (space)";
        else if (c == 0) repr = "<NUL>";
        else if (c == 65535 || c == Character.MIN_VALUE) repr = "<EOF>";
        else if (Character.isISOControl(c)) repr = String.format("<%04X> (control)", (int) c);
        else repr = String.format("'%c'", c);

        log(
                Level.INFO,
                String.format(
                        "Char [%5d]  Pos: (line=%3d, col=%2d)  Codepoint: U+%04X  →  %s",
                        index, mc.line(), mc.column(), (int) c, repr));
    }

    // ──────────────────────────────
    // Clean shutdown
    // ──────────────────────────────
    public void close() {
        log(Level.INFO, "=== LoggerCharStream CLOSED === Total characters consumed: " + charCount);
        logWriter.close();
    }
}
