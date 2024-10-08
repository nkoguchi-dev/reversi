data "aws_ssm_parameter" "web-api_codebuild_role_arn" {
  name = "/reversi/web-api/codebuild_role_arn"
}

data "aws_ssm_parameter" "ui_codebuild_role_arn" {
  name = "/reversi/ui/codebuild_role_arn"
}

locals {
  web-api_codebuild_role_arn = data.aws_ssm_parameter.web-api_codebuild_role_arn.value
  ui_codebuild_role_arn = data.aws_ssm_parameter.ui_codebuild_role_arn.value
}

resource "aws_codebuild_project" "reversi_web-api" {
  name               = "reversi_web-api"
  badge_enabled      = false
  build_timeout      = 60
  encryption_key     = "arn:aws:kms:${var.region}:${local.aws_account_id}:alias/aws/s3"
  project_visibility = "PRIVATE"
  queued_timeout     = 480
  service_role       = local.web-api_codebuild_role_arn
  tags               = {}
  tags_all           = {}

  artifacts {
    encryption_disabled    = false
    name                   = "reversi_web-api"
    override_artifact_name = false
    packaging              = "NONE"
    type                   = "CODEPIPELINE"
  }

  cache {
    type = "LOCAL"
    modes = ["LOCAL_SOURCE_CACHE", "LOCAL_DOCKER_LAYER_CACHE"]
  }

  environment {
    compute_type                = "BUILD_GENERAL1_SMALL"
    image                       = "aws/codebuild/amazonlinux2-x86_64-standard:5.0"
    image_pull_credentials_type = "CODEBUILD"
    privileged_mode             = false
    type                        = "LINUX_CONTAINER"

    environment_variable {
      name  = "AWS_ACCOUNT_ID"
      type  = "PLAINTEXT"
      value = local.aws_account_id
    }
    environment_variable {
      name  = "AWS_REGION"
      type  = "PLAINTEXT"
      value = var.region
    }
  }

  logs_config {
    cloudwatch_logs {
      status = "ENABLED"
    }
    s3_logs {
      encryption_disabled = false
      status              = "DISABLED"
    }
  }

  source {
    buildspec           = "web-api/buildspec.yml"
    git_clone_depth     = 0
    insecure_ssl        = false
    report_build_status = false
    type                = "CODEPIPELINE"
  }
}

resource "aws_codebuild_project" "reversi_ui" {
  name               = "reversi_ui"
  badge_enabled      = false
  build_timeout      = 60
  encryption_key     = "arn:aws:kms:${var.region}:${local.aws_account_id}:alias/aws/s3"
  project_visibility = "PRIVATE"
  queued_timeout     = 480
  service_role       = local.ui_codebuild_role_arn
  tags               = {}
  tags_all           = {}

  artifacts {
    encryption_disabled    = false
    name                   = "reversi_ui"
    override_artifact_name = false
    packaging              = "NONE"
    type                   = "CODEPIPELINE"
  }

  cache {
    type = "LOCAL"
    modes = ["LOCAL_SOURCE_CACHE", "LOCAL_DOCKER_LAYER_CACHE"]
  }

  environment {
    compute_type                = "BUILD_GENERAL1_SMALL"
    image                       = "aws/codebuild/amazonlinux2-x86_64-standard:5.0"
    image_pull_credentials_type = "CODEBUILD"
    privileged_mode             = false
    type                        = "LINUX_CONTAINER"
  }

  logs_config {
    cloudwatch_logs {
      status = "ENABLED"
    }
    s3_logs {
      encryption_disabled = false
      status              = "DISABLED"
    }
  }

  source {
    buildspec           = "ui/buildspec.yml"
    git_clone_depth     = 0
    insecure_ssl        = false
    report_build_status = false
    type                = "CODEPIPELINE"
  }
}