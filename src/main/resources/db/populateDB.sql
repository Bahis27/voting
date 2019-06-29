DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('admin', 'admin@gmail.com', 'password'),
  ('hotab', 'hotab58@yandex.ru', 'oVolka'),
  ('volk', 'volk27@mail.ru', 'NuZayatsPogodi'),
  ('ninja', 'shadowninja@gmail.com', 'youwillnotseeme'),
  ('hotgirl', 'hotgirl69@loveplanet.net', 'NoMoneyNoHoney'),
  ('batman', 'batmanofghotem@yandex.ru', 'ImBatman'),
  ('spiderman', 'friendlyspidey@gmail.com', 'thegreatpoweristhegreatresponsibility'),
  ('pussinboots', 'pussinboots@mail.ru','123myau123'),
  ('simple', 'simple@mail.ru', 'simple');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_ADMIN', 100000),
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_USER', 100003),
  ('ROLE_USER', 100004),
  ('ROLE_USER', 100005),
  ('ROLE_USER', 100006),
  ('ROLE_USER', 100007),
  ('ROLE_USER', 100008);