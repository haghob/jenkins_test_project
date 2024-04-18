pipeline {
    agent any
    
    environment {
        MY_SECRET = credentials('407907bd-ef21-45ce-94b2-3680739e98d1')
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo "Checking out the code from Git"
                git 'https://github.com/haghob/jenkins_test_project.git'
                echo "Code checkout complete"
            }
        }
        stage('Configure Backend') {
            steps {
                echo "Configuring Backend"
                sh "cd backend && npm install"
            }
        }
        stage('Run Backend') {
            steps {
                echo "Running Backend"
                sh "cd backend && npm start &"
            }
        }
        stage('Configure Frontend') {
            steps {
                echo "Configuring Frontend"
                sh "cd frontend && npm install"
            }
        }
        stage('Run Frontend') {
            steps {
                echo "Running Frontend"
                sh "cd frontend && npm start &"
            }
        }
        stage('Test') {
            steps {
                echo "Running tests"
                parallel (
                    "Node 10": {
                        sh "cd backend && npm run test --node=10"
                    },
                    "Node 12": {
                        sh "cd backend && npm run test --node=12"
                    },
                    "Node 14": {
                        sh "cd backend && npm run test --node=14"
                    }
                )
                sh 'cd backend && npm run eslint'
                sh 'cd backend && mvn clean test jacoco:report'
            }
        }
        stage('Deploy') {
            when {
                anyOf {
                    branch 'main'
                    changeRequest target: 'main'
                }
                expression {
                    return sh(script: "git diff --name-only HEAD HEAD~ | grep '^specific_folder/'", returnStdout: true).trim() != ""
                }
            }
            steps {
                script {
                    try {
                        sh 'cd backend && deploy_to_cloud.sh'
                        sh 'cd frontend && deploy_to_cloud.sh'
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
