CREATE TABLE IF NOT EXISTS credit_application (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(19, 2) NOT NULL,
    term_months INT NOT NULL,
    monthly_income DECIMAL(19, 2) NOT NULL,
    current_credit_load DECIMAL(19, 2) NOT NULL,
    credit_rating INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);