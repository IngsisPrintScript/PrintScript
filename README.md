PrintScript — Integración lógica y roles de módulos

Resumen
- PrintScript implementa un lenguaje simple y su pipeline completo: lectura de código → tokenización → parseo sintáctico → chequeo semántico / análisis estático → tipado → interpretación/ejecución.
- Este README explica la integración lógica entre módulos, el rol de cada uno y el flujo de trabajo típico para desarrollar y documentar el proyecto.

Visión general del flujo (alto nivel)
1. Entrada de código (archivo .pisp) es leído por `charstream`.
2. `lexer` tokeniza el texto en `Token`s usando diversos `tokenizers`.
3. `tokenstream` encapsula la secuencia de tokens y la entrega al `syntactic` parser.
4. `syntactic` produce un AST (nodos en `com.ingsis.nodes`).
5. `typer`/`types` realizan inferencia/resolución de tipos sobre el AST.
6. `semantic` y `sca` (Static Code Analyzer) ejecutan reglas y chequeos semánticos sobre el AST y publican eventos/publisher si es necesario.
7. `interpreter` + `runtime` ejecutan el AST validado, manipulando `Environment` y produciendo `Result`.
8. `result.factory` encapsula la creación de objetos `Result` (Correct/Incorrect), y `rule.observer` gestiona la publicación/registro de eventos relacionados a reglas.

Roles por módulo (detalle)
- `com.ingsis.charstream`
  - Propósito: lectura de fuentes (archivo, memoria) y entrega de caracteres.
  - Rol en flujo: punto de entrada para el lexer.

- `com.ingsis.lexer`
  - Propósito: tokenizar el flujo de caracteres en tokens.
  - Componentes clave: `Tokenizer`s, `TokenizersRegistry`, `DefaultLexer`.

- `com.ingsis.tokens` y `com.ingsis.tokenstream`
  - Propósito: definir la estructura `Token` y proveer `TokenStream` para el parser.
  - Rol: transportar tokens con metadatos (tipo, valor, posición).

- `com.ingsis.syntactic`
  - Propósito: construir el AST mediante parsers modulares (expression, declaration, conditional, etc.).
  - Componentes clave: `ParserFactory`, `ParserChainFactory`, diversos `parsers/`.

- `com.ingsis.nodes` y `com.ingsis.visitors`
  - Propósito: modelos de nodos AST y contratos `Visitor`/`Interpreter`/`Checker`.
  - Importancia: estructura central que usan semantic/typer/interpreter.

- `com.ingsis.typer` y `com.ingsis.types`
  - Propósito: inferencia y resolución de tipos por nodos y expresiones.
  - Rol: asegurar consistencia de tipos antes/ durante la interpretación.

- `com.ingsis.semantic`
  - Propósito: chequeos semánticos que no son estrictamente de tipos (existencia de variables, validez de operadores, etc.).
  - Relación: puede emitir eventos hacia `rule.observer`/`sca`.

- `com.ingsis.sca` (Static Code Analyzer)
  - Propósito: motor para ejecutar reglas estáticas, handlers y publishers para reportar findings.
  - Uso: análisis offline o integrado durante la compilación/análisis.

- `com.ingsis.interpreter` y `com.ingsis.runtime`
  - Propósito: evaluación de AST y gestión de estado/entorno (variables, funciones).
  - Resultado: produce `Result` a través de `result.factory`.

- `com.ingsis.result` y `com.ingsis.result.factory`
  - Propósito: modelos de resultados (éxito/ error) y fábrica para instanciarlos.

- `com.ingsis.rule.observer`
  - Propósito: mecanismo de publicación y manejo de eventos relacionados con reglas (registrar handlers, publishers).
  - Integración: `semantic` y `sca` lo usan para reportar issues.

Buenas prácticas para desarrollo y documentación
- Mantener `README.md` en módulos principales (ya añadidos) con propósito, clases clave y dónde mirar.
- Añadir JavaDoc en clases públicas y ejemplos de uso mínimos (peticiones pequeñas) para mejorar la calidad de resúmenes automáticos.
- Iterar en `.devin/wiki.json` para priorizar las páginas críticas que DeepWiki debe generar (si eliges usar steering por repo).

Flujo de trabajo típico (desarrollador)
1. Escribe código y pruebas en el módulo correspondiente (p. ej., añadir tokenizers en `com.ingsis.lexer/tokenizers`).
2. Añade/actualiza `README.md` en el módulo si introduces conceptos nuevos. DeepWiki usará estos `README`s para mejores resúmenes.
3. Ejecuta pruebas locales y linters.
4. Si deseas que DeepWiki incluya estructura guiada, crea/actualiza `.devin/wiki.json` y súbela (ver `DeepWiki.md` para opciones).
5. Repite: conecta el repo en DeepWiki y regenera wiki para revisar resultados.

Comandos útiles (Windows PowerShell / cmd)
- Añadir y commitear solo READMEs:
```bash
git add com.ingsis.*/*.md
git commit -m "docs: add module READMEs for DeepWiki"
git push origin tests
```
- Si quieres commitear también `.devin/wiki.json` (solo si quieres que la guía esté en el servidor):
```powershell
# quitar la exclusión local si la añadiste en .git/info/exclude usando un editor, luego:
git add .devin/wiki.json
git commit -m "chore: add devin wiki steering file"
git push origin tests
```