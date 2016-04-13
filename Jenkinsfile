node {
  git url: 'http://gitlab.docker/nextbuild/service.git'

  stage 'clean'
  sh 'chmod 755 gradlew'
  sh './gradlew clean'
  
  stage 'Build'
  sh './gradlew assemble'

  stage 'Test'
  sh './gradlew check'


  stage 'QA'
  sh './gradlew sonarRunner'

}
