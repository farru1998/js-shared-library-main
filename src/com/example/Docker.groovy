#!/usr/bin/env groovy
package com.example

class Docker implements Serializable {

    def script

    def upStreamBuild = Jenkins.getInstance().getItemByFullName(item.fullName).getBuildByNumber(last_job_num)
    
    Docker(script) {
        this.script = script
    }

    def buildDockerImage(String imageName) {
        script.withCredentials([script.usernamePassword(credentialsId: 'docker-credential', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
            script.docker.withRegistry('https://registry.hub.docker.com', 'docker-credential') {
                script.docker.build("$imageName:env.BUILD_NUMBER",'.').push()
                // dockerApp = dockerLib.buildDockerImage(IMAGE_NAME, "", "${workspace}/${DOCKERFILE_PATH}", imageTagsList[0], buildArgs)
            }
        }
    }

    def dockerLogin() {
        script.withCredentials([script.usernamePassword(credentialsId: 'docker-credential', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
            script.sh "echo $script.PASS | docker login -u $script.USER --password-stdin"
        }
    }

    //def dockerPush(String imageName) {
      //  script.sh "docker push $imageName -t $BUILD_NUMBER"
    //}

}

