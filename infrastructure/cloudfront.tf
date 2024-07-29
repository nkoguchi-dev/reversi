data "aws_cloudfront_cache_policy" "caching-optimized" {
  name = "Managed-CachingOptimized"
}

data "aws_cloudfront_cache_policy" "caching-disabled" {
  name = "Managed-CachingDisabled"
}

data "aws_cloudfront_origin_request_policy" "all-viewer" {
  name = "Managed-AllViewer"
}

data "aws_cloudfront_origin_access_identity" "reversi" {
  id      = "E2LWB9V6PB7VZW"
}

data "aws_ssm_parameter" "acm_certificate_arn" {
  name = "/reversi/cloud-front/acm_certificate_arn"
}

resource "aws_cloudfront_distribution" "reversi" {
  aliases             = ["reversi.koppepan.org"]
  enabled             = true
  is_ipv6_enabled     = true
  retain_on_delete    = false
  default_root_object = "index.html"
  price_class         = "PriceClass_All"

  default_cache_behavior {
    allowed_methods        = ["GET", "HEAD"]
    cached_methods         = ["GET", "HEAD"]
    compress               = true
    default_ttl            = 0
    max_ttl                = 0
    smooth_streaming       = false
    viewer_protocol_policy = "allow-all"
    cache_policy_id        = data.aws_cloudfront_cache_policy.caching-optimized.id
    target_origin_id       = aws_s3_bucket.reversi-web-front.bucket_regional_domain_name
  }

  ordered_cache_behavior {
    allowed_methods          = ["GET", "HEAD", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"]
    cached_methods           = ["GET", "HEAD"]
    compress                 = true
    default_ttl              = 0
    max_ttl                  = 0
    smooth_streaming         = false
    path_pattern             = "/api/*"
    trusted_key_groups       = []
    trusted_signers          = []
    target_origin_id         = aws_lb.reversi-web-api-lb.dns_name
    cache_policy_id          = data.aws_cloudfront_cache_policy.caching-disabled.id
    viewer_protocol_policy   = "redirect-to-https"
    origin_request_policy_id = data.aws_cloudfront_origin_request_policy.all-viewer.id
  }

  origin {
    domain_name         = aws_s3_bucket.reversi-web-front.bucket_regional_domain_name
    origin_id           = aws_s3_bucket.reversi-web-front.bucket_regional_domain_name

    s3_origin_config {
      origin_access_identity = data.aws_cloudfront_origin_access_identity.reversi.cloudfront_access_identity_path
    }
  }

  origin {
    domain_name         = aws_lb.reversi-web-api-lb.dns_name
    origin_id           = aws_lb.reversi-web-api-lb.dns_name

    custom_origin_config {
      http_port                = 80
      https_port               = 443
      origin_protocol_policy   = "match-viewer"
      origin_ssl_protocols     = ["TLSv1.2"]
    }
  }

  restrictions {
    geo_restriction {
      locations        = []
      restriction_type = "none"
    }
  }

  viewer_certificate {
    acm_certificate_arn            = data.aws_ssm_parameter.acm_certificate_arn.value
    cloudfront_default_certificate = false
    minimum_protocol_version       = "TLSv1.2_2021"
    ssl_support_method             = "sni-only"
  }
}