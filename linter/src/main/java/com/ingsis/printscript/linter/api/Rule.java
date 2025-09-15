/*
 * My Project
 */

package com.ingsis.printscript.linter.api;

import com.ingsis.printscript.astnodes.Node;
import java.util.List;

public interface Rule {
    String id();

    String description();

    boolean enabled(AnalyzerConfig cfg);

    List<Violation> check(Node root, AnalyzerConfig cfg);
}
