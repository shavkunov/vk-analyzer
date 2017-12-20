pipeline {

    agent {
        docker {
            image 'maven:3.5.2-jdk-8-slim'
        }
    }

    stages {
        stage('Build') { 
            steps {
                sh 'mvn install' 
            }
        }
    }

}