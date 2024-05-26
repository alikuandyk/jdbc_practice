CREATE TABLE cat
(
    id    SERIAL8 PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    color VARCHAR(255)
);

INSERT INTO cat (name, color)
VALUES ('Мурзик', 'серый');
INSERT INTO cat (name, color)
VALUES ('Барсик', 'коричневый');
INSERT INTO cat (name, color)
VALUES ('Васька', 'черный');
INSERT INTO cat (name, color)
VALUES ('Рыжик', 'рыжий');
INSERT INTO cat (name, color)
VALUES ('Снежок', 'белый');



CREATE TABLE artist
(
    id   SERIAL8 PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE album
(
    id        SERIAL8 PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    artist_id INT8 references artist (id)
);

CREATE TABLE song
(
    id        SERIAL8 PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    duration duration,
    album_id INT8 references album (id)
);

INSERT INTO artist (name) VALUES ('The Beatles');
INSERT INTO artist (name) VALUES ('Pink Floyd');
INSERT INTO artist (name) VALUES ('Led Zeppelin');
INSERT INTO artist (name) VALUES ('Queen');
INSERT INTO artist (name) VALUES ('The Rolling Stones');


INSERT INTO album (name, artist_id) VALUES ('Abbey Road', 1);
INSERT INTO album (name, artist_id) VALUES ('The Dark Side of the Moon', 2);
INSERT INTO album (name, artist_id) VALUES ('Led Zeppelin IV', 3);
INSERT INTO album (name, artist_id) VALUES ('A Night at the Opera', 4);
INSERT INTO album (name, artist_id) VALUES ('Let It Bleed', 5);

SELECT artist.id, artist.name, COUNT(album.id) AS album_count
FROM artist
JOIN album ON artist.id = album.artist_id
GROUP BY artist.id, artist.name
ORDER BY album_count DESC
LIMIT 1;

select artist.id, artist.name
from artist
left join album on artist.id = album.artist_id
where album.id is null;

select song.id, song.name, song.duration, song.album_id
from song
join album on song.album_id = album.id
where album.artist_id = ?;


