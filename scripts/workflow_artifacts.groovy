pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh 'npm install'
                sh 'npm run build'
            }
        }
        stage('Package') {
            steps {
                // création du répertoire d'artefacts et y copier les fichiers de build
                dir('artifacts') {
                    sh 'cp -r path/to/build/* .'
                }
                archiveArtifacts 'artifacts/*'
            }
        }
        stage('Deploy Artifact') {
            steps {
                build job: 'deploy', parameters: [[$class: 'ArtifactParameterValue', name: 'ARTIFACT', value: 'artifacts/*']]
            }
        }
    }
}
