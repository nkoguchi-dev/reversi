-- CREATE ROLE reversi_user LOGIN PASSWORD 'reversi_user';
--
-- REVOKE ALL PRIVILEGES ON SCHEMA public FROM PUBLIC;
--
-- GRANT USAGE ON SCHEMA reversi TO reversi_user;
--
-- ALTER DEFAULT PRIVILEGES IN SCHEMA reversi GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO reversi_user;

-- ユーザーの作成
CREATE USER IF NOT EXISTS reversi_user PASSWORD 'reversi_user';

-- スキーマの作成
CREATE SCHEMA IF NOT EXISTS reversi AUTHORIZATION reversi_user;

-- 公共の権限を削除
REVOKE ALL ON SCHEMA public FROM PUBLIC;

-- テーブルの作成と権限設定
-- CREATE TABLE test_schema.some_table
-- (
--     id   INT PRIMARY KEY,
--     name VARCHAR(100)
-- );

-- ユーザーに権限を付与
-- GRANT USAGE ON SCHEMA test_schema TO test_user;