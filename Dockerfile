
# Jenkins customized image built for devops project
# Author Hana Ghorbel
#
# Usage with docker
#   (Build) docker build -t jenkins-cust .
#   (Run) docker run --rm --name myjenkins -p 8080:8080 -p 50000:50000 jenkins-cust


###############################################

FROM jenkins/jenkins:lts-jdk11
LABEL author="Hana Ghorbel <hana.ghorbel@ynov.com>"
LABEL maintainer="Hana Ghorbel <hana.ghorbel@ynov.com>"

###############################################

# Disable setup wizard
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false -Dpermissive-script-security.enabled=true
# ENV JENKINS_USER hana
# ENV JENKINS_PASS Th3Jenkins!!!

USER root

#################################################

# Docker and dependencies intsall
RUN apt-get update \
  && apt-get -y install \
  apt-transport-https \
  ca-certificates \
  curl \
  gnupg2 \
  software-properties-common \
  && curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey \
  && add-apt-repository \
  "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
  $(lsb_release -cs) \
  stable" \
  && apt-get update \
  && apt-get -y install \
  docker-ce \
  && rm -rf /var/lib/apt/lists/*

#################################################

RUN apt-get update && apt-get install -y \
  wget \
  unzip \
  graphviz* \
  && rm -rf /var/lib/apt/lists/*


#################################################

USER jenkins

# Load plugins w jenkins-plugin-cli
COPY --chown=jenkins:jenkins conf/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

# Jenkins runs all grovy files from init.groovy.d dir
##COPY --chown=jenkins:jenkins conf/scripts/admin-user.groovy /usr/share/jenkins/ref/init.groovy.d/
##VOLUME /var/jenkins_home
