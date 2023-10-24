CREATE TABLE Users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    email VARCHAR CHECK(email LIKE '%@%' AND email LIKE '%.%')
);