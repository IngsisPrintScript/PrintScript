Com.ingsis.nodes

Propósito:
- Definición de los nodos AST y patrones visitor (Checkable, Interpreter, Visitable).

Clases clave:
- `Node`, `ExpressionNode`, nodos concretos (`LiteralNode`, `IdentifierNode`, `OperatorNode`, etc.).
- `visitors/` — interfaces `Visitor`, `Interpreter`, `Checker`.

Dónde mirar:
- `src/main/java/com/ingsis/nodes` y `src/main/java/com/ingsis/visitors`.

Sugerencia: añadir diagrama de nodos y ejemplos de árbol AST para DeepWiki.
