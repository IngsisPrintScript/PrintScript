/*
 * My Project
 */

package com.ingsis.parser.syntactic;

public enum NodePriority {
    // ===== Top-level Statement Nodes =====
    STATEMENT(0),

    // ===== Generic Nodes =====
    EXPRESSION(1),

    // ===== Structured Expression Nodes =====
    CALL_FUNCTION(2),
    OPERATOR(3),

    // ===== Atomic Expression Nodes =====
    LITERAL(4),
    IDENTIFIER(5);

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
