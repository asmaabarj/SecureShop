pipeline {
    agent any

    tools {
        maven "Maven"
    }

    environment {
        DOCKER_HUB_REPO = 'asmaaba/secureshop'
    }

    stages {
        stage('Clone Repo') {
            steps {
                cleanWs()
                git branch: 'main', url: 'https://github.com/asmaabarj/SecureShop'
            }
        }

        stage('Build Artifact') {
            steps {
                bat 'cd'
                bat 'dir'
                bat '''
                    cd secureshop
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('Prepare Docker Build') {
            steps {
                script {
                    // Créer le Dockerfile s'il n'existe pas
                    writeFile file: 'Dockerfile', text: '''FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY secureshop/target/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]'''
                }
            }
        }

        stage('Docker Build') {
            steps {
                bat "docker build -t ${DOCKER_HUB_REPO}:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-token', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    bat "docker push ${DOCKER_HUB_REPO}:latest"
                }
            }
        }
    }

    post {
        always {
            bat 'docker logout'
        }
    }
}