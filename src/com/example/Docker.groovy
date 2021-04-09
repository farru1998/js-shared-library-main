#!/usr/bin/env groovy
package com.example

class Docker implements Serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def buildDockerImage(String imageName) {
        script.withCredentials([script.usernamePassword(credentialsId: 'docker-credential', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
            script.docker.withRegistry('https://registry.hub.docker.com', 'docker-credential') {
                //script.docker.build("$imageName", '.').push()
                script{
                    docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
    }

    def dockerLogin() {
        script.withCredentials([script.usernamePassword(credentialsId: 'docker-credential', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
            script.sh "echo $script.PASS | docker login -u $script.USER --password-stdin"
        }
    }

    def dockerPush(String imageName) {
        script.sh "docker push $imageName"
    }

}

