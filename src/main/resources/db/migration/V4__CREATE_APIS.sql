CREATE TABLE APIs(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20),
    endpoint VARCHAR(100)
);



CREATE TABLE Users_APIs(
    user_id BIGSERIAL REFERENCES Users(id),
    api_id BIGSERIAL REFERENCES APIs(id),
    PRIMARY KEY(user_id, api_id)
);
