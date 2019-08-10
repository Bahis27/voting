DELETE
FROM user_roles;

DELETE
FROM votes;

DELETE
FROM day_menus;

DELETE
FROM users;

DELETE
FROM dishes;

DELETE
FROM restaurants;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (id, name, email, password)
VALUES (10, 'admin', 'admin@gmail.com', '{noop}password'),
       (11, 'hotab', 'hotab58@yandex.ru', '{noop}oVolka'),
       (12, 'volk', 'volk27@mail.ru', '{noop}NuZayatsPogodi'),
       (13, 'ninja', 'shadowninja@gmail.com', '{noop}youwillnotseeme'),
       (14, 'hotgirl', 'hotgirl69@loveplanet.net', '{noop}NoMoneyNoHoney'),
       (15, 'batman', 'batmanofghotem@yandex.ru', '{noop}ImBatman'),
       (16, 'spiderman', 'friendlyspidey@gmail.com', '{noop}thegreatpoweristhegreatresponsibility'),
       (17, 'pussinboots', 'pussinboots@mail.ru', '{noop}123myau123'),
       (18, 'simple', 'simple@mail.ru', '{noop}simple');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_ADMIN', 10),
       ('ROLE_USER', 10),
       ('ROLE_USER', 11),
       ('ROLE_USER', 12),
       ('ROLE_USER', 13),
       ('ROLE_USER', 14),
       ('ROLE_USER', 15),
       ('ROLE_USER', 16),
       ('ROLE_USER', 17),
       ('ROLE_USER', 18);

INSERT INTO restaurants (id, name)
VALUES (101, 'Kale Me Crazy'),
       (102, 'Wok And Roll'),
       (103, 'Life of Pie'),
       (104, 'Lord of the Fries'),
       (105, 'Pita Pan'),
       (106, 'Why Not Bar'),
       (107, 'Planet of the Crepes'),
       (108, 'Thai Tanic'),
       (109, 'He is Not Here Bar');

INSERT INTO dishes(id, name, price, restaurant_id)
VALUES (1001, 'French Fries', 100, 101),
       (1002, 'Beef Burger', 210, 101),
       (1003, 'Chicken Wings', 250, 101),
       (1004, 'Signature Wok', 320, 102),
       (1005, 'Yakisoba Noodles', 230, 102),
       (1006, 'Salmon Rolls', 350, 102),
       (1007, 'Meet Pie', 350, 103),
       (1008, 'Sweet Pie', 320, 103),
       (1009, 'Just Pie', 300, 103),
       (1010, 'Greek Chicken Pasta', 270, 104),
       (1011, 'Tuscan Bean Soup', 240, 104),
       (1012, 'French Fries', 90, 104),
       (1013, 'Big Beer', 120, 105),
       (1014, 'French Fries', 90, 105),
       (1015, 'Spicy Basque Chicken and Rice', 350, 105),
       (1016, 'French Fries', 100, 106),
       (1017, 'Salmon Provencal', 450, 106),
       (1018, 'Moroccan Chicken Skillet', 240, 106),
       (1019, 'French Fries', 100, 107),
       (1020, 'CheeseBurger', 270, 107),
       (1021, 'Pizza 4 Cheeses', 340, 107),
       (1022, 'Thai Sweet Potato Soup', 390, 108),
       (1023, 'Tom Yum Goong', 490, 108),
       (1024, 'Green Curry', 420, 108),
       (1025, 'French Fries', 120, 109),
       (1026, 'Root Beer', 140, 109),
       (1027, 'New York Pizza', 370, 109);

INSERT INTO day_menus(id, menu_day, restaurant_id, dish_id)
VALUES (10001, DATE '2019-7-1', 101, 1001), -- 01.07.2019
       (10002, DATE '2019-7-1', 101, 1002),
       (10003, DATE '2019-7-1', 101, 1003),
       (10004, DATE '2019-7-1', 102, 1004),
       (10005, DATE '2019-7-1', 103, 1007),
       (10006, DATE '2019-7-1', 103, 1008),
       (10007, DATE '2019-7-1', 104, 1012),
       (10008, DATE '2019-7-1', 105, 1014),
       (10009, DATE '2019-7-1', 107, 1019),
       (10010, DATE '2019-7-1', 107, 1020),
       (10011, DATE '2019-7-1', 107, 1021),
       (10012, DATE '2019-7-1', 109, 1025),
       (10013, DATE '2019-7-1', 109, 1026),
       (10014, DATE '2019-7-2', 101, 1001), -- 02.07.2019
       (10015, DATE '2019-7-2', 101, 1002),
       (10016, DATE '2019-7-2', 102, 1005),
       (10017, DATE '2019-7-2', 102, 1004),
       (10018, DATE '2019-7-2', 103, 1009),
       (10019, DATE '2019-7-2', 103, 1008),
       (10020, DATE '2019-7-2', 104, 1012),
       (10021, DATE '2019-7-2', 105, 1014),
       (10022, DATE '2019-7-2', 105, 1015),
       (10023, DATE '2019-7-2', 107, 1020),
       (10024, DATE '2019-7-2', 108, 1023),
       (10025, DATE '2019-7-2', 109, 1025),
       (10026, DATE '2019-7-2', 108, 1022),
       (10027, DATE '2019-7-3', 101, 1001), -- 03.07.2019
       (10028, DATE '2019-7-3', 102, 1006),
       (10029, DATE '2019-7-3', 101, 1003),
       (10030, DATE '2019-7-3', 102, 1004),
       (10031, DATE '2019-7-3', 105, 1013),
       (10032, DATE '2019-7-3', 103, 1008),
       (10033, DATE '2019-7-3', 104, 1012),
       (10034, DATE '2019-7-3', 108, 1022),
       (10035, DATE '2019-7-3', 108, 1023),
       (10036, DATE '2019-7-3', 108, 1024),
       (10037, DATE '2019-7-3', 107, 1021),
       (10038, DATE '2019-7-3', 109, 1027),
       (10039, DATE '2019-7-3', 109, 1026);

INSERT INTO votes(id, vote_day, user_id, restaurant_id)
VALUES (50001, DATE '2019-7-1', 18, 109), -- 01.07.2019
       (50002, DATE '2019-7-1', 11, 101),
       (50003, DATE '2019-7-1', 12, 103),
       (50004, DATE '2019-7-1', 13, 105),
       (50005, DATE '2019-7-1', 14, 103),
       (50006, DATE '2019-7-1', 15, 103),
       (50007, DATE '2019-7-1', 16, 101),
       (50008, DATE '2019-7-1', 17, 108),
       (50009, DATE '2019-7-2', 18, 109), -- 02.07.2019
       (50010, DATE '2019-7-2', 11, 107),
       (50011, DATE '2019-7-2', 12, 103),
       (50012, DATE '2019-7-2', 13, 106),
       (50013, DATE '2019-7-2', 14, 103),
       (50014, DATE '2019-7-2', 15, 105),
       (50015, DATE '2019-7-2', 16, 101),
       (50016, DATE '2019-7-2', 17, 102),
       (50017, DATE '2019-7-3', 18, 103), -- 03.07.2019
       (50018, DATE '2019-7-3', 11, 103),
       (50019, DATE '2019-7-3', 12, 103),
       (50020, DATE '2019-7-3', 13, 108),
       (50021, DATE '2019-7-3', 14, 108),
       (50022, DATE '2019-7-3', 15, 108),
       (50023, DATE '2019-7-3', 16, 101),
       (50024, DATE '2019-7-3', 17, 104);