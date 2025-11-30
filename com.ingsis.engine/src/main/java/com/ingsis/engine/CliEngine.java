/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.engine.versions.Version;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@SuppressWarnings("FieldMayBeFinal")
@CommandLine.Command(
        name = "cli-engine",
        mixinStandardHelpOptions = true,
        description = "Runs the interpreter with CLI input")
public final class CliEngine implements Runnable {

    @Option(names = "--repl-mode", description = "Enable REPL mode")
    public boolean replMode = false;

    @Option(names = "--action", description = "Command to execute.", required = true)
    public String command;

    @Option(names = "--file", description = "Path to a PrintScript file.")
    public Path file;

    @Option(names = "--formatConfig", description = "Path to the format config file.")
    public Path formatConfig;

    @Option(names = "--version", description = "PrintScript version to use.", required = true)
    public Version version;

    private volatile boolean stopRequested = false;

    private final Engine engine = new InMemoryEngine();

    public void requestStop() {
        stopRequested = true;
    }

    public CliEngine() {}

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliEngine()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            runREPL();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void runREPL() {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            DefaultRuntime.getInstance().push();

            if (replMode) System.out.println("Welcome to PrintScript REPL! Type 'exit' to quit.");

            String line;

            while (!stopRequested) {

                if (replMode) System.out.print("> ");

                line = reader.readLine();

                if (line == null || (replMode && line.equalsIgnoreCase("exit"))) {
                    if (replMode) System.out.println("Goodbye!");
                    break;
                }

                InputStream input = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
                Result<String> result = engine.interpret(input);

                IncorrectResult<?> error = DefaultRuntime.getInstance().getExecutionError();
                if (!result.isCorrect() && error != null) {
                    System.err.println("Error: " + error.error());
                }
            }

            if (replMode) System.out.println("REPL stopped.");

        } catch (IOException e) {
            System.err.println("Error in REPL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
