pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('Dockerhub_credentials')  // ID de las credenciales en Jenkins
        IMAGE_FRONT = "jpcc141999/front_jenkis"  // Reemplaza con tu usuario y repo de Docker Hub
        IMAGE_BACK = "jpcc141999/back_jenkis"
        IMAGE_DB = "jpcc141999/db_jenkis"
    }

    stages {
        stage('Cleanup') {
            steps {
                script {
                    deleteDir() // Elimina todos los archivos en el workspace
                }
            }
        }

        stage('Set Variables') {
            steps {
                script {
                    def timestamp = new Date().format("yyyyMMdd-HHmmss", TimeZone.getTimeZone('UTC')) // Formato: 20240206-134500
                    env.IMAGE_TAG = "latest-${timestamp}"  // Concatenamos "latest" con el timestamp
                }
            }
        }

        stage('Checkout') {
            steps {
                sh 'git clone git@github.com:juanpa1999/amazon-test.git'  // Reemplaza con tu repo
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${IMAGE_FRONT}:${IMAGE_TAG} ./amazon-test/back"
                    sh "docker build -t ${IMAGE_BACK}:${IMAGE_TAG} ./amazon-test/front"
                    sh "docker build -t ${IMAGE_DB}:${IMAGE_TAG} ./amazon-test/db"
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                script {
                    sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    sh "docker push ${IMAGE_FRONT}:${IMAGE_TAG}"
                    sh "docker push ${IMAGE_BACK}:${IMAGE_TAG}"
                    sh "docker push ${IMAGE_DB}:${IMAGE_TAG}"
                }
            }
        }

        stage('Cleanup_final') {
            steps {
                script {
                    sh "docker rmi ${IMAGE_FRONT}:${IMAGE_TAG}"
                    sh "docker rmi ${IMAGE_BACK}:${IMAGE_TAG}"
                    sh "docker rmi ${IMAGE_DB}:${IMAGE_TAG}"
                }
            }
        }
    }
}
