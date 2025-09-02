package factories;

import common.ExecutionEngineInterface;
import repositories.CodeRepositoryInterface;

public interface EngineFactoryInterface {
    ExecutionEngineInterface getInterpreterEngine(CodeRepositoryInterface repository);
}
