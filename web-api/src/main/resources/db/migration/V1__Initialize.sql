-- ユーザーの作成
CREATE USER IF NOT EXISTS reversi_user PASSWORD 'reversi_user';

-- スキーマの作成
CREATE SCHEMA IF NOT EXISTS reversi AUTHORIZATION reversi_user;