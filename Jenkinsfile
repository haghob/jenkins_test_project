pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/haghob/jenkins_test_project.git'
            }
        }
        stage('Build') {
            steps {
                sh 'npm install'
            }
        }
        stage('Test') {
            steps {
                sh 'npm test'
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
                    } else {
                        echo 'Tests failed on production branch. Skipping deployment.'
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
