/*
 * My Project
 */

package com.ingsis.interpreter.visitor.factory;

import com.ingsis.interpreter.visitor.DefaultInterpreterVisitor;
import com.ingsis.utils.nodes.visitors.Interpreter;

public final class DefaultInterpreterVisitorFactory implements InterpreterVisitorFactory {
  @Override
  public Interpreter createDefaultInterpreter() {
    return new DefaultInterpreterVisitor();
  }
}
