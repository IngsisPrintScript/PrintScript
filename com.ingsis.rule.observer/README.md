Com.ingsis.rule.observer

Propósito:
- Sistema de observadores y publicación de eventos relacionados con reglas y validaciones dentro del proyecto.

Clases clave:
- `NodeEventPublisher`, `GenericNodeEventPublisher` — publishers para eventos.
- `NodeEventHandler`, `NodeEventHandlerRegistry`, `InMemoryNodeEventHandlerRegistry` — registro y manejo de handlers.
- `factories/` — fábricas para checkers y publishers.

Dónde mirar:
- `src/main/java/com/ingsis/rule/observer`.

Sugerencia: añadir ejemplos de cómo registrar handlers y cómo publicar eventos para que DeepWiki capture flujos de integración.
