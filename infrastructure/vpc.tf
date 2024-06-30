resource "aws_vpc" "reversi-vpc" {
  cidr_block = "10.0.0.0/16"
  tags       = {
    "Name" = "reversi-vpc"
  }
  tags_all = {
    "Name" = "reversi-vpc"
  }
}

resource "aws_subnet" "public_1" {
  vpc_id = aws_vpc.reversi-vpc.id
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
  tags_all = {
    "Name" = "reversi-subnet-public1-ap-northeast-1a"
  }
}

resource "aws_subnet" "public_2" {
  vpc_id = aws_vpc.reversi-vpc.id
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
  tags_all = {
    "Name" = "reversi-subnet-public2-ap-northeast-1c"
  }
}

resource "aws_subnet" "private_1" {
  vpc_id = aws_vpc.reversi-vpc.id
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
  tags_all = {
    "Name" = "reversi-subnet-private1-ap-northeast-1a"
  }
}

resource "aws_subnet" "private_2" {
  vpc_id = aws_vpc.reversi-vpc.id
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
  tags_all = {
    "Name" = "reversi-subnet-private2-ap-northeast-1c"
  }
}

resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.reversi-vpc.id
  tags   = {
    "Name" = "reversi-igw"
  }
  tags_all = {
    "Name" = "reversi-igw"
  }
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.reversi-vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "igw-02fc7a234a1f399af"
  }
  tags = {
    "Name" = "reversi-rtb-public"
  }
  tags_all = {
    "Name" = "reversi-rtb-public"
  }
}

resource "aws_route_table" "private_1" {
  vpc_id = aws_vpc.reversi-vpc.id
  route  = []
  tags   = {
    "Name" = "reversi-rtb-private1-ap-northeast-1a"
  }
  tags_all = {
    "Name" = "reversi-rtb-private1-ap-northeast-1a"
  }
}

resource "aws_route_table" "private_2" {
  vpc_id = aws_vpc.reversi-vpc.id
  route  = []
  tags   = {
    "Name" = "reversi-rtb-private2-ap-northeast-1c"
  }
  tags_all = {
    "Name" = "reversi-rtb-private2-ap-northeast-1c"
  }
}

resource "aws_route_table_association" "public_1" {
  subnet_id      = aws_subnet.public_1.id
  route_table_id = aws_route_table.public.id
}

resource "aws_route_table_association" "public_2" {
  subnet_id      = aws_subnet.public_2.id
  route_table_id = aws_route_table.public.id
}

resource "aws_route_table_association" "private_1" {
  subnet_id      = aws_subnet.private_1.id
  route_table_id = aws_route_table.private_1.id
}

resource "aws_route_table_association" "private_2" {
  subnet_id      = aws_subnet.private_2.id
  route_table_id = aws_route_table.private_2.id
}
