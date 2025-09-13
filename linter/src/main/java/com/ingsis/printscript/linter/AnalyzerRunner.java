/*
 * My Project
 */

package com.ingsis.printscript.linter;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.linter.api.AnalyzerConfig;
import com.ingsis.printscript.linter.api.Rule;
import com.ingsis.printscript.linter.api.Violation;
import java.util.ArrayList;
import java.util.List;

public final class AnalyzerRunner {
    private final List<Rule> rules;

    public AnalyzerRunner(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Violation> analyze(Node root, AnalyzerConfig cfg) {
        List<Violation> out = new ArrayList<>();
        for (Rule r : rules) {
            if (r.enabled(cfg)) out.addAll(r.check(root, cfg));
        }
        return out;
    }
}
