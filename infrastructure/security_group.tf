resource "aws_security_group" "ecs_service-sg" {
  name        = "reversi_ecs_service-sg"
  vpc_id      = aws_vpc.reversi-vpc.id
  description = "for_reversi_ecs_service_security_group"
  ingress = [
    {
      from_port        = 8080
      to_port          = 8080
      cidr_blocks      = ["0.0.0.0/0"]
      description      = ""
      ipv6_cidr_blocks = []
      prefix_list_ids  = []
      protocol         = "tcp"
      security_groups  = []
      self             = false
    },
  ]
  egress      = [
    {
      from_port        = 0
      to_port          = 0
      cidr_blocks      = ["0.0.0.0/0"]
      description      = ""
      ipv6_cidr_blocks = []
      prefix_list_ids  = []
      protocol         = "-1"
      security_groups  = []
      self             = false
    },
  ]
  tags     = {}
  tags_all = {}
}

resource "aws_security_group" "reversi_lb-sg" {
  vpc_id      = aws_vpc.reversi-vpc.id
  name        = "reversi_lb-sg"
  description = "for_reversi_load_balancer_security_group"
  tags        = {}
  tags_all    = {}
  ingress = [
    {
      from_port        = 0
      to_port          = 0
      cidr_blocks      = ["0.0.0.0/0"]
      description      = ""
      ipv6_cidr_blocks = []
      prefix_list_ids  = []
      protocol         = "-1"
      security_groups  = []
      self             = false
    }
  ]
  egress      = [
    {
      from_port        = 0
      to_port          = 0
      cidr_blocks      = ["0.0.0.0/0"]
      description      = ""
      ipv6_cidr_blocks = []
      prefix_list_ids  = []
      protocol         = "-1"
      security_groups  = []
      self             = false
    },
  ]
}
