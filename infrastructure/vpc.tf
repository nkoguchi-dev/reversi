resource "aws_vpc" "reversi-vpc" {
  cidr_block = "10.0.0.0/16"
  tags     = {
    "Name" = "reversi-vpc"
  }
  tags_all = {
    "Name" = "reversi-vpc"
  }
}

resource "aws_subnet" "public_1" {
  assign_ipv6_address_on_creation                = false
  availability_zone                              = "ap-northeast-1a"
  cidr_block                                     = "10.0.0.0/20"
  enable_dns64                                   = false
  enable_resource_name_dns_a_record_on_launch    = false
  enable_resource_name_dns_aaaa_record_on_launch = false
  ipv6_native                                    = false
  map_public_ip_on_launch                        = false
  private_dns_hostname_type_on_launch            = "ip-name"
  tags                                           = {
    "Name" = "reversi-subnet-public1-ap-northeast-1a"
  }
  tags_all                                       = {
    "Name" = "reversi-subnet-public1-ap-northeast-1a"
  }
  vpc_id                                         = "vpc-069a2ddbd70e939a8"
}

resource "aws_subnet" "public_2" {
  assign_ipv6_address_on_creation                = false
  availability_zone                              = "ap-northeast-1c"
  cidr_block                                     = "10.0.16.0/20"
  enable_dns64                                   = false
  enable_resource_name_dns_a_record_on_launch    = false
  enable_resource_name_dns_aaaa_record_on_launch = false
  ipv6_native                                    = false
  map_public_ip_on_launch                        = false
  private_dns_hostname_type_on_launch            = "ip-name"
  tags                                           = {
    "Name" = "reversi-subnet-public2-ap-northeast-1c"
  }
  tags_all                                       = {
    "Name" = "reversi-subnet-public2-ap-northeast-1c"
  }
  vpc_id                                         = "vpc-069a2ddbd70e939a8"
}

resource "aws_subnet" "private_1" {
  assign_ipv6_address_on_creation                = false
  availability_zone                              = "ap-northeast-1a"
  cidr_block                                     = "10.0.128.0/20"
  enable_dns64                                   = false
  enable_resource_name_dns_a_record_on_launch    = false
  enable_resource_name_dns_aaaa_record_on_launch = false
  ipv6_native                                    = false
  map_public_ip_on_launch                        = false
  private_dns_hostname_type_on_launch            = "ip-name"
  tags                                           = {
    "Name" = "reversi-subnet-private1-ap-northeast-1a"
  }
  tags_all                                       = {
    "Name" = "reversi-subnet-private1-ap-northeast-1a"
  }
  vpc_id                                         = "vpc-069a2ddbd70e939a8"
}

resource "aws_subnet" "private_2" {
  assign_ipv6_address_on_creation                = false
  availability_zone                              = "ap-northeast-1c"
  cidr_block                                     = "10.0.144.0/20"
  enable_dns64                                   = false
  enable_resource_name_dns_a_record_on_launch    = false
  enable_resource_name_dns_aaaa_record_on_launch = false
  ipv6_native                                    = false
  map_public_ip_on_launch                        = false
  private_dns_hostname_type_on_launch            = "ip-name"
  tags                                           = {
    "Name" = "reversi-subnet-private2-ap-northeast-1c"
  }
  tags_all                                       = {
    "Name" = "reversi-subnet-private2-ap-northeast-1c"
  }
  vpc_id                                         = "vpc-069a2ddbd70e939a8"
}

#
#resource "aws_internet_gateway" "gw" {
#  vpc_id = aws_vpc.reversi.id
#}
#
#resource "aws_route_table" "public" {
#  vpc_id = aws_vpc.reversi.id
#
#  route {
#    cidr_block = "0.0.0.0/0"
#    gateway_id = aws_internet_gateway.gw.id
#  }
#}

#resource "aws_route_table_association" "public_1" {
#  subnet_id      = aws_subnet.public_1.id
#  route_table_id = aws_route_table.public.id
#}
#
#resource "aws_route_table_association" "public_2" {
#  subnet_id      = aws_subnet.public_2.id
#  route_table_id = aws_route_table.public.id
#}
