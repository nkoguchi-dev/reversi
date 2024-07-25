

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
    min_ttl                = 0
    smooth_streaming       = false
    viewer_protocol_policy = "allow-all"
    cache_policy_id        = "658327ea-f89d-4fab-a63d-7e88639e58f6"
    target_origin_id       = aws_s3_bucket.reversi-web-front.bucket_regional_domain_name
  }

  ordered_cache_behavior {
    allowed_methods          = ["DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"]
    cached_methods           = ["GET", "HEAD"]
    compress                 = true
    default_ttl              = 0
    max_ttl                  = 0
    min_ttl                  = 0
    smooth_streaming         = false
    path_pattern             = "/api/*"
    trusted_key_groups       = []
    trusted_signers          = []
    target_origin_id         = aws_lb.reversi-web-api-lb.dns_name
    cache_policy_id          = "4135ea2d-6df8-44a3-9df3-4b5a84be39ad"
    viewer_protocol_policy   = "redirect-to-https"
    origin_request_policy_id = "216adef6-5c7f-47e4-b989-5492eafa07d3"
  }

  origin {
    connection_attempts = 3
    connection_timeout  = 10
    domain_name         = "reversi-web-front.s3.ap-northeast-1.amazonaws.com"
    origin_id           = "reversi-web-front.s3.ap-northeast-1.amazonaws.com"

    s3_origin_config {
      origin_access_identity = "origin-access-identity/cloudfront/E2LWB9V6PB7VZW"
    }
  }

  origin {
    connection_attempts = 3
    connection_timeout  = 10
    domain_name         = aws_lb.reversi-web-api-lb.dns_name
    origin_id           = aws_lb.reversi-web-api-lb.dns_name

    custom_origin_config {
      http_port                = 80
      https_port               = 443
      origin_keepalive_timeout = 5
      origin_protocol_policy   = "match-viewer"
      origin_read_timeout      = 30
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
    acm_certificate_arn            = "arn:aws:acm:us-east-1:713746206827:certificate/a39248cb-af17-42ca-9d3a-6d7cd2230dcd"
    cloudfront_default_certificate = false
    minimum_protocol_version       = "TLSv1.2_2021"
    ssl_support_method             = "sni-only"
  }
}