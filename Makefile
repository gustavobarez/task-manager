.PHONY: default run build test docker-build docker-run ecr-login ecr-push deploy-infra deploy-app deploy destroy clean

default: run

# Desenvolvimento local
run:
	./mvnw quarkus:dev

build:
	./mvnw clean package -DskipTests

test:
	./mvnw test

# Docker local
docker-build:
	./mvnw package -Dquarkus.package.type=uber-jar
	docker build -f src/main/docker/Dockerfile.jvm -t taskmanager .

docker-run:
	docker-compose up -d

docker-stop:
	docker-compose down

# ECR e AWS
ecr-login:
	aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin $(shell cd terraform && terraform output -raw ecr_repository_url | cut -d'/' -f1)

ecr-push: docker-build ecr-login
	docker tag taskmanager:latest $(shell cd terraform && terraform output -raw ecr_repository_url):latest
	docker push $(shell cd terraform && terraform output -raw ecr_repository_url):latest

# Terraform
deploy-infra:
	cd terraform && terraform init
	cd terraform && terraform plan
	cd terraform && terraform apply

deploy-app: ecr-push
	aws ecs update-service --cluster taskmanager-cluster --service taskmanager-service --force-new-deployment --region us-east-1

deploy: deploy-infra deploy-app

destroy:
	cd terraform && terraform destroy

# Utilitários
logs:
	aws logs tail /ecs/taskmanager --follow --region us-east-1

status:
	aws ecs describe-services --cluster taskmanager-cluster --services taskmanager-service --region us-east-1

clean:
	./mvnw clean
	docker system prune -f
