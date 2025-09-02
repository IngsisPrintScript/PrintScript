package formatter.yamlAnalizer;

import formatter.FormatterRules.AscriptionRules.ColonSpacingAfter;
import formatter.FormatterRules.AscriptionRules.ColonSpacingBefore;
import formatter.FormatterRules.AssignationRules.SpaceAfterAssignation;
import formatter.FormatterRules.AssignationRules.SpaceBeforeAssignation;
import formatter.FormatterRules.FormatterRules;
import formatter.FormatterRules.PrintlnRules.OneJumpBeforePrint;
import formatter.FormatterRules.PrintlnRules.TwoJumpsBeforePrint;
import formatter.FormatterRules.PrintlnRules.ZeroJumpsBeforePrint;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;

public class ReadRules implements ReadRulesInterface {
    @Override
    public HashMap<FormatterRules, Boolean> readRules() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = ReadRules.class.getResourceAsStream("/Rules.yaml")) {
            if (inputStream == null) {
                throw new RuntimeException("Did not found Rules.yml");
            }
            HashMap<String, Boolean> raw = yaml.load(inputStream);
            HashMap<FormatterRules, Boolean> rules = new HashMap<>();
            for (String key : raw.keySet()) {
                Boolean value = raw.get(key);
                switch (key) {
                    case "ColonSpacingAfter" -> rules.put(new ColonSpacingAfter(), value);
                    case "ColonSpacingBefore" -> rules.put(new ColonSpacingBefore(), value);
                    case "SpaceAfterAssignation" -> rules.put(new SpaceAfterAssignation(), value);
                    case "SpaceBeforeAssignation" -> rules.put(new SpaceBeforeAssignation(), value);
                    case "ZeroJumpsBeforePrint" -> rules.put(new ZeroJumpsBeforePrint(), value);
                    case "OneJumpBeforePrint" -> rules.put(new OneJumpBeforePrint(), value);
                    case "TwoJumpsBeforePrint" -> rules.put(new TwoJumpsBeforePrint(), value);
                }
            }
            return rules;

        } catch (Exception e) {
            throw new RuntimeException("Error reading Rules.yml", e);
        }
    }


    public static void main(String[] args) {
        ReadRules loader = new ReadRules();
        HashMap<FormatterRules, Boolean> rules = loader.readRules();
        System.out.println(rules);
    }
}
