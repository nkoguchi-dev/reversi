resource "aws_s3_bucket" "reversi-web-front" {
  bucket                      = "reversi-web-front"
  object_lock_enabled         = false
  tags                        = {}
  tags_all                    = {}
}