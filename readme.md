# プロジェクトの説明
ドメイン駆動設計とヘキサゴナルアーキテクチャを利用してリバーシを作成するプロジェクトです。
個人的なメモと学習のために作成しているため、実際に遊ぶことを目的としていません。

## プロジェクトの構成

### バックエンドアプリケーション

KotlinとSpring Bootを利用して作成しています。

#### Presentation

Controllerを用いてプライマリーアダプターを実装しています。

#### Application

アプリケーション層になります。
プライマリーポートを実装しています。

#### Domain

ドメイン層になります。
リバーシのルールを実装し他の層に依存しません。
ドメイン層に配置されたRepositoryインターフェースがセカンダリーポートを表現しています。

#### Infrastructure

セカンダリーアダプターを実装しています。
H2データベースを利用し、Komapperを用いてデータベースアクセスを行う予定です。

### フロントエンドアプリケーション

未実装です

### インフラストラクチャー

IaCを利用してAWSにリソースを構築する予定です。

