pipeline {
  agent any
    stages{
        // git clone
        stage('Clone') {
            steps {
                checkout scm
            }
        }
        stage("Delete") {
            steps{
              sh(script: "docker rmi ${IMAGE_NAME}  || true")
              sh(script: 'docker rmi $(docker images -f "dangling=true" -q) || true')
            }
        }

        stage('Build'){
          steps{
                // sh(script: "chmod +x gradlew")
                // sh(script: "./gradlew clean bootBuildImage --imageName=${IMAGE_NAME}")
                sh(script: "docker build -t ${IMAGE_NAME}:latest .")
          }
        }
        stage('push') {
          steps{
             // This step should not normally be used in your script. Consult the inline help for details.
              withDockerRegistry(credentialsId: 'docker-hub', url: '') {
                  // some block
                  sh(script: "docker push ${IMAGE_NAME}:latest")
              }
          }
        }
        stage('Tag'){
          steps{
            sh(script: "docker tag ${IMAGE_NAME}:latest ${HARBOR_URL}/${HARBOR_USER}/${IMAGE_NAME}:latest")
          }
        }
        stage('Login'){
           steps{
             sh("echo ${HARBOR_PWD} | docker login -u ${HARBOR_USER} --password-stdin")// docker hub 로그인
           }
        }
        stage('Deploy') {
            steps {
                script {
                  sh(script: "docker push ${HARBOR_USER}/${IMAGE_NAME}:latest") //docker push
                }
            }
        }
        stage("Docker Pushed Image delete") {
            steps{
              sh(script: "docker rmi ${HARBOR_URL}/${HARBOR_PROJECT}/${IMAGE_NAME}:latest || true")
            }
        }
    }
}