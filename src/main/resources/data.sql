
INSERT INTO Writer (id, name, bio) VALUES
  (1, 'John Doe', 'Famous writer of fantasy tales'),
  (2, 'Jane Smith', 'Renowned journalist and editor'),
  (3, 'Emily Brontë', 'Author of Wuthering Heights'),
  (4, 'Ernest Hemingway', 'Nobel Prize-winning author known for works like The Old Man and the Sea');

INSERT INTO Magazine (id, title, publicationDate) VALUES
  (1, 'Fantasy Tales', '2023-10-05'),
  (2, 'Journalist Weekly', '2023-09-15'),
  (3, 'Classic Literature Monthly', '2023-10-15'),
  (4, 'Modern Writers Digest', '2023-09-20');

INSERT INTO writer_magazine (writerId, magazineId) VALUES
  (1, 1),
  (1, 2),
  (2, 2),
  (3, 3),
  (4, 3),
  (4, 4);