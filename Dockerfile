FROM jenkins/jenkins:2.479.1-jdk17

USER root

# Crear el directorio .ssh para Jenkins y asignar permisos
RUN mkdir -p /var/jenkins_home/.ssh && chown -R jenkins:jenkins /var/jenkins_home/.ssh

# Instalar dependencias necesarias
RUN apt-get update && apt-get install -y lsb-release curl gnupg

# Agregar la llave GPG de Docker
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg

# Agregar el repositorio oficial de Docker
RUN echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list

# Instalar Docker CLI y el demonio de Docker
RUN apt-get update && apt-get install -y docker-ce-cli docker-ce docker-compose-plugin

# Cambiar de nuevo al usuario Jenkins
USER jenkins

# Instalar los plugins de Jenkins necesarios
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"

USER root

RUN usermod -aG docker jenkins


