variable "image_name" {
    description = "ECR image name"
    type        = string
}

variable "region" {
    description = "AWS Region"
    type        = string
    default     = "ap-northeast-1"
}

variable "artifact_bucket" {
    description = "S3 bucket for CodePipeline artifacts"
    type        = string
}
