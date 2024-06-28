# SSM Parameter Storeから値を取得
data "aws_ssm_parameter" "codepipeline_role_arn" {
  name = "/reversi/web-api/codepipeline_role_arn"
}

data "aws_ssm_parameter" "codestar_connection_arn" {
  name = "/reversi/web-api/codestar_connection_arn"
}

# Terraform変数にSSMから取得した値を設定
locals {
  codepipeline_role_arn = data.aws_ssm_parameter.codepipeline_role_arn.value
  codestar_connection_arn = data.aws_ssm_parameter.codestar_connection_arn.value
}

resource "aws_codepipeline" "reversi_web-api" {
  name     = "reversi_web-api"
  role_arn = local.codepipeline_role_arn
  tags     = {}
  tags_all = {}

  artifact_store {
    location = var.artifact_bucket
    type     = "S3"
  }

  stage {
    name = "Source"

    action {
      category      = "Source"
      configuration = {
        "BranchName"           = "release/prd"
        "ConnectionArn"        = local.codestar_connection_arn
        "DetectChanges"        = "true"
        "FullRepositoryId"     = "nkoguchi-dev/reversi"
        "OutputArtifactFormat" = "CODE_ZIP"
      }
      input_artifacts  = []
      name             = "Source"
      namespace        = "SourceVariables"
      output_artifacts = ["SourceArtifact"]
      owner            = "AWS"
      provider         = "CodeStarSourceConnection"
      region           = var.region
      run_order        = 1
      version          = "1"
    }
  }
  stage {
    name = "Build"

    action {
      category         = "Build"
      configuration    = {
        "ProjectName" = "Reversi"
      }
      input_artifacts  = ["SourceArtifact"]
      name             = "Build"
      namespace        = "BuildVariables"
      output_artifacts = ["BuildArtifact"]
      owner            = "AWS"
      provider         = "CodeBuild"
      region           = var.region
      run_order        = 1
      version          = "1"
    }
  }
  stage {
    name = "Deploy"

    action {
      category      = "Deploy"
      configuration = {
        "ClusterName" = "ReversiWebApi"
        "FileName"    = "imagedefinitions.json"
        "ServiceName" = "reversi-web-api"
      }
      input_artifacts  = ["BuildArtifact"]
      name             = "Deploy"
      namespace        = "DeployVariables"
      output_artifacts = []
      owner            = "AWS"
      provider         = "ECS"
      region           = var.region
      run_order        = 1
      version          = "1"
    }
  }
}