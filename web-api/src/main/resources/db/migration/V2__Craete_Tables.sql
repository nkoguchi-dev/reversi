CREATE TABLE reversi.games
(
    game_id      VARCHAR(64),
    player1_name VARCHAR(100)             NOT NULL,
    player2_name VARCHAR(100)             NOT NULL,
    status       VARCHAR(20)              NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (game_id),
    CHECK (status IN ('CREATED', 'IN_PROGRESS', 'FINISHED'))
);
GRANT ALL ON reversi.games TO reversi_user;

CREATE TABLE reversi.moves
(
    move_id             VARCHAR(64),
    game_id             VARCHAR(64)              NOT NULL,
    player_number       VARCHAR(10)              NOT NULL,
    horizontal_position VARCHAR(10)              NOT NULL,
    vertical_position   VARCHAR(10)              NOT NULL,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (move_id),
    FOREIGN KEY (game_id) REFERENCES reversi.games (game_id)
);
GRANT ALL ON reversi.moves TO reversi_user;