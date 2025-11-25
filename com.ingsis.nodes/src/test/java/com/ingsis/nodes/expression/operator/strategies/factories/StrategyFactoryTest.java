/*
 * My Project
 */

package com.ingsis.nodes.expression.operator.strategies.factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.nodes.expression.operator.strategies.OperatorStrategy;
import org.junit.jupiter.api.Test;

public class StrategyFactoryTest {
    @Test
    public void anonymousFactoryProvidesStrategies() {
        StrategyFactory f =
                new StrategyFactory() {
                    @Override
                    public OperatorStrategy typeAssignationStrategy() {
                        return args -> null;
                    }

                    @Override
                    public OperatorStrategy valueAssignationStrategy() {
                        return args -> null;
                    }
                };

        assertNotNull(f.typeAssignationStrategy());
        assertNotNull(f.valueAssignationStrategy());
    }
}
