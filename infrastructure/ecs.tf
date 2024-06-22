
locals {
  aws_account_id = data.aws_caller_identity.self.account_id
}

resource "aws_ecs_task_definition" "ReversiWebApi" {
  cpu                      = "256"
  execution_role_arn       = "arn:aws:iam::${local.aws_account_id}:role/EcsTaskExecutionRole"
  family                   = "ReversiWebApi"
  memory                   = "2048"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  task_role_arn            = "arn:aws:iam::${local.aws_account_id}:role/EcsTaskExecutionRole"

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
