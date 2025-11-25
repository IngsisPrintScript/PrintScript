Com.ingsis.syntactic

Propósito:
- Parseo sintáctico modular: conjuntos de parsers (expression, declaration, conditional, operators) y factories para encadenarlos.

Clases clave:
- `SyntacticParser`, `DefaultSyntacticParser` — implementación principal del parser.
- `parsers/` — parsers específicos por constructo.
- `factories/` — `ParserChainFactory`, `ParserFactory`.

Dónde mirar:
- `src/main/java/com/ingsis/syntactic`.

Sugerencia: incluir ejemplos de entrada/salida de parseo para mejorar la documentación automática.
