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
                        name 'Browser'
                        values 'chrome', 'firefox'
                    }
                }
                stages {
                    stage('Test') {
                        steps {
                            echo "Running tests on ${Browser}"
                            sh "npm test --browser ${Browser}"
                        }
                    }
                }
            }
        }
    }
}
