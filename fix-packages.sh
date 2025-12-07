#!/usr/bin/env bash

# =============================================================================
# Java Package Fixer (Correctly handles src/main/java, etc., skips buildSrc)
# =============================================================================

set -euo pipefail

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Root directory to scan (default: current directory)
ROOT_DIR="${1:-.}"

# Dry-run mode (true by default)
DRY_RUN="${DRY_RUN:-true}"

# Known Java source roots (adjust as needed, buildSrc removed)
SOURCE_ROOTS=("src/main/java" "src/test/java")

echo "Scanning Java files in: $ROOT_DIR"
echo "Dry-run mode: $DRY_RUN"
echo

find "$ROOT_DIR" -type f -name "*.java" | while IFS= read -r file; do
    [[ -f "$file" ]] || continue

    # Skip all files in buildSrc
    if [[ "$file" == *"/buildSrc/"* ]]; then
        continue
    fi

    # Find the matching source root
    rel_dir=""
    for root in "${SOURCE_ROOTS[@]}"; do
        if [[ "$file" == *"$root"* ]]; then
            rel_dir="${file#*"$root"/}"
            rel_dir=$(dirname "$rel_dir")
            break
        fi
    done

    # If no source root matched, fall back to relative path from ROOT_DIR
    if [[ -z "$rel_dir" ]]; then
        rel_dir=$(realpath --relative-to="$ROOT_DIR" "$(dirname "$file")")
    fi

    # Compute expected package
    expected_package="${rel_dir//\//.}"
    [[ "$expected_package" == "." ]] && expected_package=""

    # Extract current package
    current_package=$(grep -m1 -E '^\s*package\s+[a-zA-Z0-9_.]+\s*;' "$file" | \
                      sed -E 's/^\s*package\s+([a-zA-Z0-9_.]+)\s*;.*/\1/' || true)

    if [[ "$current_package" == "$expected_package" ]]; then
        printf "${GREEN}OK${NC}   %-60s %s\n" "$(basename "$file")" "$current_package"
        continue
    fi

    if [[ -z "$current_package" ]]; then
        printf "${YELLOW}MISSING${NC} → package %-50s %s\n" "$expected_package" "$file"
    else
        printf "${RED}FIX${NC}     %-60s %s → %s\n" "$(basename "$file")" "$current_package" "$expected_package"
    fi

    if [[ "$DRY_RUN" == "false" ]]; then
        # Backup original file
        cp "$file" "$file.bak"

        # Remove existing package line
        temp=$(mktemp)
        grep -v -E '^\s*package\s+[a-zA-Z0-9_.]+\s*;' "$file" > "$temp"

        # Insert correct package at top
        {
            [[ -n "$expected_package" ]] && echo "package $expected_package;"
            [[ -n "$expected_package" ]] && echo
            cat "$temp"
        } > "$file.fixed"

        mv "$file.fixed" "$file"
        rm -f "$temp"
        echo "   Fixed: $file (backup: $file.bak)"
    fi
done

echo
if [[ "$DRY_RUN" == "true" ]]; then
    echo "Dry-run complete. To apply changes, run:"
    echo "   DRY_RUN=false $0"
else
    echo "All fixes applied!"
fi
