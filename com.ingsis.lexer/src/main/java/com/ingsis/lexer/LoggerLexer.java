/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.token.Token;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoggerLexer implements SafeIterator<Token>, Logger {

    private final SafeIterator<Token> baseLexer;
    private final PrintWriter logWriter;
    private final String loggerName;

    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private int tokenCount = 0;

    public LoggerLexer(SafeIterator<Token> baseLexer, String logFilePath) throws IOException {
        this.baseLexer = Objects.requireNonNull(baseLexer);
        this.loggerName = "LoggerLexer[" + baseLexer.hashCode() + "]";
        this.logWriter = new PrintWriter(new FileWriter(logFilePath, true));

        logHeader();
    }

    // ========================
    // SafeIterator<Token> methods
    // ========================

    @Override
    public SafeIterationResult<Token> next() {
        SafeIterationResult<Token> result = baseLexer.next();

        if (result.isCorrect()) {
            Token token = result.iterationResult();
            tokenCount++;
            logToken(tokenCount, token);
        } else {
            log(Level.INFO, "End of token stream reached (no more tokens)");
        }

        return result;
    }

    // ========================
    // System.Logger methods
    // ========================

    @Override
    public String getName() {
        return loggerName;
    }

    @Override
    public boolean isLoggable(Level level) {
        // Always log everything when using LoggerLexer (it's for debugging!)
        return true;
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        log(level, msg + (thrown != null ? " - " + thrown : ""));
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        String msg = params.length == 0 ? format : String.format(format, params);
        log(level, msg);
    }

    @Override
    public void log(Level level, String message) {
        String timestamp = LocalDateTime.now().format(DTF);
        String line = String.format("[%s] %-6s %s%n", timestamp, level.name(), message);
        logWriter.write(line);
        logWriter.flush();
    }

    // ========================
    // Token logging helper
    // ========================

    private void logToken(int index, Token token) {
        String text = token.value();
        String escaped =
                text == null
                        ? "<null>"
                        : text.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");

        log(
                Level.INFO,
                String.format("Token [%3d]  Type: %-12s  Text: %s", index, token.type(), escaped));
    }

    private void logHeader() {
        log(Level.INFO, "=== LoggerLexer STARTED ===");
        log(Level.INFO, "Logging tokens as they are consumed (pull-based lexer trace)");
        log(Level.INFO, "Logger name: " + getName());
    }

    // ========================
    // Close resources cleanly
    // ========================

    public void close() {
        log(Level.INFO, "=== LoggerLexer CLOSED === Total tokens consumed: " + tokenCount);
        logWriter.close();
    }
}
