pipeline {
  agent any
    stages{
        // git clone
        stage('Clone') {
            steps {
                checkout scm
            }
        }
        // docker image delete
        stage("Delete") {
            steps{
              sh(script: "docker rmi ${IMAGE_NAME}  || true")
              sh(script: 'docker rmi $(docker images -f "dangling=true" -q) || true')
            }
        }
        // docker build & push
        stage('Build'){
          steps{
                sh(script: "docker build -t ${HARBOR_USER}/${IMAGE_NAME}:latest .")
                withDockerRegistry(credentialsId: 'docker-hub', url: '') {
                  sh(script: "docker push ${HARBOR_USER}/${IMAGE_NAME}:latest")
              }
          }
        }
    }
}