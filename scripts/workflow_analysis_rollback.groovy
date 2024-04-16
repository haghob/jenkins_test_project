pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Static Code Analysis') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
        stage('Code Coverage') {
            steps {
                sh 'mvn jacoco:prepare-agent test jacoco:report'
            }
            post {
                always {
                    jacoco(execPattern: '**/target/jacoco.exec')
                    publishHTML(target: [reportDir: 'target/site/jacoco', reportFiles: 'index.html', reportName: 'JaCoCo Code Coverage Report'], keepAll: true)
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    try {
                        sh 'deploy.sh'
                    } catch (Exception e) {
                        echo "Rolling back deployment..."
                        sh 'rollback.sh'
                    }
                }
            }
        }
    }
}
