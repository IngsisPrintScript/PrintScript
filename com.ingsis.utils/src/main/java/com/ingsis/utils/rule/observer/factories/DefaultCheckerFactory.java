/*
 * My Project
 */

package com.ingsis.utils.rule.observer.factories;

import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.rule.observer.EventsChecker;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerSupplier;

public class DefaultCheckerFactory implements CheckerFactory {

  public Checker createInMemoryEventBasedChecker(HandlerSupplier handlerSupplier) {
    return new EventsChecker(handlerSupplier);
  }
}
