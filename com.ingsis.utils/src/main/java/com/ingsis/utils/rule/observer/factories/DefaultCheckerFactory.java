/*
 * My Project
 */

package com.ingsis.utils.rule.observer.factories;

import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.rule.observer.EventsChecker;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;

public class DefaultCheckerFactory implements CheckerFactory {

  public Checker createInMemoryEventBasedChecker(PublishersFactory publishersFactory) {
    return new EventsChecker(publishersFactory);
  }
}
