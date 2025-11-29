/*
 * My Project
 */

package com.ingsis.utils.type.typer.string; /*
                                             * My Project
                                             */

import com.ingsis.utils.type.types.Types;
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
