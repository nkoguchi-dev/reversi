-- テーブルの作成と権限設定
CREATE TABLE reversi.test_table
(
    id   INT PRIMARY KEY,
    name VARCHAR(100)
);

-- reversi_userにtest_tableに対する権限を付与
GRANT ALL ON reversi.test_table TO reversi_user;
