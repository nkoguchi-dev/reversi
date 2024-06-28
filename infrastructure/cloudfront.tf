resource "aws_cloudfront_distribution" "reversi" {
  aliases = [
    "reversi.koppepan.org",
  ]
  default_root_object = "index.html"
  enabled             = true
  http_version        = "http2"
  is_ipv6_enabled     = true
  price_class         = "PriceClass_All"
  retain_on_delete    = false
  tags                = {}
  tags_all            = {}
  wait_for_deployment = true
  web_acl_id          = "arn:aws:wafv2:us-east-1:713746206827:global/webacl/CreatedByCloudFront-5b7514f5-7ae6-4338-992d-fe2768829f9d/943c1042-792d-4e63-b5bf-431ad616735a"

  default_cache_behavior {
    allowed_methods = [
      "GET",
      "HEAD",
    ]
    cache_policy_id = "658327ea-f89d-4fab-a63d-7e88639e58f6"
    cached_methods  = [
      "GET",
      "HEAD",
    ]
    compress               = true
    default_ttl            = 0
    max_ttl                = 0
    min_ttl                = 0
    smooth_streaming       = false
    target_origin_id       = "reversi-web-front.s3.ap-northeast-1.amazonaws.com"
    trusted_key_groups     = []
    trusted_signers        = []
    viewer_protocol_policy = "allow-all"
  }

  origin {
    connection_attempts      = 3
    connection_timeout       = 10
    #origin_access_control_id = "E2UOHDJZRBGJ94"
    domain_name = "reversi-web-front.s3.ap-northeast-1.amazonaws.com"
    origin_id   = "reversi-web-front.s3.ap-northeast-1.amazonaws.com"

    s3_origin_config {
      origin_access_identity = "origin-access-identity/cloudfront/E2LWB9V6PB7VZW"
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