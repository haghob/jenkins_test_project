pipeline {
    agent any
    
    environment {
        SECRET_KEY = credentials('secret-key-id')
        API_TOKEN = credentials('api-token-id')
    }
    
    stages {
        stage('Build') {
            steps {
                sh "echo ${SECRET_KEY}"
                sh "echo ${API_TOKEN}"
            }
        }
    }
}
