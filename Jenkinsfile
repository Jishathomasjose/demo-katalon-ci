pipeline {
  agent any

  environment {
    REPORT_DIR = 'Reports'
    KATALON_DIR = '/Users/jishathomas/Downloads/Katalon_Studio_Engine_MacOS-9.7.7/Katalon Studio Engine.app/Contents/MacOS'
    // --- TEMP SAMPLE KEY (replace with your real API key or use Jenkins credentials) ---
    KATALON_API_KEY = '6a9cb5b7-12f5-45fe-94ae-4dd40128278c'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Run Katalon Tests') {
      steps {
        sh '''
          set -euo pipefail
          echo "Workspace: ${WORKSPACE}"
          echo "Using KATALON_DIR: ${KATALON_DIR}"

          KATALON_EXEC="${KATALON_DIR}/katalonc"
          if [ ! -x "${KATALON_EXEC}" ]; then
            FOUND=$(find "${KATALON_DIR}" -maxdepth 1 -type f -perm -u=x 2>/dev/null | head -n 1 || true)
            if [ -n "${FOUND:-}" ]; then
              KATALON_EXEC="${FOUND}"
              echo "Found executable: ${KATALON_EXEC}"
            else
              echo "❌ No katalonc found in ${KATALON_DIR}"; exit 1
            fi
          fi

          # locate the project
          PRJ_FILE=$(find "${WORKSPACE}" -maxdepth 3 -type f -name '*.prj' | head -n 1 || true)
          if [ -z "${PRJ_FILE:-}" ]; then echo "❌ No .prj file found"; exit 3; fi
          echo "Using project: ${PRJ_FILE}"

          mkdir -p "${REPORT_DIR}"

          echo "🚀 Running Katalon using API key..."
          "${KATALON_EXEC}" -noSplash -runMode=console \
            -projectPath="${PRJ_FILE}" \
            -apiKey="${KATALON_API_KEY}" \
            -reportFolder="${REPORT_DIR}" \
            -reportFileName="katalon-report" \
            -browserType="Chrome"
        '''
      }
    }

    stage('Show Reports') {
      steps {
        sh 'ls -la ${REPORT_DIR} || echo "No Reports directory created."'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'Reports/**', allowEmptyArchive: true
      junit allowEmptyResults: true, testResults: 'Reports/**/*.xml'
      echo "Pipeline finished"
    }
  }
}
