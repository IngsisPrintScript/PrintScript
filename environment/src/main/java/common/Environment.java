package common;

import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Environment implements EnvironmentInterface {
    private final Map<String, String> idTypeMap;
    private final Map<String, Object> idValueMap;

    private Environment() {
        idTypeMap = new ConcurrentHashMap<>();
        idValueMap = new ConcurrentHashMap<>();
    }

    private static class EnvironmentHelper {
        private static final Environment INSTANCE = new Environment();
    }

    public static Environment getInstance() {
        return EnvironmentHelper.INSTANCE;
    }
    @Override
    public Result<String> putIdType(String id, String type) {
        try{
            if (variableIsDeclared(id)) {
                return new IncorrectResult<>("Id has already been declared.");
            }
            idTypeMap.put(id, type);
            return new CorrectResult<String>("Id has been declared.");
        } catch (Exception e){
            return new IncorrectResult<>(e.getMessage());
        }
    }
    @Override
    public Result<Object> putIdValue(String id, Object value) {
        try {
            if (!variableIsDeclared(id)) {
                return new IncorrectResult("Id has not been declared.");
            }
            idValueMap.put(id, value);
            return new CorrectResult<>(value);
        } catch (Exception e){
            return new IncorrectResult<>(e.getMessage());
        }
    }
    @Override
    public Result<String> getIdType(String id) {
        try{
            if (!variableIsDeclared(id)) {
                return new IncorrectResult<>("Id has not been declared.");
            }
            return new CorrectResult<>(idTypeMap.get(id));
        } catch (Exception e){
            return new IncorrectResult<>(e.getMessage());
        }
    }
    @Override
    public Result<Object> getIdValue(String id) {
        try {
            if (!variableIsDeclared(id)) {
                return new IncorrectResult<>("Id has not been declared.");
            }
            return new CorrectResult<>(idValueMap.get(id));
        } catch (Exception e){
            return new IncorrectResult<>(e.getMessage());
        }
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return idTypeMap.containsKey(id);
    }
}
