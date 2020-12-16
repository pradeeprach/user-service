CREATE TABLE IF NOT EXISTS "user" (
	ID BIGSERIAL PRIMARY KEY,
	FIRSTNAME VARCHAR(100) NOT NULL,
	LASTNAME VARCHAR(100) NOT NULL,
	USERNAME VARCHAR(50) UNIQUE NOT NULL,
	PASSWORD VARCHAR(50) NOT NULL,
	PHONE_NUMBER VARCHAR(20) NOT NULL,
	EMAIL_ID VARCHAR(100) NOT NULL
);