pipeline {
    agent any
    stages {
        stage('Trigger first Job') {
            steps {
                build(job: 'gittriggerjenkinsjob', parameters: [
                    string(name: 'TAG_NAME', value: 'v0.0')
                ])
            }
        }stage('Trigger Downstream Job') {
            steps {
                build(job: 'job1', parameters: [
                    string(name: 'TAG_NAME', value: 'v1.0')
                ])
            }
        }
        stage('Trigger Downstream Job') {
            steps {
                build(job: 'job2', parameters: [
                    string(name: 'TAG_NAME', value: 'v1.1')
                ])
            }
        }
        stage('Trigger Downstream Job') {
            steps {
                build(job: 'job3', parameters: [
                    string(name: 'TAG_NAME', value: 'v1.2')
                ])
            }
        }
    }
}
