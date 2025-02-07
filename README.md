# Jenkins in a Docker Container

> ![Docker-in-Docker Setup](https://camo.githubusercontent.com/0ecb21c2d2c2dabf45a825e7bcd2151a3ade2a942aa1e55cccec03d77fd1095f/68747470733a2f2f6a656e6b696e732e696f2f73697465732f64656661756c742f66696c65732f6a656e6b696e735f6c6f676f2e706e67)

> ![Jenkins UI](https://www.stackhero.io/assets/src/images/servicesLogos/openGraphVersions/docker.png?d87f4381)

This repository contains the necessary files to deploy Jenkins inside a Docker container. It uses a customized Dockerfile to interact with the host machine's Docker socket.

## Features
- **Custom Dockerfile**: Allows interaction with the Docker daemon from inside the container.
- **Automated Installation**: The `install.sh` script installs Docker and configures it for non-root usage.
- **Docker-in-Docker Setup**: The Docker daemon of the host machine is accessible from the container.
- **Utility Scripts**: Includes scripts for easy setup, cleanup, and swap memory creation.

## Prerequisites
Before deploying Jenkins, ensure you have:
- A Linux-based host machine (Debian-based recommended)
- Docker installed (if not, run `install.sh`)

## Installation Steps

1. **Run the Installation Script**
   ```bash
   chmod +x install.sh
   ./install.sh
   ```
   This script installs Docker and grants permission to interact with the Docker daemon without requiring root privileges.

2. **Build and Run Jenkins**
   ```bash
   docker-compose up -d
   ```
   This will build the custom Jenkins image and start the container in detached mode.

## Understanding the Configuration

### Dockerfile
Inside the `Dockerfile`, Docker is installed to allow the container to manage other containers. However, to ensure it interacts with the host's Docker daemon, we map the Docker socket.

### docker-compose.yml
In **line #18** of the `docker-compose.yml` file, the volume `/var/run/docker.sock:/var/run/docker.sock` is mounted to allow the Jenkins container to communicate with the host's Docker daemon.

## Utility Scripts

### deleter.sh
This script helps remove Docker containers and images, making it easier to clean up and reinstall Jenkins.
```bash
chmod +x deleter.sh
./deleter.sh
```

### swap_creation.sh
This script creates a **2GB swap memory** on a Debian-based Linux system to improve performance.
```bash
chmod +x swap_creation.sh
./swap_creation.sh
```

## Important Notes
- Review the comments inside each script before execution to modify them according to your server and project requirements.
- Ensure that your user has the necessary permissions to execute Docker commands.
- Check logs with:
  ```bash
  docker logs -f jenkins
  ```


## Contributing
Feel free to submit pull requests for improvements or bug fixes.

---

**Maintainer**: Pablo Castiblanco

