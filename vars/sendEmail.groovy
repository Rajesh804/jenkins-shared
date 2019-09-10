#!/usr/bin/env groovy

def call(String buildStatus) {
  def status = buildStatus ?: 'SUCCESS'
  def color = '#e3e4e6'
  def statusMessage = status

  if (status == 'STARTED') {
    color = '#e3e4e6'
    statusMessage = 'Started'
  }
  if (status == 'SUCCESS') {
    color = 'good'
    statusMessage = 'Success'
  }
  if (status == 'FAILURE') {
    color = 'danger'
    statusMessage = 'FAILURE'
  }
  if (status == 'ABORTED') {
    color = 'warning'
    statusMessage = 'Aborted'
  }
  if (status == 'NOT_BUILT') {
    color = 'warning'
    statusMessage = 'Not built'
  }
  if (status == 'UNSTABLE') {
    color = 'danger'
    statusMessage = 'Unstable'
  }

  def body_message = "${env.JOB_NAME} <${env.BUILD_URL}|#${env.BUILD_NUMBER}> ${statusMessage}"
  def subject_message = "${env.JOB_NAME} - Build: #${env.BUILD_NUMBER} ${statusMessage}"


  emailext body: body_message,
  subject: subject_message,
  recipientProviders: [brokenTestsSuspects(), brokenBuildSuspects(), developers()],
  to: "devops@urjanet.com"

}
