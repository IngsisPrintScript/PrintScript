/*
 * My Project
 */

package com.ingsis.utils.metachar.string.builder;

import com.ingsis.utils.metachar.MetaChar;
import java.util.ArrayList;
import java.util.List;

public class MetaCharStringBuilder {
    private final List<MetaChar> charStream;
    private final Integer line;
    private final Integer column;

    public MetaCharStringBuilder(List<MetaChar> metaChars) {
        this.charStream = List.copyOf(metaChars);
        if (metaChars.size() <= 0) {
            this.line = -1;
            this.column = -1;
        } else {
            this.line = metaChars.get(0).line();
            this.column = metaChars.get(0).column();
        }
    }

    public MetaCharStringBuilder() {
        this(List.of());
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

    public MetaCharStringBuilder append(MetaChar newMetaChar) {
        List<MetaChar> next = new ArrayList<>(this.charStream);
        next.add(newMetaChar);
        return new MetaCharStringBuilder(next);
    }
}
