CREATE KEYSPACE gaming_network WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1};

USE gaming_network;

CREATE TABLE authentifications (
  gamer_id VARCHAR PRIMARY KEY,
  password VARCHAR
);

CREATE INDEX authentification_password ON authentifications (password);

CREATE TABLE gamers (
  gamer_id VARCHAR PRIMARY KEY,
  gamer_name VARCHAR,
  gamer_surname VARCHAR,
  gamer_location VARCHAR,
  gamer_email VARCHAR
);

CREATE TABLE games (
  game_id VARCHAR PRIMARY KEY,
  game_type VARCHAR,
  game_website VARCHAR,
  game_plateforms SET<VARCHAR>
);

CREATE TABLE gamers_subscription_counts (
  gamer_subscription_count COUNTER,
  gamer_id VARCHAR PRIMARY KEY
);

CREATE TABLE games_subscription_counts (
  game_subscription_count COUNTER,
  game_id VARCHAR PRIMARY KEY
);

CREATE TABLE games_gamers (
  game_id VARCHAR,
  gamer_id VARCHAR,
  gamer_name VARCHAR,
  gamer_surname VARCHAR,
  gamer_location VARCHAR,
  gamer_email VARCHAR,
  PRIMARY KEY (game_id, gamer_id)
);

CREATE TABLE gamers_games (
  gamer_id VARCHAR,
  game_id VARCHAR,
  game_type VARCHAR,
  game_website VARCHAR,
  game_plateforms SET<VARCHAR>,
  PRIMARY KEY (gamer_id, game_id)
);

CREATE TABLE posts (
  post_id UUID PRIMARY KEY,
  post_body VARCHAR,
  author_gamer_id VARCHAR,
  post_game_id VARCHAR,
  latest_change TIMESTAMP
);

CREATE INDEX post_author ON posts (author_gamer_id);

CREATE INDEX post_game ON posts (post_game_id);

CREATE INDEX post_latest_change ON posts (latest_change);

CREATE TABLE timeline (
  gamer_id VARCHAR,
  post_id UUID,
  post_game_id VARCHAR,
  author_gamer_id VARCHAR,
  post_body VARCHAR,
  latest_change TIMESTAMP,
  PRIMARY KEY (gamer_id, post_id)
);

CREATE INDEX timeline_latest_change ON timeline (latest_change);

CREATE TABLE comments (
  post_id UUID,
  comment_id UUID,
  comment_author_gamer_id VARCHAR,
  comment_body VARCHAR,
  comment_latest_change TIMESTAMP,
  PRIMARY KEY (post_id, comment_id)
);

CREATE INDEX comment_latest_change ON comments (comment_latest_change);
