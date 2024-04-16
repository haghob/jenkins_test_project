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
                    axis {
                        name 'Node_Version'
                        values '10.x', '12.x', '14.x'
                    }
                }
                stages {
                    stage('Run Tests') {
                        steps {
                            echo "Running tests on ${Browser} with Node.js ${Node_Version}"
                            sh "node --version"
                            sh "npm install"
                            sh "npm test --browser ${Browser}"
                        }
                    }
                }
            }
        }
    }
}
