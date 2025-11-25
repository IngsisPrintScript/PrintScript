Com.ingsis.engine

Propósito:
- Orquesta la pipeline completa: construye y conecta `lexer`, `syntactic`, `semantic`, `interpreter`.

Clases clave:
- `Engine.java` — punto de entrada/coordina factories.
- `CliEngine.java` — CLI de ejemplo para ejecutar programas.
- `factories/` — factories para `charstream`, `lexer`, `syntactic`, `interpreter`, `sca`.

Dónde mirar:
- `src/main/java/com/ingsis/engine` — factories y versiones.

Ejemplo rápido:
- Ver `CliEngine` para cómo se ensamblan las piezas.
