locals {
  aws_account_id = data.aws_caller_identity.self.account_id
}

resource "aws_ecs_cluster" "ReversiWebApi" {
  name               = "ReversiWebApi"
  tags               = {}
  tags_all           = {}

  configuration {
    execute_command_configuration {
      logging = "DEFAULT"
    }
  }

  service_connect_defaults {
    namespace = "arn:aws:servicediscovery:ap-northeast-1:${local.aws_account_id}:namespace/ns-k27qc4qbvs43mcu7"
  }

  setting {
    name  = "containerInsights"
    value = "disabled"
  }
}

resource "aws_ecs_service" "reversi-web-api" {
  name                               = "reversi-web-api"
  cluster                            = "arn:aws:ecs:ap-northeast-1:${local.aws_account_id}:cluster/ReversiWebApi"
  deployment_maximum_percent         = 200
  deployment_minimum_healthy_percent = 100
  desired_count                      = 1
  enable_ecs_managed_tags            = true
  enable_execute_command             = false
  health_check_grace_period_seconds  = 0
  iam_role                           = "/aws-service-role/ecs.amazonaws.com/AWSServiceRoleForECS"
  platform_version                   = "LATEST"
  propagate_tags                     = "NONE"
  scheduling_strategy                = "REPLICA"
  tags                               = {}
  tags_all                           = {}
  task_definition                    = "ReversiWebApi:1"
  triggers                           = {}

  alarms {
    alarm_names = []
    enable      = false
    rollback    = false
  }

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
      "sg-2d2c1250",
    ]
    subnets = [
      "subnet-1fd13b34",
      "subnet-af7c21f4",
      "subnet-b4cc2ffc",
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
        image            = "${local.aws_account_id}.dkr.ecr.ap-northeast-1.amazonaws.com/reversi/web-api"
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
