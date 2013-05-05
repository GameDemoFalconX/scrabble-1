CREATE TABLE scrabble_user (
    user_id VARCHAR(36) NOT NULL,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password TEXT NOT NULL,
    salt TEXT NOT NULL,
    created VARCHAR(25) NOT NULL,
    games_played INTEGER,
    games_won INTEGER,
    games_lost INTEGER,
    PRIMARY KEY (user_id),
    UNIQUE(user_id)
);

CREATE TABLE scrabble_play (
    play_id VARCHAR(36) NOT NULL,
    player VARCHAR(36) NOT NULL,
    created VARCHAR(25) NOT NULL,
    modified VARCHAR(25) NOT NULL,
    state INTEGER NOT NULL,
    score INTEGER,
    tests_played INTEGER,
    tests_won INTEGER,
    tests_lost INTEGER,
    PRIMARY KEY (play_id),
    UNIQUE (play_id),
    FOREIGN KEY (player) REFERENCES scrabble_user (user_id)
);

/* rack and grid are stored in JSON */
CREATE TABLE scrabble_test (
    id INTEGER NOT NULL,
    indice INTEGER NOT NULL,
    parent_play VARCHAR(36) NOT NULL,
    rack BLOB NOT NULL,
    grid BLOB NOT NULL,
    score INTEGER NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id),
    FOREIGN KEY (parent_play) REFERENCES scrabble_play (play_id)
);

CREATE TABLE scrabble_play_state (
    id INTEGER NOT NULL,
    parent_play VARCHAR(36) NOT NULL,
    rack BLOB NOT NULL,
    grid BLOB NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id),
    FOREIGN KEY (parent_play) REFERENCES scrabble_play (play_id)
);