variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

variable "app_name" {
  description = "Nome da aplicação"
  type        = string
  default     = "taskmanager"
}

variable "availability_zones" {
  description = "Zonas de disponibilidade"
  type        = list(string)
  default     = ["us-east-1a", "us-east-1b"]
}

variable "fargate_cpu" {
  description = "CPU para Fargate task"
  type        = string
  default     = "256"
}

variable "fargate_memory" {
  description = "Memória para Fargate task"
  type        = string
  default     = "512"
}

variable "app_count" {
  description = "Número de containers da aplicação"
  type        = number
  default     = 2
}

variable "db_username" {
  description = "Username do PostgreSQL"
  type        = string
  default     = "postgres"
}

variable "db_password" {
  description = "Password do PostgreSQL"
  type        = string
  sensitive   = true
  default     = "admin123"
}