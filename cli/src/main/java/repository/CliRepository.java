package repository;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import repositories.CodeRepositoryInterface;
import results.CorrectResult;
import results.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

@Command(name = "RepositoryCLI", mixinStandardHelpOptions = true, version = "1.0",
        description = "Asks the user to write a line of code")
public class CliRepository implements CodeRepositoryInterface, Callable<Result<String>> {
    private final BlockingQueue<Character> buffer = new LinkedBlockingQueue<>();
    private boolean running = true;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliRepository()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public boolean hasNext() {
        return !buffer.isEmpty() || running;
    }

    @Override
    public Character next() {
        try {
            return buffer.take();
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    @Override
    public Result<String> call() throws Exception {
        System.out.println("Welcome to the PrintScript CLI.\n" +
                "Write your code below. Each line will be added to the buffer.\n" +
                "Type 'exit' to close this CLI.");

        Thread inputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(line.trim())) {
                        running = false;
                        break;
                    }
                    for (char c : (line + "\n").toCharArray()) {
                        buffer.put(c);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            }
        });

        inputThread.setDaemon(true);
        inputThread.start();

        // keep CLI alive until user exits
        while (running) {
            Thread.sleep(200);
        }

        return new CorrectResult<>("CLI ended successfully.");
    }

    @Override
    public Character peek() {
        try {
            BlockingQueue<Character> tempQueue = new LinkedBlockingQueue<>(buffer);
            return tempQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
