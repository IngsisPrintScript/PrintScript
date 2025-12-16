/*
 * My Project
 */

package com.ingsis.utils.rule.observer.factories;

import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerSupplier;

public interface CheckerFactory {
    Checker createInMemoryEventBasedChecker(HandlerSupplier handlerSupplier);
}
