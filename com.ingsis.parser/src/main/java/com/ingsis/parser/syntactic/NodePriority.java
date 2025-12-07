/*
 * My Project
 */

package com.ingsis.parser.syntactic;

public enum NodePriority {
    // ===== Atomic Expression Nodes =====
    LITERAL(0),
    IDENTIFIER(1),

    // ===== Structured Expression Nodes =====
    CALL_FUNCTION(2),
    OPERATOR(3),

    // ===== Generic Nodes =====
    EXPRESSION(4),

    // ===== Top-level Statement Nodes =====
    STATEMENT(5);

    private final int priority;

    NodePriority(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return this.priority;
    }

    public boolean higherThan(NodePriority other) {
        return this.priority < other.priority;
    }
}
