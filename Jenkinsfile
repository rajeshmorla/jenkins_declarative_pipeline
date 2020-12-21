def holiday = false
def f_build
def f_static
def f_qa
def f_unittest

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

          def json_res = readJSON text: data
          holidays = json_res['response']['holidays']

          holidays.each{
            holiday = it['date']['iso']
            if (holiday.contains("wrongday")) {
              println('Today: '+today+' is a holiday, skipping next stages...')
              holiday = true
            }
          }
        }
      }
    }

    stage('Build')
    {
      when {
        expression {
          !holiday
        }
      }
      steps {
        script {
          def build_json = readJSON file: 'Build.json'
          build_json.each { key, value ->
              file_name = value['Name'] + '.txt'
              file_content = value['Content']

              writeFile file: file_name, text: file_content
          }

          fileOperations([folderCreateOperation('builds')])

          fileOperations([fileCopyOperation(
            includes: '*.txt', 
            excludes: '',
            targetLocation: 'builds',
            flattenFiles: false
            )])

          fileOperations([fileZipOperation(folderPath: 'builds', outputFolderPath: '.')])
          
          f_build = findFiles(glob: '*.txt')

        }
      }
    }

    stage('Static Check')
    {
      when {
        expression {
          holiday
        }
      }
      steps {
        script {
          if (params.STATIC_CHECK) {
            echo "Running static checks..."
            script {
              fileOperations([folderCreateOperation('Static_Check')])
              fileOperations([fileUnZipOperation(filePath: 'builds.zip', targetLocation: 'static_check_extract')])
              fileOperations([fileCopyOperation(
                includes: '*Static_Check.txt', 
                excludes: '',
                targetLocation: 'Static_Check',
                flattenFiles: false
                )])
              f_static = 'Static_Check.txt'
            }
          }
          else{
            echo "Summary::: Stage Static_Check is disabled."
          }
        }
      }
    }

    stage('QA')
    {
      when {
        expression {
          holiday
        }
      }
      steps {
        script {
          if (params.QA) {
            echo "Running QA..."
            script {
              fileOperations([folderCreateOperation('QA')])
              fileOperations([fileUnZipOperation(filePath: 'builds.zip', targetLocation: 'qa_extract')])
              fileOperations([fileCopyOperation(
                includes: '*QA.txt', 
                excludes: '',
                targetLocation: 'QA',
                flattenFiles: false
                )])
              
              f_qa = 'QA.txt'
            }
          }
          else{
            echo "Summary::: Stage QA is disabled."
          }
        }
      }
    }

    stage('Unit Test')
    {
      when {
        expression {
          holiday
        }
      }
      steps {
        script {
          if (params.UNIT_TEST) {
            echo "Running UNIT_TEST..."
            script {
              fileOperations([folderCreateOperation('UNIT_TEST')])
              fileOperations([fileUnZipOperation(filePath: 'builds.zip', targetLocation: 'unit_test_extract')])
              fileOperations([fileCopyOperation(
                includes: '*UNIT_TEST.txt', 
                excludes: '',
                targetLocation: 'UNIT_TEST',
                flattenFiles: false
                )])
              
              f_unittest = 'UNIT_TEST.txt'
            }
          }
          else{
            echo "Summary::: Stage UNIT_TEST is disabled."
          }
        }
      }
    }  

    stage('Summary')
    {
      steps{
        script {
          if (holiday) {
            if (params.STATIC_CHECK) {
              echo "Summary::: Stage Static_Check excecuted and "+f_static+" has been copied."
            }
            else{
              echo "Summary::: Stage Static_Check is disabled and no files has been copied."
            }

            if (params.QA) {
              echo "Summary::: Stage QA excecuted and file_1 has been copied."
            }
            else{
              echo "Summary::: Stage QA is disabled and no files has been copied."
            }

            if (params.UNIT_TEST) {
              echo "Summary::: Stage UNIT_TEST excecuted and file_1 has been copied."
            }
            else{
              echo "Summary::: Stage UNIT_TEST is disabled and no files has been copied."
            }
          }
          else {
            if (params.STATIC_CHECK) {
              echo "Summary::: Stage Static_Check is enabled, but not excecuted due to holiday."
            }
            else{
              echo "Summary::: Stage Static_Check is disabled and no files has been copied."
            }

            if (params.QA) {
              echo "Summary::: Stage QA is enabled, but not excecuted due to holiday."
            }
            else{
              echo "Summary::: Stage QA is disabled and no files has been copied."
            }

            if (params.UNIT_TEST) {
              echo "Summary::: Stage UNIT_TEST is enabled, but not excecuted due to holiday."
            }
            else{
              echo "Summary::: Stage UNIT_TEST is disabled and no files has been copied."
            }
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
