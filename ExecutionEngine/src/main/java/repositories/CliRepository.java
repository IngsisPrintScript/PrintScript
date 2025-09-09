package repositories;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import results.CorrectResult;
import results.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Command(name = "RepositoryCLI", mixinStandardHelpOptions = true, version = "1.0",
        description = "Asks the user to write a line of code")
public class CliRepository implements CodeRepositoryInterface, Callable<Result<String>> {
    private final BlockingQueue<Character> buffer = new LinkedBlockingQueue<>();
    private boolean running = true;

    @Override
    public boolean hasNext() {
        StringBuilder builder = new StringBuilder();
        for (Character character : buffer) {
            builder.append(character);
        }
        String result = builder.toString();
        return !result.equals("exit");
    }

    @Override
    public Character next() {
        try {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return buffer.poll(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
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
                    for (char c : (line).toCharArray()) {
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

        return new CorrectResult<>("CLI ended successfully.");
    }

    @Override
    public Character peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return buffer.peek();
    }
}
