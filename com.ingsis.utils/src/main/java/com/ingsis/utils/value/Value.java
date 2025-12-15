package com.ingsis.utils.value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.ingsis.utils.type.types.Types;

public sealed interface Value {
  public record IntValue(BigDecimal v) implements Value {
  }

  public record StringValue(String v) implements Value {
  }

  public record BooleanValue(boolean v) implements Value {
  }

  public enum UnitValue implements Value {
    INSTANCE
  }

  public record GlobalFunctionValue(
      Map<String, Types> args,
      Types returnType,
      Function<List<Value>, Value> impl) implements Value {
  }
}
