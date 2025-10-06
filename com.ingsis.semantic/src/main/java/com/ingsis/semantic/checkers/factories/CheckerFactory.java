/*
 * My Project
 */

package com.ingsis.semantic.checkers.factories;

import com.ingsis.semantic.checkers.publishers.factories.PublishersFactory;
import com.ingsis.visitors.Checker;

public interface CheckerFactory {
    Checker createInMemoryEventBasedChecker(PublishersFactory publishersFactory);
}
