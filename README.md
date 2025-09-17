# Task Manager - Task Management

![JAVA_BADGE](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![QUARKUS_BADGE](https://img.shields.io/badge/quarkus-%234695eb.svg?style=for-the-badge&logo=quarkus&logoColor=white)
![AWS_BADGE](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![DOCKER_COMPOSE](https://img.shields.io/badge/Docker%20Compose-%231d63ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![TERRAFORM](https://img.shields.io/badge/Terraform-%23623ce4.svg?style=for-the-badge&logo=terraform&logoColor=white)

This project is a task management API, built with Java 21 and Quarkus. The API provides a simple and efficient way to create, manage, and track tasks with a clean RESTful interface.

---

## 🚀 Features

- Task Management: Complete CRUD operations for creating, reading, updating, and deleting tasks.
- RESTful API: Clean and intuitive REST endpoints following best practices.
- API Documentation: Interactive API exploration and documentation provided by Swagger UI (OpenAPI).
- Database Integration: PostgreSQL database integration for reliable data persistence.
- Containerization: Ready to deploy using Docker, with optimized container configuration.
- Cloud-Ready: Full AWS deployment infrastructure using Terraform with ECS Fargate, RDS, and Application Load Balancer.

## 🏗️ Installation

To use this project, you need to follow these steps:

1. Clone the repository: `git clone https://github.com/gustavobarez/task-manager.git`
2. Install the dependencies: `mvn clean package`
3. Run the application: `mvn quarkus:dev`

## ⚙️ Makefile Commands

The project includes a Makefile to help you manage common tasks more easily. Here's a list of the available commands and a brief description of what they do:

### Local Development

- `make run`: Run the application locally
- `make build`: Build the application and package a JAR
- `make test`: Run tests for all packages in the project.
- `make docs`: Generate Swagger API documentation
- `make clean`: Clean project build artifacts

### Docker Commands

- `make docker-build`: Build the Docker image for the application
- `make docker-run`: Run the application in a Docker container
- `make docker-stop`: Stop and remove all containers defined in docker-compose.yml

### AWS Deployment Commands

- `make ecr-login`: Authenticate Docker with AWS ECR
- `make ecr-push`: Build and push Docker image to ECR repository
- `make deploy-infra`: Deploy AWS infrastructure using Terraform
- `make deploy-app`: Update ECS service with new application version
- `make deploy`: Complete deployment (infrastructure + application)

### Monitoring and Management

- `make logs`: View real-time logs from ECS containers
- `make status`: Check ECS service status
- `make destroy`: Destroy all AWS infrastructure

To use these commands, simply type `make` followed by the desired command in your terminal. For example:

```sh
make run
```

## 🐳 Docker and Docker Compose

This project includes a `Dockerfile` and `docker-compose.yml` file for easy containerization and deployment. Here are the most common Docker and Docker Compose commands you may want to use:

- `docker build -t your-image-name .`: Build a Docker image for the project. Replace `your-image-name` with a name for your image.
- `docker run -p 8080:8080 -e PORT=8080 your-image-name`: Run a container based on the built image. Replace `your-image-name` with the name you used when building the image. You can change the port number if necessary.

If you want to use Docker Compose, follow these commands:

- `docker compose build`: Build the services defined in the `docker-compose.yml` file.
- `docker compose up`: Run the services defined in the `docker-compose.yml` file.

To stop and remove containers, networks, and volumes defined in the `docker-compose.yml` file, run:

```sh
docker-compose down
```

For more information on Docker and Docker Compose, refer to the official documentation:

- [Docker](https://docs.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## ☁️ Terraform

This project uses Terraform to manage infrastructure as code (IaC). The configuration files define the necessary cloud resources to run the application. To get started, navigate to the `terraform` directory and run the following commands:

- `terraform init`: Initializes the working directory by downloading the required providers and modules. This command should be run first.
- `terraform plan`: Creates an execution plan, allowing you to preview the changes Terraform will make to your infrastructure before applying them.
- `terraform apply`: Applies the changes to create, update, or delete resources according to the configuration files.
- `terraform destroy`: Removes all the resources managed by the Terraform configuration. Use this command with caution.

Here is the typical workflow:

```sh
cd terraform/
terraform init
terraform plan
terraform apply
```

## 🛠️ Used Tools

This project uses the following tools:

- [Java](https://docs.oracle.com/en/java/javase/21/) for backend development
- [Quarkus](https://quarkus.io/guides/#) framework for building APIs
- [Docker](https://docs.docker.com/) for containerization
- [Terraform](https://developer.hashicorp.com/terraform/docs) for IAC
- [Swagger](https://swagger.io/) for API documentation and testing

## 💻 Usage

After the API is running, you can use the Swagger UI to interact with the available endpoints for tasks. The API can be accessed at `http://localhost:$PORT/swagger-ui`.

Default $PORT if not provided=8080.

## 🤝 Contributing

To contribute to this project, please follow these guidelines:

1. Fork the repository
2. Create a new branch: `git checkout -b feature/your-feature-name`
3. Make your changes and commit them using Conventional Commits
4. Push to the branch: `git push origin feature/your-feature-name`
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## ❤️ Credits

This project was created by [Gustavo Barez](https://github.com/gustavobarez).