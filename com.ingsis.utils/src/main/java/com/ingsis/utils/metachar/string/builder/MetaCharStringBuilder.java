/*
 * My Project
 */

package com.ingsis.utils.metachar.string.builder;

import com.ingsis.utils.metachar.MetaChar;
import java.util.ArrayList;
import java.util.List;

public class MetaCharStringBuilder {
    private final List<MetaChar> charStream;
    private Integer line;
    private Integer column;

    public MetaCharStringBuilder(List<MetaChar> metaChars) {
        this.charStream = new ArrayList<>(metaChars);
        if (metaChars.size() <= 0) {
            this.line = null;
            this.column = null;
        } else {
            this.line = metaChars.get(0).line();
            this.column = metaChars.get(0).column();
        }
    }

    public MetaCharStringBuilder() {
        this(new ArrayList<>());
    }

    public Integer getLine() {
        return this.line;
    }

    public Integer getColumn() {
        return this.column;
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();
        for (MetaChar metaChar : charStream) {
            sb.append(metaChar.character());
        }
        return sb.toString();
    }

    public void append(MetaChar newMetaChar) {
        if (this.column == null) {
            this.column = newMetaChar.column();
        }
        if (this.line == null) {
            this.line = newMetaChar.line();
        }
        this.charStream.add(newMetaChar);
    }
}
