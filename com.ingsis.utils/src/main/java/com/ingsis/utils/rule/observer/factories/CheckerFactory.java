/*
 * My Project
 */

package com.ingsis.utils.rule.observer.factories; /*
                                                   * My Project
                                                   */

import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;

public interface CheckerFactory {
    Checker createInMemoryEventBasedChecker(PublishersFactory publishersFactory);
}
