pipeline {
  agent any

  environment {
    REPORT_DIR = 'Reports'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Run Katalon (from ~/Downloads)') {
      steps {
        sh '''
          set -euo pipefail
          echo "Running on: $(whoami) @ $(hostname)"
          echo "Workspace: ${WORKSPACE}"
          pwd
          ls -la

          KATALON_EXEC="${HOME}/Downloads/Katalon_Studio_Engine/katalonc"

          if [ ! -f "${KATALON_EXEC}" ]; then
            echo "ERROR: katalonc not found at ${KATALON_EXEC}"
            echo "Please place katalonc at ~/Downloads/Katalon_Studio_Engine/katalonc"
            exit 1
          fi

          if [ ! -x "${KATALON_EXEC}" ]; then
            echo "ERROR: katalonc found but not executable. Setting +x..."
            chmod +x "${KATALON_EXEC}" || { echo "Failed to chmod +x"; exit 2; }
          fi

          PRJ_FILE=$(find "${WORKSPACE}" -maxdepth 4 -type f -name '*.prj' | head -n 1 || true)
          if [ -z "${PRJ_FILE:-}" ]; then
            echo "ERROR: No .prj file found in workspace (${WORKSPACE})"
            exit 3
          fi
          echo "Using project: ${PRJ_FILE}"

          mkdir -p "${REPORT_DIR}"
          "${KATALON_EXEC}" -noSplash -runMode=console -projectPath="${PRJ_FILE}" -reportFolder="${REPORT_DIR}" -reportFileName="katalon-report" -browserType="Chrome"
        '''
      }
    }

    stage('Show Reports (if any)') {
      steps {
        sh 'ls -la ${REPORT_DIR} || echo "No Reports directory"'
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
