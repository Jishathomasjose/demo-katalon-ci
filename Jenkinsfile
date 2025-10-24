pipeline {
  agent any

  environment {
    REPORT_DIR = 'Reports'
    // exact folder you provided
    KATALON_DIR = '/Users/jishathomas/Downloads/Katalon_Studio_Engine_MacOS-9.7.7/Katalon Studio Engine.app/Contents/MacOS'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Run Katalon (using provided MacOS app bundle path)') {
      steps {
        sh '''
          set -euo pipefail
          echo "Workspace: ${WORKSPACE}"
          echo "Using KATALON_DIR: ${KATALON_DIR}"

          # Prefer katalonc if present, otherwise pick the first executable inside KATALON_DIR
          KATALON_EXEC="${KATALON_DIR}/katalonc"
          if [ ! -x "${KATALON_EXEC}" ]; then
            echo "katalonc not found/executable at ${KATALON_EXEC}, searching for an executable in ${KATALON_DIR}..."
            # find first executable file in the directory (non-recursive)
            FOUND=$(find "${KATALON_DIR}" -maxdepth 1 -type f -perm -u=x 2>/dev/null | head -n 1 || true)
            if [ -n "${FOUND:-}" ]; then
              KATALON_EXEC="${FOUND}"
              echo "Found executable: ${KATALON_EXEC}"
            else
              echo "❌ No executable found in ${KATALON_DIR}."
              echo "List dir:"
              ls -la "${KATALON_DIR}" || true
              echo "Please ensure the Katalon Studio Engine app bundle is present and contains an executable inside Contents/MacOS."
              exit 1
            fi
          else
            echo "Using katalonc at ${KATALON_EXEC}"
          fi

          # make sure it's executable
          if [ ! -x "${KATALON_EXEC}" ]; then
            echo "Making ${KATALON_EXEC} executable..."
            chmod +x "${KATALON_EXEC}" || { echo "Failed to chmod +x ${KATALON_EXEC}"; exit 2; }
          fi

          # locate project file
          PRJ_FILE=$(find "${WORKSPACE}" -maxdepth 3 -type f -name '*.prj' | head -n 1 || true)
          if [ -z "${PRJ_FILE:-}" ]; then
            echo "❌ ERROR: No .prj file found in workspace (${WORKSPACE})"
            exit 3
          fi
          echo "Using project file: ${PRJ_FILE}"

          mkdir -p "${REPORT_DIR}"

          echo "Running Katalon engine: ${KATALON_EXEC}"
          "${KATALON_EXEC}" -noSplash -runMode=console -projectPath="${PRJ_FILE}" -reportFolder="${REPORT_DIR}" -reportFileName="katalon-report" -browserType="Chrome"
          echo "Katalon execution finished."
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
