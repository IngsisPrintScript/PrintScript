/*
 * My Project
 */

package com.ingsis.rule.observer.factories;

import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import com.ingsis.visitors.Checker;

public interface CheckerFactory {
    Checker createInMemoryEventBasedChecker(PublishersFactory publishersFactory);
}
