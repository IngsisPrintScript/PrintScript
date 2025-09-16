/*
 * My Project
 */

package com.ingsis.printscript.astnodes;

import com.ingsis.printscript.visitor.VisitableInterface;
import java.util.List;

public interface Node extends VisitableInterface {
    List<Node> children();

    Boolean isNil();
}
