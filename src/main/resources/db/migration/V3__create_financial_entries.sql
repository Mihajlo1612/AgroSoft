CREATE TABLE financial_entries (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    farm_id BIGINT NOT NULL REFERENCES farms(id),
    type VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    amount NUMERIC(12, 2) NOT NULL,
    year INTEGER NOT NULL,
    month INTEGER NOT NULL,
    planned BOOLEAN NOT NULL,
    description VARCHAR(255)
);