package factories;

import common.ExecutionEngineInterface;
import repositories.CodeRepositoryInterface;


public class DefaultEngineFactory implements EngineFactoryInterface {
    @Override
    public ExecutionEngineInterface getInterpreterEngine(CodeRepositoryInterface repository) {
        return null;
    }
}
