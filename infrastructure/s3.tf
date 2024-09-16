resource "aws_s3_bucket" "reversi-ui" {
  bucket                      = "reversi-ui"
  object_lock_enabled         = false
  tags                        = {}
  tags_all                    = {}
}