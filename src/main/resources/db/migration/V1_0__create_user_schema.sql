CREATE TABLE users
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    version INT          NOT NULL,
    PRIMARY KEY (id)
)