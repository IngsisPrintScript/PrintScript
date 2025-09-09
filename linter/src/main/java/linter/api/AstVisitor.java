package linter.api;

public interface AstVisitor {
  void visit(VariableDeclarationNode node);

  void visit(PrintlnNode node);
}
