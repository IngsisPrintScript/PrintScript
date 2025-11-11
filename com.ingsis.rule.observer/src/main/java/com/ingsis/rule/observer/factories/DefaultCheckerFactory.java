/*
 * My Project
 */

package com.ingsis.rule.observer.factories;

import com.ingsis.rule.observer.EventsChecker;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import com.ingsis.visitors.Checker;

public class DefaultCheckerFactory implements CheckerFactory {

    @Override
    public Checker createInMemoryEventBasedChecker(PublishersFactory publishersFactory) {
        return new EventsChecker(publishersFactory);
    }
}
