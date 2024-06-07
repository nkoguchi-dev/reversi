-- テーブルの作成と権限設定
CREATE TABLE reversi.games
(
    game_id   VARCHAR(64),
    player1_name VARCHAR(100) NOT NULL,
    player2_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (game_id)
);

-- reversi_userにtest_tableに対する権限を付与
GRANT ALL ON reversi.games TO reversi_user;
