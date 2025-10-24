pipeline {
  agent any

  environment {
    REPORT_DIR = 'Reports'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Run Katalon Tests') {
      steps {
        sh '''
          set -euo pipefail
          echo "=== Jenkins workspace ==="
          echo "Workspace: ${WORKSPACE}"
          echo "Home: ${HOME}"
          echo "User: $(whoami)"
          echo "Looking for katalonc in ~/Downloads/Katalon_Studio_Engine/MacOS"

          KATALON_EXEC="${HOME}/Downloads/Katalon_Studio_Engine/MacOS/katalonc"

          if [ ! -f "${KATALON_EXEC}" ]; then
            echo "❌ ERROR: katalonc not found at ${KATALON_EXEC}"
            echo "Please place katalonc at ~/Downloads/Katalon_Studio_Engine/MacOS/katalonc"
            exit 1
          fi

          if [ ! -x "${KATALON_EXEC}" ]; then
            echo "⚙️  Making katalonc executable..."
            chmod +x "${KATALON_EXEC}" || { echo "Failed to chmod +x katalonc"; exit 2; }
          fi

          echo "✅ Found katalonc at ${KATALON_EXEC}"

          # find the project file (.prj)
          PRJ_FILE=$(find "${WORKSPACE}" -maxdepth 3 -type f -name '*.prj' | head -n 1 || true)
          if [ -z "${PRJ_FILE:-}" ]; then
            echo "❌ ERROR: No .prj file found in workspace."
            exit 3
          fi

          echo "✅ Found project file: ${PRJ_FILE}"

          mkdir -p "${REPORT_DIR}"

          echo "🚀 Running Katalon tests..."
          "${KATALON_EXEC}" -noSplash -runMode=console \
            -projectPath="${PRJ_FILE}" \
            -reportFolder="${REPORT_DIR}" \
            -reportFileName="katalon-report" \
            -browserType="Chrome"

          echo "✅ Katalon test execution completed."
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
      echo "📦 Pipeline finished. Reports archived if present."
    }
  }
}
