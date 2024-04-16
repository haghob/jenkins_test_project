pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh 'echo "building the application..."'
            }
        }
        stage('Test') {
            steps {
                sh 'echo "running tests..."'
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo "deploying the application..."'
            }
        }
    }
}
