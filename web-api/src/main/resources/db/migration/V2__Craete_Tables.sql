CREATE TABLE reversi.games
(
    game_id            VARCHAR(255)             NOT NULL,
    player1_name       VARCHAR(200)             NOT NULL,
    player2_name       VARCHAR(200)             NOT NULL,
    status             VARCHAR(20)              NOT NULL,
    next_player_number VARCHAR(10)              NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (game_id),
    CHECK (status IN ('CREATED', 'IN_PROGRESS', 'FINISHED')),
    CHECK (next_player_number IN ('PLAYER1', 'PLAYER2'))
);
GRANT ALL ON reversi.games TO reversi_user;

CREATE TABLE reversi.disk_maps
(
    game_id             VARCHAR(255)             NOT NULL,
    horizontal_position VARCHAR(1)               NOT NULL,
    vertical_position   VARCHAR(1)               NOT NULL,
    disk_type           VARCHAR(10)              NOT NULL,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (game_id, horizontal_position, vertical_position),
    FOREIGN KEY (game_id) REFERENCES reversi.games (game_id),
    CHECK (disk_type IN ('DARK', 'LIGHT'))
);
GRANT ALL ON reversi.disk_maps TO reversi_user;

CREATE TABLE reversi.moves
(
    game_id             VARCHAR(255)             NOT NULL,
    move_id             VARCHAR(255)             NOT NULL,
    player_number       VARCHAR(10)              NOT NULL,
    horizontal_position VARCHAR(10)              NOT NULL,
    vertical_position   VARCHAR(10)              NOT NULL,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (game_id, move_id),
    FOREIGN KEY (game_id) REFERENCES reversi.games (game_id),
    CHECK (player_number IN ('PLAYER1', 'PLAYER2'))
);
GRANT ALL ON reversi.moves TO reversi_user;