CREATE TABLE farms (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    size_in_hectars NUMERIC(10, 2) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id)
);