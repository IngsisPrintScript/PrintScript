package formatter.yamlAnalizer;


import formatter.FormatterRules.FormatterRules;

import java.util.HashMap;

public interface ReadRulesInterface {

    public HashMap<FormatterRules, Boolean> readRules();
}
