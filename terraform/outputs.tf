output "load_balancer_dns" {
  description = "DNS do Load Balancer"
  value       = aws_lb.main.dns_name
}

output "ecr_repository_url" {
  description = "URL do repositório ECR"
  value       = aws_ecr_repository.app.repository_url
}

output "rds_endpoint" {
  description = "Endpoint do RDS PostgreSQL"
  value       = aws_db_instance.postgres.endpoint
}

output "ecs_cluster_name" {
  description = "Nome do cluster ECS"
  value       = aws_ecs_cluster.main.name
}

output "vpc_id" {
  description = "ID da VPC"
  value       = aws_vpc.main.id
}