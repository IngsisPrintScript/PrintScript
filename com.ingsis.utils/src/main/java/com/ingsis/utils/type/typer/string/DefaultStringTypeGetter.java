/*
 * My Project
 */

package com.ingsis.utils.type.typer.string;

import com.ingsis.utils.type.types.Types;

public final class DefaultStringTypeGetter {

    public Types getType(String input) {
        for (Types type : Types.allTypes()) {
            if (type == Types.UNDEFINED || type == Types.NIL) continue;

            if (type.checkFormat(input)) {
                return type;
            }
        }

        if ("nil".equals(input)) return Types.NIL;

        return Types.UNDEFINED;
    }
}
