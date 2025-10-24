pipeline {
  agent any

  environment {
    // Test suite inside your project (adjust if your suite name / path differs)
    TEST_SUITE_PATH = "Test Suites/TS_SmokeTests"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Run Katalon Tests (detect katalonc in Downloads)') {
      steps {
        sh '''
          set -euo pipefail
          echo "=== Jenkins workspace ==="
          echo "WORKSPACE=${WORKSPACE}"
          pwd
          ls -la

          # 1) Find the .prj file in the workspace (first match)
          PRJ_FILE=$(find "${WORKSPACE}" -maxdepth 4 -type f -name "*.prj" | head -n 1 || true)
          if [ -z "${PRJ_FILE}" ]; then
            echo "ERROR: No .prj file found in workspace. Please ensure the Katalon project (.prj) is in the repository root or adjust the path."
            echo "You can verify with: find ${WORKSPACE} -type f -name '*.prj'"
            exit 2
          fi
          echo "Found project file: ${PRJ_FILE}"
          PROJECT_ABS="$(cd "$(dirname "${PRJ_FILE}")" && pwd)/$(basename "${PRJ_FILE}")"
          echo "Resolved PROJECT_PATH=${PROJECT_ABS}"

          # 2) Locate katalonc - look in common locations (Downloads and /Applications)
          CANDIDATES=(
            "${HOME}/Downloads/Katalon_Studio_Engine/katalonc"
            "${HOME}/Downloads/Katalon_Studio_Engine*/katalonc"
            "/Applications/Katalon_Studio_Engine/katalonc"
            "/opt/katalon/katalonc"
          )
          KATALON_BIN=""
          for p in "${CANDIDATES[@]}"; do
            # shellcheck disable=SC2086
            for match in $(ls -d ${p} 2>/dev/null || true); do
              if [ -x "${match}" ]; then
                KATALON_BIN="${match}"
                break 2
              fi
            done
          done

          # As a fallback, search home Downloads recursively
          if [ -z "${KATALON_BIN}" ]; then
            FOUND=$(find "${HOME}/Downloads" -maxdepth 3 -type f -name "katalonc" | head -n 1 || true)
            if [ -n "${FOUND}" ] && [ -x "${FOUND}" ]; then
              KATALON_BIN="${FOUND}"
            fi
          fi

          if [ -z "${KATALON_BIN}" ]; then
            echo "ERROR: katalonc not found in Downloads or /Applications. Please install Katalon Runtime Engine or place katalonc in ~/Downloads/Katalon_Studio_Engine/."
            echo "Tried candidates:"
            printf '%s\n' "${CANDIDATES[@]}"
            exit 3
          fi

          echo "Using katalonc at: ${KATALON_BIN}"
          "${KATALON_BIN}" -noSplash -version || true

          # 3) Run Katalon (capture output to a log file)
          KATALON_LOG="${WORKSPACE}/katalon_run.log"
          rm -f "${KATALON_LOG}" || true
          echo "Starting Katalon CLI..."
          # Adjust TEST_SUITE_PATH or add -apiKey="..." if you need to authenticate with TestOps
          "${KATALON_BIN}" -noSplash -runMode=console \
            -projectPath="${PROJECT_ABS}" \
            -retry=0 -testSuitePath="${TEST_SUITE_PATH}" \
            -executionProfile="default" -browserType="Chrome (headless)" 2>&1 | tee "${KATALON_LOG}"
          EXIT_CODE=${PIPESTATUS[0]}

          echo "Katalon CLI finished with exit code: ${EXIT_CODE}"
          echo "---- Last 200 lines of Katalon log ----"
          tail -n 200 "${KATALON_LOG}" || true

          if [ "${EXIT_CODE}" -ne 0 ]; then
            echo "Katalon execution FAILED (exit ${EXIT_CODE})"
            # keep logs for Jenkins artifacts
            exit ${EXIT_CODE}
          fi
          echo "Katalon execution completed successfully."
        '''
      }
    } // stage

    stage('Debug: show Reports') {
      steps {
        sh '''
          echo "Listing Reports directory (if created by KRE):"
          ls -la Reports || true
          echo "Recursive Reports list (maxdepth 5):"
          find Reports -maxdepth 5 -type f -print || true
        '''
      }
    }
  } // stages

  post {
    always {
      // Make sure Jenkins can read reports
      sh 'chmod -R a+r Reports || true'

      // Archive all reports so you can download them
      archiveArtifacts artifacts: 'Reports/**', allowEmptyArchive: true

      // Publish JUnit results without failing the post step if none are found
      junit allowEmptyResults: true, testResults: 'Reports/**/JUnit/*.xml'
    }
    success { echo 'Pipeline completed successfully' }
    failure { echo 'Pipeline failed — check console & archived Reports' }
  }
}
