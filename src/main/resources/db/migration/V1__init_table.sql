CREATE TABLE users(
    seq_id SERIAL PRIMARY KEY,
    id UUID UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email  VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE roles(
    seq_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    role VARCHAR(255) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(seq_id)
);

CREATE TABLE rewards(
    seq_id SERIAL PRIMARY KEY,
    user_id SERIAL NOT NULL,
    total_cashback DOUBLE PRECISION NOT NULL,
    current_balance DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(seq_id)
);

CREATE TABLE cashback_history(
    seq_id SERIAL PRIMARY KEY,
    id UUID NOT NULL UNIQUE,
    user_id INTEGER NOT NULL,
    created_date DATE NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    description VARCHAR(255) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(seq_id)
);



