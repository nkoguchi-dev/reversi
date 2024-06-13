# プロジェクトの説明
ドメイン駆動設計とヘキサゴナルアーキテクチャを利用してリバーシを作成するプロジェクトです。
個人的なメモと学習のために作成しているため、実際にサービスとして稼働することを目的としていません。

# 起動方法

Dockerを利用してアプリケーションを起動します。

まず最初にプロジェクトのルートディレクトリに移動し、npm installを実行します。
```shell
docker compose run --no-deps --rm reversi_web-front npm install
```

次にDockerコンテナを起動します。
```shell
docker compose up -d --build
```

ブラウザで`http://localhost:14200`にアクセスするとアプリケーションが起動していることを確認できます。

## プロジェクトの構成

### バックエンドアプリケーション

KotlinとSpring Bootを利用して作成しています。
ORMはKomapperを利用しデータベースにはインメモリのH2を用いています。

#### Presentation

Controllerを用いてプライマリーアダプターを実装しています。

#### Application

プライマリーポートを実装しています。
アプリケーション層としてドメインロジックの呼び出し、トランザクション管理、ログ出力を行います。

#### Domain

ドメイン層になります。
リバーシのルールを実装し他の層に依存しません。
ドメイン層に配置されたRepositoryインターフェースがセカンダリーポートを表現しています。

#### Infrastructure

セカンダリーアダプターを実装しています。

### フロントエンドアプリケーション

未実装です

### インフラストラクチャー

IaCを利用してAWSにリソースを構築する予定です。

# 静的解析

SonarQubeを利用して静的解析を行うよう設定してあります。
別途SonarQubeのDockerコンテナを起動しプロジェクトの解析結果を閲覧可能です。

環境変数にSonarQubeへの接続情報を設定してください。
```shell
# web-api
export REVERSI_WEB_API_SONAR_PROJECT_KEY={SonarQubeでのプロジェクトキー}
export REVERSI_WEB_API_SONAR_PROJECT_NAME={SonarQubeでのプロジェクト名}
export REVERSI_WEB_API_SONAR_HOST_URL={SonarQubeのホストURL}
export REVERSI_WEB_API_SONAR_TOKEN={SonarQubeでのアクセストークン}
# web-front
export REVERSI_WEB_FRONT_SONAR_PROJECT_KEY={SonarQubeでのプロジェクトキー}
export REVERSI_WEB_FRONT_SONAR_PROJECT_NAME={SonarQubeでのプロジェクト名}
export REVERSI_WEB_FRONT_SONAR_HOST_URL={SonarQubeのホストURL}
export REVERSI_WEB_FRONT_SONAR_TOKEN={SonarQubeでのアクセストークン}
```