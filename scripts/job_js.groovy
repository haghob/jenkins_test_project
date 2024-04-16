pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Test') {
            matrix {
                axes {
                    axis {
                        name 'Node_Version'
                        values '10.x', '12.x', '14.x'
                    }
                }
                stages {
                    stage('Test') {
                        steps {
                            echo "Running tests with Node.js ${Node_Version}"
                            sh "node --version"
                            sh "npm install"
                            sh "npm test"
                        }
                    }
                }
            }
        }
    }
}
