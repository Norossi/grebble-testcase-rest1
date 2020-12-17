DROP TABLE IF EXISTS adverts;
DROP SEQUENCE IF EXISTS adverts_id_sequence;

CREATE TABLE adverts (
  id INT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  photos VARCHAR[] NOT NULL,
  description VARCHAR(1000),
  price INT NOT NULL,
  post_date TIMESTAMP NOT NULL
);

CREATE SEQUENCE adverts_id_sequence START 6;