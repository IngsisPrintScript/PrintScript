/*
 * My Project
 */

package com.ingsis.typer.string;

import com.ingsis.types.Types;
import java.util.List;

public final class DefaultStringTypeGetter {
    public Types getType(String input) {
        List<Types> types = Types.allTypes();
        for (Types type : types) {
            if (type.checkFormat(input)) {
                return type;
            }
        }

        return Types.UNDEFINED;
    }
}
