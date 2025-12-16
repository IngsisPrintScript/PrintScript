#!/bin/bash

JAR_PATH="./com.ingsis.engine/build/libs/com.ingsis.engine-0.0.0-SNAPSHOT-all.jar"

if [[ ! -f "$JAR_PATH" ]]; then
    echo "JAR not found at $JAR_PATH"
    exit 1
fi

java -jar "$JAR_PATH" "$@"

