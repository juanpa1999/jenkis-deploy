pipeline {
    agent any

    stages {
        stage('Cleanup') {
            steps {
                script {
                    deleteDir() // Elimina todos los archivos en el workspace
                }
            }
        }

        stage('Checkout') {
            steps {
                sh 'git clone git@github.com:juanpa1999/Docker-_container_run.git'  // Reemplaza con tu repo
            }
        }

        stage('Delete images and containers') {
            steps {
                script {
                    sh "./Docker-_container_run/deleter.sh"
                }
            }
        }

        stage('Get tag variables') {
            steps {
                script {
                    sh "cd Docker-_container_run && ./tags.sh"
                }
            }
        }
        
    }
}
