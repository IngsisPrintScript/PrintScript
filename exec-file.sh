#!/bin/bash
version=$1
java -jar ./com.ingsis.engine/build/libs/com.ingsis.engine-1.0.0-SNAPSHOT-all.jar --file ./test.pisp --version "$version" --action interpret --repl-mode

