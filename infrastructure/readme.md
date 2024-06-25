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
- ECSサービスに紐づけるサブネットとセキュリティグループを仮で作成
  - ReversiWebApiサブネットを作成
  - ReversiWebApiセキュリティグループを作成
- ECSでクラスターを作成
  - ReversiWebApiクラスターを作成
- ESCで作成したクラスターに紐づくサービスを作成
  - ReversiWebApiサービスを作成
- Code Buildに割り当てられたIAM RoleにECRにアクセスするための権限を持ったポリシーを追加
  - reversi-codebuild-policyを作成
    - ECRの権限とS3の権限を持つポリシー
  - codebuild-Reversi-service-roleを作成
    - reversi-codebuild-policyをアタッチ
- Code Pipelineを作成
  - GitHubリポジトリ上にreversi/web-api/buildspec.ymlを作成
    - dockerコマンドでイメージをビルド
    - 作成したイメージをECRにプッシュ
    - MANIFEST情報をS3にアップロード
- system managerのparameter storeに環境変数で利用するパスワード等を登録
- ECSのタスク定義に環境変数を設定



# tfファイル作成時に利用するterraformのコマンド

AWSのコンソールからリソースを作成してその状態をterraformで管理するためのtfファイルを作成する必要があるけど初学者が1から自分で書くのはかなり難しい。
本当はtfファイルを作成してからterraform applyを実行するのが理想だと思うのだけど、今の段階はtfファイルの書き方を完全に理解した状態まで持っていくための学習なので楽をする方法をメモに残しておく。

それぞれのリソースに対するtfファイルの書き方は[terraformの公式ドキュメント](https://registry.terraform.io/providers/hashicorp/aws/latest/docs)を参照。

- AWSのコンソールやコマンドを利用してリソースを作成する
- terraformでリソースを管理するためのtfファイルを作成する
  - ECRリポジトリを作成する場合
    ```terraform
    # erc.tf
    resource "aws_ecr_repository" "reversi" {
      name = "reversi/web-api"
    }
    ```
  - terraform showを実行すると現在のリソースの状態を確認できるのでtfファイル作成時に参考できる
- terraform planを実行して実際のリソースとの差分を確認する
  ```bash
  % terraform plan 
  aws_ecr_repository.reversi: Refreshing state... [id=reversi/web-api]
  
  No changes. Your infrastructure matches the configuration.
  
  Terraform has compared your real infrastructure against your configuration and found no differences, so no changes are needed.
  ```
  No changesとか出てれば大丈夫。差分がある場合はtfファイルを修正する。
