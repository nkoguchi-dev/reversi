# AWSでのリソース構築メモ

- terraform準備
  - main.tf作成
  - provider.tf作成
- ECRでリポジトリを作成
  - reversi/web-apiを作成
- IAMでECSタスクのRoleを作成
  - ECSが動作するためのロールEcsTaskExecutionRoleを作成
- ECSでタスク定義を作成
  - ReversiWebApiタスクを作成
- Code Pipelineを作成
  - GitHubリポジトリ上にreversi/web-api/buildspec.ymlを作成



# terraformコマンドの基本的な使い方

terraform initは実行済みの想定。
AWSのコンソールからリソースを作成してその状態をterraformで管理するためのtfファイルを作成する。
本当はtfファイルを作成してからterraform applyを実行するのが良さそうだけど、tfファイルの書き方を完全に理解した状態まで持っていくための学習なので今は順序を気にしない。

それぞれのリソースに対するtfファイルの書き方は[terraformの公式ドキュメント](https://registry.terraform.io/providers/hashicorp/aws/latest/docs)を参照する。

- AWSのコンソールやコマンドを利用してリソースを作成する
- terraformでリソースを管理するためのtfファイルを作成する
  - ECRリポジトリを作成する場合
    ```terraform
    # erc.tf
    resource "aws_ecr_repository" "reversi" {
      name = "reversi/web-api"
    }
    ```
- terraform planを実行して実際のリソースとの差分を確認する
```bash
% terraform plan 
aws_ecr_repository.reversi: Refreshing state... [id=reversi/web-api]

No changes. Your infrastructure matches the configuration.

Terraform has compared your real infrastructure against your configuration and found no differences, so no changes are needed.
```
No changesとか出てれば大丈夫。差分がある場合はtfファイルを修正する。
