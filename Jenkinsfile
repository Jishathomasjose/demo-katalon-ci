pipeline {
  agent any

  environment {
    KATALON_DIR = '/Users/jishathomas/Downloads/Katalon_Studio_Engine_MacOS-9.7.7/Katalon Studio Engine.app/Contents/MacOS'
    PROJECT_FILE = 'DemoQA_TestProject.prj'
    TEST_SUITE_PATH = 'Test Suites/TS_SmokeTests'    // 👈 change to your test suite name
    REPORT_DIR = 'Reports'
    KATALON_API_KEY = '6a9cb5b7-12f5-45fe-94ae-4dd40128278c'       // 👈 replace with your real Katalon API key
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Run Katalon Test Suite') {
      steps {
        sh '''
          set -e

          KATALON_EXEC="${KATALON_DIR}/katalonc"
          PROJECT_PATH="${WORKSPACE}/${PROJECT_FILE}"

          echo "Running Katalon test suite: ${TEST_SUITE_PATH}"
          "${KATALON_EXEC}" -noSplash -runMode=console \
            -projectPath="${PROJECT_PATH}" \
            -testSuitePath="${TEST_SUITE_PATH}" \
            -browserType="Chrome (headless)" \
            -apiKey="${KATALON_API_KEY}" \
            -reportFolder="${REPORT_DIR}" \
            -reportFileName="katalon-report"
        '''
      }
    }

    stage('Show Reports') {
      steps {
        sh 'ls -la ${REPORT_DIR} || echo "No Reports generated."'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'Reports/**', allowEmptyArchive: true
      junit allowEmptyResults: true, testResults: 'Reports/**/*.xml'
      echo "✅ Katalon pipeline finished."
    }
  }
}
