DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('admin', 'admin@gmail.com', '{noop}password'),
  ('hotab', 'hotab58@yandex.ru', '{noop}oVolka'),
  ('volk', 'volk27@mail.ru', '{noop}NuZayatsPogodi'),
  ('ninja', 'shadowninja@gmail.com', '{noop}youwillnotseeme'),
  ('hotgirl', 'hotgirl69@loveplanet.net', '{noop}NoMoneyNoHoney'),
  ('batman', 'batmanofghotem@yandex.ru', '{noop}ImBatman'),
  ('spiderman', 'friendlyspidey@gmail.com', '{noop}thegreatpoweristhegreatresponsibility'),
  ('pussinboots', 'pussinboots@mail.ru','{noop}123myau123'),
  ('simple', 'simple@mail.ru', '{noop}simple');

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