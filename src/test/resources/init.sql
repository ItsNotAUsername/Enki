CREATE TYPE permission_scope AS ENUM ('workspace', 'project');
CREATE TYPE member_status    AS ENUM ('pending', 'approved');

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

CREATE TABLE permission (
  id    SERIAL           NOT NULL PRIMARY KEY,
  name  VARCHAR(31)      NOT NULL,
  scope permission_scope NOT NULL
);

CREATE TABLE role (
  id           BIGSERIAL   NOT NULL PRIMARY KEY,
  name         VARCHAR(31) NOT NULL,
  system       BOOLEAN     NOT NULL,
  workspace_id BIGINT
);

CREATE TABLE role_permission (
  role_id       BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,

  PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE role_scheme (
  id           BIGSERIAL   NOT NULL PRIMARY KEY,
  name         VARCHAR(31) NOT NULL,
  system       BOOLEAN     NOT NULL,
  workspace_id BIGINT
);

CREATE TABLE role_scheme_role (
  role_scheme_id BIGINT NOT NULL,
  role_id        BIGINT NOT NULL,

  PRIMARY KEY (role_scheme_id, role_id)
);

CREATE TABLE workspace (
  id             BIGSERIAL   NOT NULL PRIMARY KEY,
  name           VARCHAR(31) NOT NULL UNIQUE,
  owner_id       BIGINT      NOT NULL,
  role_scheme_id BIGINT      NOT NULL,
  created        TIMESTAMP   NOT NULL,
  updated        TIMESTAMP   NOT NULL
);

CREATE TABLE workspace_member (
  user_id      BIGINT        NOT NULL,
  workspace_id BIGINT        NOT NULL,
  role_id      BIGINT        NOT NULL,
  status       member_status NOT NULL,
  joined       TIMESTAMP     NOT NULL, 

  PRIMARY KEY (user_id, workspace_id)
);

ALTER TABLE role             ADD CONSTRAINT role_fk_workspace               FOREIGN KEY (workspace_id)   REFERENCES workspace (id);
ALTER TABLE role_scheme      ADD CONSTRAINT role_scheme_fk_workspace        FOREIGN KEY (workspace_id)   REFERENCES workspace (id);
ALTER TABLE role_permission  ADD CONSTRAINT role_permission_fk_role         FOREIGN KEY (role_id)        REFERENCES role (id);
ALTER TABLE role_permission  ADD CONSTRAINT role_permission_fk_permission   FOREIGN KEY (permission_id)  REFERENCES permission (id);
ALTER TABLE role_scheme_role ADD CONSTRAINT role_scheme_role_fk_role_scheme FOREIGN KEY (role_scheme_id) REFERENCES role_scheme (id);
ALTER TABLE role_scheme_role ADD CONSTRAINT role_scheme_role_fk_role        FOREIGN KEY (role_id)        REFERENCES role (id);
ALTER TABLE workspace        ADD CONSTRAINT workspace_fk_usr                FOREIGN KEY (owner_id)       REFERENCES usr (id);
ALTER TABLE workspace        ADD CONSTRAINT workspace_fk_role_scheme        FOREIGN KEY (role_scheme_id) REFERENCES role_scheme (id);
ALTER TABLE workspace_member ADD CONSTRAINT workspace_member_fk_usr         FOREIGN KEY (user_id)        REFERENCES usr (id);
ALTER TABLE workspace_member ADD CONSTRAINT workspace_member_fk_workspace   FOREIGN KEY (workspace_id)   REFERENCES workspace (id);
ALTER TABLE workspace_member ADD CONSTRAINT workspace_member_fk_role        FOREIGN KEY (role_id)        REFERENCES role (id);

INSERT INTO usr (username, email, password, active, code, created, updated)
     VALUES ('Nikita', 'nikita@yandex.com', 'PaSsWoRd', true, '9e057b2a-a9f4-4e7c-b62a-4a62e8e3243e', TIMESTAMP '2021-04-16 15:30:00', TIMESTAMP '2021-04-16 15:30:00'),
            ('Sasha',  'sasha@gmail.com',   'SecUrITy', true, 'b9351304-d326-4e90-a54b-c46516b134d3', TIMESTAMP '2021-04-20 10:20:00', TIMESTAMP '2021-05-01 21:50:00');

INSERT INTO permission (name, scope)
     VALUES ('create_project', 'workspace'),
            ('create_ticket', 'project');

INSERT INTO role (name, system, workspace_id)
     VALUES ('admin', true, null);

INSERT INTO role_permission (role_id, permission_id)
     VALUES (1, 1),
            (1, 2);

INSERT INTO role_scheme (name, system, workspace_id)
     VALUES ('default', true, null);

INSERT INTO role_scheme_role (role_scheme_id, role_id)
     VALUES (1, 1);

INSERT INTO workspace (name, owner_id, role_scheme_id, created, updated)
     VALUES ('JvmGang', 1, 1, TIMESTAMP '2021-04-16 16:30:00', TIMESTAMP '2021-04-16 16:30:00');

INSERT INTO workspace_member (user_id, workspace_id, role_id, status, joined)
     VALUES (1, 1, 1, 'approved', TIMESTAMP '2021-04-16 16:30:00');
