package app;


import common.ExecutionEngineInterface;
import factories.DefaultEngineFactory;
import repository.CliRepository;
import results.Result;

public class CliApp {
    public static void main(String[] args) {
        ExecutionEngineInterface engine = new DefaultEngineFactory().getInterpreterEngine(new CliRepository());
        Result<String> result = engine.execute();
        System.out.println(result);
        main(args);
    }
}
