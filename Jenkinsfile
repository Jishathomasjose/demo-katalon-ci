pipeline {
  agent any

  environment {
    KATALON_PATH = "/Downloads/Katalon_Studio_Engine/katalonc"
    PROJECT_PATH = "${WORKSPACE}/DemoQA_MacProject.prj"
    TEST_SUITE_PATH = "Test Suites/TS_SmokeTests"
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
        ${KATALON_PATH} -noSplash -runMode=console \
        -projectPath="${PROJECT_PATH}" \
        -retry=0 -testSuitePath="${TEST_SUITE_PATH}" \
        -executionProfile="default" -browserType="Chrome (headless)"
        '''
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'Reports/**', allowEmptyArchive: true
      junit 'Reports/**/JUnit_Report.xml'
    }
  }
}
