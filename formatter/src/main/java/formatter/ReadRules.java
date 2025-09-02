package formatter;

import formatter.FormatterRules.FormatterRules;

import java.io.InputStream;
import java.util.HashMap;

public class ReadRules implements ReadRulesInterface{
    @Override
    public HashMap<FormatterRules, Boolean> readRules() {
        Yaml rules = new Yaml();
        InputStream inputStream = ReadRules.class.getResourceAsStream("/Rules.yml");

        return null;
    }
}
