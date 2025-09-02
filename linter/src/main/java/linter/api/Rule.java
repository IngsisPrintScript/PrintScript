package linter.api;

import java.util.List;
import nodes.common.Node;

public interface Rule {
  String id();

  String description();

  boolean enabled(AnalyzerConfig cfg);

  List<Violation> check(Node root, AnalyzerConfig cfg);
}
