pipeline {
  agent any

  environment {
    // Optional overrides (can be left empty)
    KATALON_BIN = ''
    KATALON_HOME = ''
    PROJECT_PATTERN = '**/*.prj'
    REPORT_DIR = 'Reports'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Run Katalon Tests (detect katalonc)') {
      steps {
        sh '''
          set -euo pipefail
          echo "=== Jenkins workspace ==="
          echo "WORKSPACE=${WORKSPACE}"
          pwd
          ls -la

          # use safe expansions so -u doesn't fail when vars are unset
          KATALON_EXEC=""
          RC=0

          if [ -n "${KATALON_BIN:-}" ]; then
            echo "KATALON_BIN override provided: ${KATALON_BIN:-}"
            if [ -x "${KATALON_BIN:-}" ]; then
              KATALON_EXEC="${KATALON_BIN:-}"
            else
              echo "ERROR: KATALON_BIN is set but not executable: ${KATALON_BIN:-}"
              exit 3
            fi
          elif [ -n "${KATALON_HOME:-}" ]; then
            echo "KATALON_HOME override provided: ${KATALON_HOME:-}"
            if [ -x "${KATALON_HOME:-}/katalonc" ]; then
              KATALON_EXEC="${KATALON_HOME:-}/katalonc"
            else
              echo "ERROR: katalonc not found in KATALON_HOME: ${KATALON_HOME:-}"
              exit 3
            fi
          else
            CANDIDATES=(
              "${HOME}/Downloads/Katalon_Studio_Engine/katalonc"
              "${HOME}/Downloads/Katalon_Studio_Engine*/katalonc"
              "/Applications/Katalon_Studio_Engine/katalonc"
              "/opt/katalon/katalonc"
            )

            for p in "${CANDIDATES[@]}"; do
              for f in $p; do
                if [ -f "$f" ] && [ -x "$f" ]; then
                  KATALON_EXEC="$f"
                  break 2
                fi
              done
            done

            if [ -z "$KATALON_EXEC" ]; then
              FOUND=$(find "${HOM
