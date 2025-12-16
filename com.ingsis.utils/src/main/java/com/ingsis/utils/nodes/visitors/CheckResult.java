/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;

public sealed interface CheckResult {
    public record CORRECT(SemanticEnvironment environment) implements CheckResult {}

    public record INCORRECT(SemanticEnvironment environment, String error) implements CheckResult {}
}
