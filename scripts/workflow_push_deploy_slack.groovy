pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
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
                sh './deploy.sh'
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
