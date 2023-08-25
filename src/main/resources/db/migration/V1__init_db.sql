CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    username   VARCHAR(50) UNIQUE NOT NULL CHECK (LENGTH(username) >= 5),
    email      VARCHAR(50) UNIQUE NOT NULL CHECK (LENGTH(email) >= 5),
    password   VARCHAR(100)       NOT NULL CHECK (LENGTH(password) >= 8),
    first_name VARCHAR(50),
    last_name  VARCHAR(50)
);

CREATE TABLE note
(
    id          UUID PRIMARY KEY,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    title       VARCHAR(100) NOT NULL CHECK (LENGTH(title) >= 5 ),
    content     VARCHAR(500) NOT NULL CHECK (LENGTH(content) >= 5),
    access_type VARCHAR(7)   NOT NULL,
    user_id     UUID,
    FOREIGN KEY (user_id) REFERENCES users (id)
);