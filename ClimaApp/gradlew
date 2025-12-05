#!/usr/bin/env sh
if [ -z "$JAVA_HOME" ]; then
  echo "Warning: JAVA_HOME is not set. Gradle may fail if Java is not available."
fi
DIR="$(cd "$(dirname "$0")" && pwd)"
exec "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
