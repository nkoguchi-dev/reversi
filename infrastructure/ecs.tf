data "aws_ssm_parameter" "r2dbc_password" {
  name = "/reversi/web-api/r2dbc_password"
}

data "aws_ssm_parameter" "r2dbc_username" {
  name = "/reversi/web-api/r2dbc_username"
}

data "aws_ssm_parameter" "flyway_username" {
  name = "/reversi/web-api/flyway_username"
}

data "aws_ssm_parameter" "flyway_password" {
  name = "/reversi/web-api/flyway_password"
}

data "aws_ecs_task_definition" "reversi_task_definition" {
  task_definition = aws_ecs_task_definition.ReversiWebApi.family
}


resource "aws_ecs_cluster" "ReversiWebApi" {
  name     = "ReversiWebApi"
  tags     = {}
  tags_all = {}

  setting {
    name  = "containerInsights"
    value = "disabled"
  }
}

resource "aws_ecs_service" "reversi" {
  name                               = "reversi"
  cluster                            = aws_ecs_cluster.ReversiWebApi.id
  task_definition                    = "${aws_ecs_task_definition.ReversiWebApi.family}:${max(aws_ecs_task_definition.ReversiWebApi.revision, data.aws_ecs_task_definition.reversi_task_definition.revision)}"
  deployment_maximum_percent         = 200
  deployment_minimum_healthy_percent = 100
  desired_count                      = 1
  enable_ecs_managed_tags            = true
  enable_execute_command             = false
  health_check_grace_period_seconds  = 0
  launch_type                        = "FARGATE"
  platform_version                   = "LATEST"
  propagate_tags                     = "NONE"
  scheduling_strategy                = "REPLICA"
  tags                               = {}
  tags_all                           = {}
  triggers                           = {}

  deployment_controller {
    type = "ECS"
  }

  load_balancer {
    container_name   = "reversi-web-api"
    container_port   = 8080
    target_group_arn = aws_alb_target_group.reversi-web-api-tg.arn
  }

  network_configuration {
    assign_public_ip = true
    security_groups  = [aws_security_group.ecs_service-sg.id]
    subnets          = [
      aws_subnet.public_1.id,
      aws_subnet.public_2.id,
      aws_subnet.private_1.id,
      aws_subnet.private_2.id,
    ]
  }
}

resource "aws_ecs_task_definition" "ReversiWebApi" {
  cpu                      = "1024"
  family                   = "ReversiWebApi"
  memory                   = "4096"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  task_role_arn            = "arn:aws:iam::713746206827:role/EcsTaskExecutionRole"
  execution_role_arn       = "arn:aws:iam::713746206827:role/EcsTaskExecutionRole"

  container_definitions = jsonencode(
    [
      {
        cpu         = 0
        environment = [
          {
            name  = "FLYWAY_URL"
            value = "jdbc:h2:mem:reversi_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
          },
          {
            name  = "R2DBC_URL"
            value = "r2dbc:h2:mem:///reversi_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
          },
        ]
        secrets = [
          {
            name      = "FLYWAY_USERNAME"
            valueFrom = data.aws_ssm_parameter.flyway_username.arn
          },
          {
            name      = "FLYWAY_PASSWORD"
            valueFrom = data.aws_ssm_parameter.flyway_password.arn
          },
          {
            name      = "R2DBC_USERNAME"
            valueFrom = data.aws_ssm_parameter.r2dbc_username.arn
          },
          {
            name      = "R2DBC_PASSWORD"
            valueFrom = data.aws_ssm_parameter.r2dbc_password.arn
          },
        ]
        environmentFiles = []
        essential        = true
        image            = "${local.aws_account_id}.dkr.ecr.${var.region}.amazonaws.com/${var.image_name}"
        logConfiguration = {
          logDriver = "awslogs"
          options   = {
            awslogs-create-group  = "true"
            awslogs-group         = "/ecs/reversi_web-api/"
            awslogs-region        = var.region
            awslogs-stream-prefix = "ecs"
          }
          secretOptions = []
        }
        mountPoints  = []
        name         = "reversi-web-api"
        portMappings = [
          {
            appProtocol   = "http"
            containerPort = 8080
            hostPort      = 8080
            name          = "reversi-web-api-8080-tcp"
            protocol      = "tcp"
          },
        ]
        systemControls = []
        ulimits        = []
        volumesFrom    = []
      },
    ]
  )
}
