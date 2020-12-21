def gv

pipeline {

  agent any

  parameters {
    booleanParam(
      name: "RUN_REQUIRED", 
      defaultValue: true
    )

    booleanParam(
      name: "STATIC_CHECK", 
      defaultValue: true
    )

    booleanParam(
      name: "QA", 
      defaultValue: true
    )

    booleanParam(
      name: "UNIT_TEST", 
      defaultValue: true
    )

    string(
      name: "SUCCESS_EMAIL",
      defaultValue: "rajeshmorla@live.com"
    )

    string(
      name: "FAILURE_EMAIL",
      defaultValue: "rajeshmorla@live.com"
    )
  }

  stages{
    stage('Git Pull'){
      steps{
        git(url: 'https://github.com/rajeshmorla/jenkins_declarative_pipeline.git', branch: 'master')
      }
    }

    stage('Is Run Required ?')
    {
      when {
        expression {
          params.RUN_REQUIRED
        }
      }
      steps {
        echo "Run is required, further stages should execute!"
        script {
          gv = load "script.groovy"
        }
      }
    }

    stage('Quality')
    {
      parallel {
        stage('Static Check')
        {
          when {
            expression {
              params.RUN_REQUIRED
            }
          }
          steps {
            echo "Run is required, proceeding with stage: Static Check..."
          }
        }

        stage('QA')
        {
          when {
            expression {
              params.RUN_REQUIRED
            }
          }
          steps {
            echo "Run is required, proceeding with stage: QA..."
          }
        }

        stage('Unit Test')
        {
          when {
            expression {
              params.RUN_REQUIRED
            }
          }
          steps {
            echo "Run is required, proceeding with stage: QA..."
          }
        }
      }
    }    

    stage('Summary')
    {
      when {
        expression {
          params.RUN_REQUIRED
        }
      }
      steps {
        echo "Printing all stages executed:"
      }
    }
  }

  post {
        success {
            echo 'Pipeline succeeded!'
            mail to: 'rajeshmorla@live.com',
             subject: "Pipeline Success: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'Pipeline Failed !'
            mail to: 'rajeshmorla@live.com',
             subject: "Pipeline Failed: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
        }
    }
}
