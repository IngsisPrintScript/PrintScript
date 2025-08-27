package common;

import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Environment {
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

    public Result putIdType(String id, String type) {
        try{
            if (idTypeMap.containsKey(id)) {
                return new IncorrectResult("Id has already been declared.");
            }
            idTypeMap.put(id, type);
            return new CorrectResult<String>("Id has been declared.");
        } catch (Exception e){
            return new IncorrectResult(e.getMessage());
        }
    }
    public Record putIdValue(String id, Object value) {
        try {
            if (!idTypeMap.containsKey(id)) {
                return new IncorrectResult("Id has not been declared.");
            }
            idValueMap.put(id, value);
            return new CorrectResult<String>("Id has been initialized correctly.");
        } catch (Exception e){
            return new IncorrectResult(e.getMessage());
        }
    }

    public Result getIdType(String id) {
        try{
            if (!idTypeMap.containsKey(id)) {
                return new IncorrectResult("Id has not been declared.");
            }
            return new CorrectResult<String>(idTypeMap.get(id));
        } catch (Exception e){
            return new IncorrectResult(e.getMessage());
        }
    }
    public Result getIdValue(String id) {
        try {
            if (!idTypeMap.containsKey(id)) {
                return new IncorrectResult("Id has not been declared.");
            }
            return new CorrectResult<Object>(idValueMap.get(id));
        } catch (Exception e){
            return new IncorrectResult(e.getMessage());
        }
    }
}
