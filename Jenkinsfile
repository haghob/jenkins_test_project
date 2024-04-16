pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/haghob/jenkins_test_project.git'
            }
        }
        stage('Build') {
            matrix {
                axes {
                    axis {
                        name 'VERSION'
                        values '10', '12', '14'
                    }
                }
                stages {
                    stage('Build and Test') {
                        steps {
                            echo "Running tests with Node.js version ${VERSION}"
                            sh "npm install"
                            sh "npm run test --node=${VERSION}"
                        }
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
