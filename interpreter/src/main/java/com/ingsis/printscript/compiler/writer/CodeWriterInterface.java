/*
 * My Project
 */

package com.ingsis.printscript.compiler.writer;

import com.ingsis.printscript.results.Result;
import java.nio.file.Path;

public interface CodeWriterInterface {
    Result<Path> writeCode(String code);
}
