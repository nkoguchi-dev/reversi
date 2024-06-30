resource "aws_alb_target_group" "reversi-web-api-tg" {
  deregistration_delay              = "300"
  ip_address_type                   = "ipv4"
  load_balancing_algorithm_type     = "round_robin"
  load_balancing_cross_zone_enabled = "use_load_balancer_configuration"
  name                              = "reversi-web-api-tg"
  port                              = 8080
  protocol                          = "HTTP"
  protocol_version                  = "HTTP1"
  slow_start                        = 0
  tags                              = {}
  tags_all                          = {}
  target_type                       = "ip"
  vpc_id                            = aws_vpc.reversi-vpc.id

  health_check {
    enabled             = true
    healthy_threshold   = 5
    interval            = 30
    matcher             = "200"
    path                = "/health"
    port                = "traffic-port"
    protocol            = "HTTP"
    timeout             = 5
    unhealthy_threshold = 2
  }

  stickiness {
    cookie_duration = 86400
    enabled         = false
    type            = "lb_cookie"
  }
}

resource "aws_lb" "reversi-web-api-lb" {
  name                                        = "reversi-web-api-lb2"
  desync_mitigation_mode                      = "defensive"
  drop_invalid_header_fields                  = false
  enable_cross_zone_load_balancing            = true
  enable_deletion_protection                  = false
  enable_http2                                = true
  enable_tls_version_and_cipher_suite_headers = false
  enable_waf_fail_open                        = false
  enable_xff_client_port                      = false
  idle_timeout                                = 60
  internal                                    = false
  ip_address_type                             = "ipv4"
  load_balancer_type                          = "application"
  preserve_host_header                        = false
  security_groups                             = [aws_security_group.reversi_lb-sg.id]
  subnets                                     = [
    aws_subnet.public_2.id,
    aws_subnet.private_1.id,
  ]
  tags                       = {}
  tags_all                   = {}
  xff_header_processing_mode = "append"

  subnet_mapping {
    subnet_id = aws_subnet.public_2.id
  }
  subnet_mapping {
    subnet_id = aws_subnet.private_1.id
  }
}

resource "aws_lb_listener" "reversi-https-listener" {
  certificate_arn   = "arn:aws:acm:ap-northeast-1:713746206827:certificate/73a076bd-441b-47d3-a33e-8e6fa5411325"
  load_balancer_arn = aws_lb.reversi-web-api-lb.arn
  port              = 443
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-TLS13-1-2-2021-06"
  tags              = {}
  tags_all          = {}

  default_action {
    order            = 1
    target_group_arn = aws_alb_target_group.reversi-web-api-tg.arn
    type             = "forward"
  }
}

resource "aws_lb_listener" "reversi-http-listener" {
  load_balancer_arn = aws_lb.reversi-web-api-lb.arn
  port              = 80
  protocol          = "HTTP"
  tags              = {}
  tags_all          = {}

  default_action {
    target_group_arn = aws_alb_target_group.reversi-web-api-tg.arn
    type             = "forward"
  }
}
