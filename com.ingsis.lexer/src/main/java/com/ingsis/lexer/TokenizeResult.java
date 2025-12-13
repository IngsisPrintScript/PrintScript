/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.utils.token.Token;

public sealed interface TokenizeResult {
    public TokenizeResult comparePriority(TokenizeResult tokenizeResult);

    public record COMPLETE(Token token, Integer priority) implements TokenizeResult {
        @Override
        public TokenizeResult comparePriority(TokenizeResult tokenizeResult) {
            return switch (tokenizeResult) {
                case COMPLETE C -> this.priority() < C.priority() ? this : C;
                case PREFIX P -> this;
                case INVALID I -> this;
            };
        }
    }

    public record PREFIX(Integer priority) implements TokenizeResult {
        @Override
        public TokenizeResult comparePriority(TokenizeResult tokenizeResult) {
            return switch (tokenizeResult) {
                case COMPLETE C -> tokenizeResult;
                case PREFIX P -> this.priority() < P.priority() ? this : P;
                case INVALID I -> this;
            };
        }
    }

    public record INVALID() implements TokenizeResult {
        @Override
        public TokenizeResult comparePriority(TokenizeResult tokenizeResult) {
            return switch (tokenizeResult) {
                case INVALID I -> this;
                default -> tokenizeResult;
            };
        }
    }
}
