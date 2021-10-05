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

CREATE TYPE permission_scope AS ENUM ('workspace', 'project');

CREATE TYPE member_status AS ENUM ('pending', 'approved');

CREATE TABLE permission (
  id    SERIAL           NOT NULL PRIMARY KEY,
  name  VARCHAR(31)      NOT NULL,
  scope permission_scope NOT NULL
);

INSERT INTO permission (name, scope)
     VALUES ('create_project', 'workspace'),
            ('create_ticket', 'project');

CREATE TABLE role (
  id     BIGSERIAL   NOT NULL PRIMARY KEY,
  name   VARCHAR(31) NOT NULL,
  system BOOLEAN     NOT NULL
);

INSERT INTO role (name, system)
     VALUES ('admin', true),
            ('custom_role', false);

CREATE TABLE role_permission (
  role_id       BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,

  PRIMARY KEY (role_id, permission_id),
  
  FOREIGN KEY (role_id)       REFERENCES       role (id) ON DELETE CASCADE,
  FOREIGN KEY (permission_id) REFERENCES permission (id)
);

INSERT INTO role_permission (role_id, permission_id)
     VALUES (1, 1),
            (1, 2),
            (2, 2);
