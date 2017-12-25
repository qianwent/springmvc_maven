INSERT INTO users(username,password,enabled)
VALUES ('wqian','123456', true);
INSERT INTO users(username,password,enabled)
VALUES ('wentao','123456', true);

INSERT INTO user_roles (username, role)
VALUES ('wqian', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('wqian', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('wentao', 'ROLE_USER');