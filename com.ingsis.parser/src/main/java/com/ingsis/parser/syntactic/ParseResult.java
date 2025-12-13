/*
 * My Project
 */

package com.ingsis.parser.syntactic;

import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.utils.nodes.visitors.Checkable;

public sealed interface ParseResult<T extends Checkable> {
    ParseResult<T> comparePriority(ParseResult<T> anotherResult);

    public record COMPLETE<T extends Checkable>(
            T node, TokenStream finalStream, NodePriority nodePriority, Boolean isFinal)
            implements ParseResult<T> {
        @Override
        public ParseResult<T> comparePriority(ParseResult<T> anotherResult) {
            return switch (anotherResult) {
                case COMPLETE<T> C ->
                        this.nodePriority().priority() < C.nodePriority().priority() ? this : C;
                case PREFIX<T> P -> this;
                case INVALID<T> I -> this;
            };
        }
    }

    public record PREFIX<T extends Checkable>(NodePriority nodePriority) implements ParseResult<T> {
        @Override
        public ParseResult<T> comparePriority(ParseResult<T> anotherResult) {
            return switch (anotherResult) {
                case COMPLETE<T> C -> anotherResult;
                case PREFIX<T> P ->
                        this.nodePriority.priority() < P.nodePriority().priority() ? this : P;
                case INVALID<T> I -> this;
            };
        }
    }

    public record INVALID<T extends Checkable>() implements ParseResult<T> {
        @Override
        public ParseResult<T> comparePriority(ParseResult<T> anotherResult) {
            return switch (anotherResult) {
                case INVALID<T> I -> this;
                default -> anotherResult;
            };
        }
    }
}
