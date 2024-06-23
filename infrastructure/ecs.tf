resource "aws_ecs_cluster" "ReversiWebApi" {
  name     = "ReversiWebApi"
  tags     = {}
  tags_all = {}

  setting {
    name  = "containerInsights"
    value = "disabled"
  }
}

resource "aws_ecs_service" "reversi-web-api" {
  name                               = "reversi-web-api"
  cluster                            = aws_ecs_cluster.ReversiWebApi.id
  task_definition                    = aws_ecs_task_definition.ReversiWebApi.arn
  deployment_maximum_percent         = 200
  deployment_minimum_healthy_percent = 100
  desired_count                      = 1
  enable_ecs_managed_tags            = true
  enable_execute_command             = false
  health_check_grace_period_seconds  = 0
  platform_version                   = "LATEST"
  propagate_tags                     = "NONE"
  scheduling_strategy                = "REPLICA"
  tags                               = {}
  tags_all                           = {}
  triggers                           = {}

  capacity_provider_strategy {
    base              = 0
    capacity_provider = "FARGATE"
    weight            = 1
  }

  deployment_circuit_breaker {
    enable   = true
    rollback = true
  }

  deployment_controller {
    type = "ECS"
  }

  network_configuration {
    assign_public_ip = true
    security_groups  = [
      aws_security_group.ecs_sg.id
    ]
    subnets = [
      aws_subnet.public_1.id,
      aws_subnet.public_2.id
    ]
  }
}

resource "aws_ecs_task_definition" "ReversiWebApi" {
  cpu                      = "256"
  family                   = "ReversiWebApi"
  memory                   = "2048"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  task_role_arn            = "arn:aws:iam::${local.aws_account_id}:role/EcsTaskExecutionRole"
  execution_role_arn       = "arn:aws:iam::${local.aws_account_id}:role/EcsTaskExecutionRole"

  container_definitions = jsonencode(
    [
      {
        cpu              = 0
        environment      = []
        environmentFiles = []
        essential        = true
        image            = "${local.aws_account_id}.dkr.ecr.ap-northeast-1.amazonaws.com/${var.image_name}"
        logConfiguration = {
          logDriver = "awslogs"
          options   = {
            awslogs-create-group  = "true"
            awslogs-group         = "/ecs/"
            awslogs-region        = "ap-northeast-1"
            awslogs-stream-prefix = "ecs"
          }
          secretOptions = []
        }
        mountPoints  = []
        name         = "reversi-web-api"
        portMappings = [
          {
            appProtocol   = "http"
            containerPort = 80
            hostPort      = 80
            name          = "reversi-web-api-80-tcp"
            protocol      = "tcp"
          },
        ]
        systemControls = []
        ulimits        = []
        volumesFrom    = []
      },
    ]
  )

  runtime_platform {
    cpu_architecture        = "X86_64"
    operating_system_family = "LINUX"
  }
}
