package repository;

import repositories.CodeRepositoryInterface;

import java.util.Scanner;

public class CliRepository implements CodeRepositoryInterface {
    private final Scanner scanner = new Scanner(System.in);
    private String nextCode;
    private boolean eofReached = false;

    @Override
    public Boolean hasMoreCode() {
        if (nextCode == null && !eofReached) {
            System.out.print(">>> ");
            if (scanner.hasNextLine()) {
                nextCode = scanner.nextLine();
            } else {
                eofReached = true;
            }
        }
        return nextCode != null;
    }

    @Override
    public String nextChunkOfCode() {
        String code = nextCode;
        nextCode = null;
        return code;
    }
}

