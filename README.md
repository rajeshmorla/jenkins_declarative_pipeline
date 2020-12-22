# jenkins_declarative_pipeline (MB_Jenkins CICD): Introduction
A sample jenkins project using the pipeline scripts in declarative format.

# Getting Started
TODO: Guide users through getting your code up and running on their own system. In this section you can talk about:
1.	Prerequisites
2.	Installations steps
3.	Demo (remote access)
4.	Better approches (future implementaion)

# Prerequisites:

1.      Jenkins (free version) installation in any local/remote environment.

2.      Git installation in any local/remote environment

3.      A Github or any other repository to which the Git is attached so that Git Pull can be made in the pipeline script.

4.      Pipeline plugin need to be installed to write the pipeline as script configuration.

5.      A tool or plugin to make REST calls which is part of Question 1 in the assignment.

6.      Blue ocean plugin to view the output of the pipeline execution. (optional: for better usage view only)

7.      Addtional plugins: Email Extension Plugin, File Operations Plugin, Folders Plugin, HTTP Request Plugin and Plugin Utilities API Plugin


# Installation steps
After successfull installtion of above prerequisites, follow the below steps:
1. Clone this repo to you personal repos
2. Open in Blue Ocean: Go jenkins dashboard -> "Open Blue Ocean" (Left menu)
3. Click on "New Pipeline" (Top right corner), choose code base (I used GitHub for this project and authorized for jenkins)
4. After successfull authentication and authorization from github, you will see all your repos in the list. Select previously cloned repo (from step 1)
5. Select the repo and click on "Create pipeline", A Blue ocean pipeline will automatically look for jenkinsfile fomr your repo and starts creating pipeline for you.

Note: If any failures, kindly check reqspective plugins are installed properly or not. Still need support ? Use comments section for quick responses.

# Demo (Live jenkins pipeline deplayed in AWS Cloud)
1. To access the live server and look into wokring pipeline, drop an email to 'rajeshmorla@live.com', I'll share you server details with jenkins credentials
2. URL: http://ec2-65-0-252-1.ap-south-1.compute.amazonaws.com:8080/jenkins/login
3. Jenkins username: <*****>
4. Jenkins password: <*******>


# Output: The final pipeline view in Blue Ocean:

![alt text](https://github.com/rajeshmorla/jenkins_declarative_pipeline/blob/master/img/blu_ocean_view.JPG)
