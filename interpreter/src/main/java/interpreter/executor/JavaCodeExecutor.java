package interpreter.executor;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

public record JavaCodeExecutor(JavaCompiler compiler, String className)
    implements CodeExecutorInterface {
  public JavaCodeExecutor() {
    this(ToolProvider.getSystemJavaCompiler(), "TranspiledCode");
  }

  public JavaCodeExecutor(String className) {
    this(ToolProvider.getSystemJavaCompiler(), className);
  }

  @Override
  public Result<String> executeCode(Path path) {
    if (compiler() == null) return new IncorrectResult<>("compiler is null.");
    int result = compiler().run(null, null, null, path.toAbsolutePath().toString());
    if (result != 0) return new IncorrectResult<>("compiler result is " + result);
    File currentDir = new File(path.getParent().toString());
    try {
      URLClassLoader classLoader =
          URLClassLoader.newInstance(new URL[] {currentDir.toURI().toURL()});
      Class<?> cls = Class.forName(className(), true, classLoader);
      Method main = cls.getMethod("main", String[].class);
      String[] args = {};
      main.invoke(null, (Object) args);
      return new CorrectResult<>("Compiled file has ben executed successfully.");
    } catch (Exception e) {
      return new IncorrectResult<>(e.getMessage());
    }
  }
}
