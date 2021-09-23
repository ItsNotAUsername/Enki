CREATE TABLE usr (
  id       BIGSERIAL    NOT NULL PRIMARY KEY,
  username VARCHAR(31)  NOT NULL,
  email    VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  active   BOOLEAN      NOT NULL,
  code     UUID         NOT NULL,
  created  TIMESTAMP    NOT NULL,
  updated  TIMESTAMP    NOT NULL
);

INSERT INTO usr (username, email, password, active, code, created, updated)
     VALUES ('Nikita', 'nikita@yandex.com', 'PaSsWoRd', true, '9e057b2a-a9f4-4e7c-b62a-4a62e8e3243e', TIMESTAMP '2021-04-16 15:30:00', TIMESTAMP '2021-04-16 15:30:00'),
            ('Sasha',  'sasha@gmail.com',   'SecUrITy', true, 'b9351304-d326-4e90-a54b-c46516b134d3', TIMESTAMP '2021-04-20 10:20:00', TIMESTAMP '2021-05-01 21:50:00');
