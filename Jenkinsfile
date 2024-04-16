pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/haghob/jenkins_test_project.git'
            }
        }
        stage('Build and Test') {
            parallel {
                stage('Node 10') {
                    steps {
                        echo "Running tests with Node.js version 10"
                        sh "npm install"
                        sh "npm run test --node=10"
                    }
                }
                stage('Node 12') {
                    steps {
                        echo "Running tests with Node.js version 12"
                        sh "npm install"
                        sh "npm run test --node=12"
                    }
                }
                stage('Node 14') {
                    steps {
                        echo "Running tests with Node.js version 14"
                        sh "npm install"
                        sh "npm run test --node=14"
                    }
                }
            }
        }
        stage('Deploy') {
            when {
                branch 'production'
            }
            steps {
                script {
                    if (currentBuild.result == 'SUCCESS') {
                        sh './deploy_to_cloud.sh'
                        slackSend(channel: '#notifications', message: 'Deployment to production successful')
                    } else {
                        echo 'Tests failed on production branch. Skipping deployment.'
                        slackSend(channel: '#notifications', message: 'Deployment to production failed')
                    }
                }
            }
            post {
                success {
                    slackSend(channel: '#notifications', message: 'Deployment successful')
                }
                failure {
                    slackSend(channel: '#notifications', message: 'Deployment failed')
                }
            }
        }
    }
}
