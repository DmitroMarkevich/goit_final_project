INSERT INTO users (id, username, email, password)
VALUES ('461e8eda-9e13-4051-a677-3dbf4ad5b2d8', 'test_username', 'vasya239@gmail.com', '$2a$12$2wVOpcl7nLkaZFqnKCdl0eIqjOozJOE7Dy5.S5KGnrPoQVTZPlrAK');
-- hashed password = test_password

INSERT INTO note (id, title, content, user_id, access_type)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Note Title', 'This is the content of the note.', '461e8eda-9e13-4051-a677-3dbf4ad5b2d8', 'PRIVATE');
