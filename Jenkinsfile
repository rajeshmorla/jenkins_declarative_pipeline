def run_required = true

pipeline {
  agent any

  stages{
    stage('Git Pull'){
      steps{
        git(url: 'https://github.com/rajeshmorla/jenkins_declarative_pipeline.git', branch: 'master')
      }
    }

    stage('Is Run Required ?')
    {
      steps {
        echo "Checking today is holiday or not!"
        script {
          Date now = new Date();
          year = now.getYear() + 1900;
          def response = httpRequest "https://calendarific.com/api/v2/holidays?&api_key=758f54db8c52c2b500c928282fe83af1b1aa2be8&country=IN&year=${year}"
          data = response.content
          today = new Date().format( 'yyyy-MM-dd' )
          println('today: '+today)

          def json_res = readJSON text: data
          holidays = json_res['response']['holidays']

          holidays.each{
            holiday = it['date']['iso']
            if (holiday.contains(today)) {
              println('Today: '+today+' is a holiday, skipping next stages...')
              run_required = false
            }
            else{
              println('Today: '+today+' is not a holiday, running next stages...')
            }
          }
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
              run_required
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
              run_required
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
              run_required
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
      steps{
        if (run_required) {
          if params.STATIC_CHECK {
            echo "Summary::: Stage Static_Check excecuted and file_1 has been copied."
          }
          else{
            echo "Summary::: Stage Static_Check is disabled and no files has been copied."
          }

          if params.QA {
            echo "Summary::: Stage QA excecuted and file_1 has been copied."
          }
          else{
            echo "Summary::: Stage QA is disabled and no files has been copied."
          }

          if params.UNIT_TEST {
            echo "Summary::: Stage UNIT_TEST excecuted and file_1 has been copied."
          }
          else{
            echo "Summary::: Stage UNIT_TEST is disabled and no files has been copied."
          }
        }
        else {
          if params.STATIC_CHECK {
            echo "Summary::: Stage Static_Check is enabled, but not excecuted due to holiday."
          }
          else{
            echo "Summary::: Stage Static_Check is disabled and no files has been copied."
          }

          if params.QA {
            echo "Summary::: Stage QA is enabled, but not excecuted due to holiday."
          }
          else{
            echo "Summary::: Stage QA is disabled and no files has been copied."
          }

          if params.UNIT_TEST {
            echo "Summary::: Stage UNIT_TEST is enabled, but not excecuted due to holiday."
          }
          else{
            echo "Summary::: Stage UNIT_TEST is disabled and no files has been copied."
          }
        }
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
        failure {
            echo 'Pipeline Failed !'
            mail to: 'rajeshmorla@live.com',
             subject: "Pipeline Failed: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
        }
    }
  
  parameters {
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
      defaultValue: "rajeshmorla@outlook.com"
    )
  }
}
