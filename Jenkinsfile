pipeline {
    agent any
    
    environment {
        MY_SECRET = credentials('407907bd-ef21-45ce-94b2-3680739e98d1')
    }
    
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
                        archiveArtifacts artifacts: 'test-results/**', allowEmptyArchive: true 
                    }
                }
                stage('Node 12') {
                    steps {
                        echo "Running tests with Node.js version 12"
                        sh "npm install"
                        sh "npm run test --node=12"
                        archiveArtifacts artifacts: 'test-results/**', allowEmptyArchive: true
                    }
                }
                stage('Node 14') {
                    steps {
                        echo "Running tests with Node.js version 14"
                        sh "npm install"
                        sh "npm run test --node=14"
                        archiveArtifacts artifacts: 'test-results/**', allowEmptyArchive: true
                    }
                }
            }
        }
        stage('Static Code Analysis') {
            steps {
                sh 'npm install eslint'
                sh 'npm run eslint'
            }
        }
        stage('Test Coverage Report') {
            steps {
                sh 'mvn clean test jacoco:report'
            }
        }
        stage('Deploy') {
            //when {
            //    changeset "**/specific_folder/**"
            //}
            steps {
                script {
                    try {
                        sh './deploy_to_cloud.sh'
                        slackSend(channel: '#notifications', message: 'Deployment to production successful')
                    } catch (Exception e) {
                        echo 'Deployment failed. Rolling back...'
                        slackSend(channel: '#notifications', message: 'Deployment to production failed. Rollback initiated.')
                        currentBuild.result = 'FAILURE'
                        error("Rollback initiated")
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
