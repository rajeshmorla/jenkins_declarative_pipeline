pipeline {
  agent any

  stages{
    stage('Git Pull'){
      steps{
        git(url: 'https://github.com/rajeshmorla/jenkins_declarative_pipeline.git', branch: 'master')
      }
    }

    stage('Build')
    {
      when {
        expression {
          params.RUN_REQUIRED
        }
      }
      steps {
        echo "Run is required, further stages should execute!"
        script {
          Date now = new Date();
          year = now.getYear() + 1900;
          println('year: '+year)
          def response = httpRequest "https://calendarific.com/api/v2/holidays?&api_key=758f54db8c52c2b500c928282fe83af1b1aa2be8&country=IN&year=${year}"
          data = response.content
          
          println('Status: '+response.status)
          println('Response data: '+data)
          
          today = new Date().format( 'yyyy-MM-dd' )
          println('today: '+today)

          def props = readJSON text: data

          println('props: '+props)
          
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
}
