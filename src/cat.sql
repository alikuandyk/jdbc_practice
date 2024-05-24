CREATE TABLE cat
(
    id    SERIAL8 PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    color VARCHAR(255)
);

INSERT INTO cat (name, color) VALUES ('Мурзик', 'серый');
INSERT INTO cat (name, color) VALUES ('Барсик', 'коричневый');
INSERT INTO cat (name, color) VALUES ('Васька', 'черный');
INSERT INTO cat (name, color) VALUES ('Рыжик', 'рыжий');
INSERT INTO cat (name, color) VALUES ('Снежок', 'белый');
