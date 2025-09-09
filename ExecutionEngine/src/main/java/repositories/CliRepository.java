package repositories;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CliRepository implements CodeRepositoryInterface {
    private final BlockingQueue<Character> buffer = new LinkedBlockingQueue<>();
    private volatile boolean running = true;

    public CliRepository() {
        System.out.println("Welcome to the PrintScript CLI.\n"
                + "Write your code below. Each line will be added to the buffer.\n"
                + "Type 'exit' to close this CLI.");
        startInputThread();
    }

    private void startInputThread() {
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
        }, "CLI-input-thread");

        inputThread.setDaemon(true);
        inputThread.start();
    }

    @Override
    public boolean hasNext() {
        return !buffer.isEmpty() || running;
    }

    @Override
    public Character next() {
        try {
            Character c = buffer.poll(200, TimeUnit.MILLISECONDS);
            if (c == null && !running) {
                throw new NoSuchElementException();
            }
            return c;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    @Override
    public Character peek() {
        Character c = buffer.peek();
        if (c == null && !running) {
            throw new NoSuchElementException();
        }
        return c;
    }
}
