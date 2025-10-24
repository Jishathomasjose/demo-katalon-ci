pipeline {
  agent any

  environment {
    // Optional override: set this in the job config or pipeline params if katalon is installed elsewhere
    KATALON_BIN = '' 
    // Optional: path to katalon installation dir (if you prefer)
    KATALON_HOME = ''
    PROJECT_PATTERN = '**/*.prj'
    REPORT_DIR = 'Reports'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Run Katalon Tests (detect katalonc)') {
      steps {
        sh '''
          set -euo pipefail
          echo "=== Jenkins workspace ==="
          echo "WORKSPACE=${WORKSPACE}"
          pwd
          ls -la

          # try explicit env overrides first (support job config)
          if [ -n "${KATALON_BIN}" ]; then
            echo "KATALON_BIN override provided: ${KATALON_BIN}"
            if [ -x "${KATALON_BIN}" ]; then
              KATALON_EXEC="${KATALON_BIN}"
            else
              echo "ERROR: KATALON_BIN is set but not executable: ${KATALON_BIN}"
              exit 3
            fi
          elif [ -n "${KATALON_HOME}" ]; then
            echo "KATALON_HOME override provided: ${KATALON_HOME}"
            if [ -x "${KATALON_HOME}/katalonc" ]; then
              KATALON_EXEC="${KATALON_HOME}/katalonc"
            else
              echo "ERROR: katalonc not found in KATALON_HOME: ${KATALON_HOME}"
              exit 3
            fi
          else
            # candidate locations on mac
            CANDIDATES=(
              "${HOME}/Downloads/Katalon_Studio_Engine/katalonc"
              "${HOME}/Downloads/Katalon_Studio_Engine*/katalonc"
              "/Applications/Katalon_Studio_Engine/katalonc"
              "/opt/katalon/katalonc"
            )

            KATALON_EXEC=""
            for p in "${CANDIDATES[@]}"; do
              # expand globs
              for f in $p; do
                if [ -f "$f" ] && [ -x "$f" ]; then
                  KATALON_EXEC="$f"
                  break 2
                fi
              done
            done

            # Extra search in ~/Downloads for any katalonc executable (depth 3)
            if [ -z "$KATALON_EXEC" ]; then
              FOUND=$(find "${HOME}/Downloads" -maxdepth 3 -type f -name 'katalonc' -perm -u=x 2>/dev/null | head -n 1 || true)
              if [ -n "$FOUND" ]; then
                KATALON_EXEC="$FOUND"
              fi
            fi
          fi

          if [ -z "${KATALON_EXEC}" ]; then
            echo "ERROR: katalonc not found. Please install Katalon Runtime Engine or place katalonc in one of:"
            printf '  %s\n' "${CANDIDATES[@]}"
            echo "Or set KATALON_BIN or KATALON_HOME in the pipeline/job config."
            exit 3
          fi

          echo "Found katalonc at: ${KATALON_EXEC}"
          # ensure executable
          chmod +x "${KATALON_EXEC}" || true

          # locate project file
          PRJ_FILE=$(find "${WORKSPACE}" -maxdepth 4 -type f -name '*.prj' | head -n 1 || true)
          if [ -z "${PRJ_FILE}" ]; then
            echo "ERROR: No .prj file found under workspace (${WORKSPACE})."
            exit 4
          fi
          echo "Found project file: ${PRJ_FILE}"

          # run katalon
          mkdir -p "${REPORT_DIR}"
          "${KATALON_EXEC}" -noSplash -runMode=console -projectPath="${PRJ_FILE}" -reportFolder="${REPORT_DIR}" -reportFileName="katalon-report" -browserType="Chrome" || RC=$?
          # if katalon failed, show exit code and keep artifacts for debugging
          if [ "${RC:-0}" != "0" ]; then
            echo "katalonc exited with code ${RC:-0}"
            exit "${RC:-1}"
          fi

          echo "Katalon execution finished."
        '''
      }
    }

    stage('Debug: show Reports') {
      when {
        expression { fileExists(env.REPORT_DIR) }
      }
      steps {
        sh 'ls -la ${REPORT_DIR} || true'
      }
    }
  }

  post {
    always {
      // try to archive if reports present (won't fail job if not)
      archiveArtifacts artifacts: 'Reports/**', allowEmptyArchive: true
      junit allowEmptyResults: true, testResults: 'Reports/**/*.xml'
      echo "Pipeline finished"
    }
    failure {
      echo "Pipeline failed — check console & archived Reports"
    }
  }
}
