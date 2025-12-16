/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.engine.services.ExecuteService;
import com.ingsis.engine.services.FormatService;
import com.ingsis.engine.services.LintService;
import com.ingsis.engine.versions.Version;
import com.ingsis.utils.evalstate.io.InputSupplier;
import com.ingsis.utils.evalstate.io.OutputEmitter;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.io.*;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
        name = "cli-engine",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "CLI wrapper around the Engine")
public class CliEngine implements Callable<Integer>, Engine {

    private final ExecuteService executeService = new ExecuteService();
    private final FormatService formatService = new FormatService();
    private final LintService lintService = new LintService();

    @Parameters(index = "0", description = "Operation: interpret, format, or analyze")
    private String operation;

    @Option(
            names = {"-i", "--input"},
            description = "Input file (defaults to STDIN)")
    private File inputFile;

    @Option(
            names = {"-c", "--config"},
            description = "Config file (optional)")
    private File configFile;

    @Option(
            names = {"-o", "--output"},
            description = "Output file (defaults to STDOUT)")
    private File outputFile;

    @Option(
            names = {"-v", "--version"},
            description = "Version")
    private String versionString = "1.0";

    @Override
    public Integer call() throws Exception {
        Version version = Version.fromString(versionString);
        try (InputStream in = inputFile != null ? new FileInputStream(inputFile) : System.in;
                InputStream config = configFile != null ? new FileInputStream(configFile) : null;
                Writer writer =
                        outputFile != null
                                ? new FileWriter(outputFile)
                                : new OutputStreamWriter(System.out)) {

            OutputEmitter emitter = s -> System.out.println(s);

            if (inputFile == null) {
                if (System.in.available() == 0) {
                    return runRepl(version, writer, emitter) ? 0 : 1;
                } else {
                    Result<String> result = executeOperation(version, in, config, writer, emitter);
                    return handleResult(result, writer) ? 0 : 1;
                }
            }

            Result<String> result;
            switch (operation.toLowerCase()) {
                case "interpret":
                    result = interpret(version, emitter, null, in);
                    break;
                case "format":
                    result = format(in, config, writer, version);
                    break;
                case "analyze":
                    result = analyze(in, config, version);
                    break;
                default:
                    System.err.println("Unknown operation: " + operation);
                    return 1;
            }
            return handleResult(result, writer) ? 0 : 1;
        }
    }

    private boolean runRepl(Version version, Writer writer, OutputEmitter emitter) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println(
                    "Entering CLI Engine REPL. Type empty line to execute and 'exit' to quit.");
            String line;
            StringBuilder buffer = new StringBuilder();
            while (true) {
                System.out.print("> ");
                line = reader.readLine();
                if (line == null || line.equalsIgnoreCase("exit")) break;
                if (line.trim().isEmpty()) {
                    if (buffer.length() > 0) {
                        Result<String> result =
                                executeOperation(
                                        version,
                                        new ByteArrayInputStream(buffer.toString().getBytes()),
                                        null,
                                        writer,
                                        emitter);
                        handleResult(result, writer);
                        buffer.setLength(0);
                    }
                    continue;
                }
                buffer.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Result<String> executeOperation(
            Version version,
            InputStream in,
            InputStream config,
            Writer writer,
            OutputEmitter emitter) {
        switch (operation.toLowerCase()) {
            case "interpret":
                return interpret(version, emitter, null, in);
            case "format":
                return format(in, config, writer, version);
            case "analyze":
                return analyze(in, config, version);
            default:
                return new IncorrectResult<>("Unknown operation: " + operation);
        }
    }

    private boolean handleResult(Result<String> result, Writer writer) {
        try {
            if (result.isCorrect()) {
                if (result.result() != null) {
                    writer.write(result.result());
                    writer.write("\n");
                    writer.flush();
                }
                return true;
            } else {
                System.err.println("Error: " + result.error());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Result<String> interpret(
            Version version, OutputEmitter emitter, InputSupplier supplier, InputStream in) {
        if (emitter == null) emitter = s -> System.out.println(s);
        return executeService.execute(version, emitter, supplier, in);
    }

    @Override
    public Result<String> format(
            InputStream inputStream, InputStream config, Writer writer, Version version) {
        return formatService.format(version, inputStream, config, writer);
    }

    @Override
    public Result<String> analyze(InputStream inputStream, InputStream config, Version version) {
        return lintService.lint(version, inputStream, config);
    }

    public static void main(String[] args) {
        int exitCode = new picocli.CommandLine(new CliEngine()).execute(args);
        System.exit(exitCode);
    }
}
