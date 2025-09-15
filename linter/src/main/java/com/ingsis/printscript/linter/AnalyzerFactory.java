/*
 * My Project
 */

package com.ingsis.printscript.linter;

import com.ingsis.printscript.linter.api.Rule;
import com.ingsis.printscript.linter.rules.NamingRule;
import com.ingsis.printscript.linter.rules.PrintlnSimpleArgRule;
import java.util.List;

public final class AnalyzerFactory {
    private AnalyzerFactory() {}

    public static AnalyzerRunner defaultRunner() {
        List<Rule> rules = List.of(new NamingRule(), new PrintlnSimpleArgRule());
        return new AnalyzerRunner(rules);
    }
}
