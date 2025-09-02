package app;


import common.ExecutionEngineInterface;
import factories.DefaultEngineFactory;
import repositories.CodeRepository;
import repositories.CodeRepositoryInterface;
import repository.CliRepository;
import results.Result;

public class CliApp {
    public static void main(String[] args) {
        CodeRepositoryInterface codeRepository = new CliRepository();
        while (codeRepository.hasMoreCode()){
            ExecutionEngineInterface engine = new DefaultEngineFactory().getInterpreterEngine(codeRepository);
            Result<String> result = engine.execute();
            if (!result.isSuccessful()) {
                System.out.println(result.errorMessage());
            }
        }
    }
}
