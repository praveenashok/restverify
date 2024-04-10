pipeline {
    agent any
stages {
        stage('Trigger Downstream Jobs') {
            steps {
                script {
                    build(job: 'job1', propagate: false)
                    build(job: 'job2', propagate: false)
                    build(job: 'job3', propagate: false)
                }
            }
        }
    }
}
