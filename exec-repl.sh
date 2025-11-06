#!/bin/bash
version=$1
java -jar ./com.ingsis.engine/build/libs/com.ingsis.engine-all.jar -version "$version"
