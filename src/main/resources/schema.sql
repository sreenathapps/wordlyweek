-- Create tables
DROP TABLE IF EXISTS writer_magazine;
DROP TABLE IF EXISTS WRITER;
DROP TABLE IF EXISTS MAGAZINE;

CREATE TABLE Writer (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  bio VARCHAR(255)
);

CREATE TABLE Magazine (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255),
  publicationDate DATE
);

CREATE TABLE writer_magazine (
  writerId INT,
  magazineId INT,
  PRIMARY KEY (writerId, magazineId),
  FOREIGN KEY (writerId) REFERENCES Writer(id),
  FOREIGN KEY (magazineId) REFERENCES Magazine(id)
);