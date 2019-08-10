# curl samples
<hr>

## /admin/users

###### get all Users
curl -s http://localhost:8080/voting/admin/users --user admin@gmail.com:password

###### get User by id
curl -s http://localhost:8080/voting/admin/users/16 --user admin@gmail.com:password

###### delete User by id
curl -s -X DELETE http://localhost:8080/voting/admin/users/13 --user admin@gmail.com:password

###### create User
curl -s -X POST -d '{"name":"New", "email":"new@gmail.com", "password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/users --user admin@gmail.com:password

###### update User
curl -s -X PUT -d '{"name":"UpdatedName", "email":"updatedmail@gmail.com", "password":"updatedPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/users/100005 --user admin@gmail.com:password

###### get User by email
curl -s http://localhost:8080/voting/admin/users/by?email=simple@mail.ru --user admin@gmail.com:password

### error examples

###### not found User error
curl -s http://localhost:8080/voting/admin/users/1 --user admin@gmail.com:password

###### create User with duplicate email error
curl -s -X POST -d '{"name":"New", "email":"simple@mail.ru", "password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/users --user admin@gmail.com:password

###### update User with validation error
curl -s -X PUT -d '{"name":"UpdatedName", "password":"updatedPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/users/100005 --user admin@gmail.com:password

###### get all Users with authentication error
curl -s http://localhost:8080/voting/admin/users/

###### get all Users with forbidden error
curl -s http://localhost:8080/voting/admin/users/ --user simple@mail.ru:simple

###### update User with unsafe http error
curl -s -X PUT -d '{"name":"<script>alert(123)</script>", "email":"new@gmail.com", "password":"updatedPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/users/100005 --user admin@gmail.com:password

<hr>

## /profile

###### get AuthorizedUser
curl -s http://localhost:8080/voting/profile --user simple@mail.ru:simple

###### register new User
curl -s -X POST -d '{"name":"New", "email":"new@gmail.com", "password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/profile/register

###### update AuthorizedUser
curl -s -X PUT -d '{"name":"UpdatedName", "email":"updatedmail@gmail.com", "password":"updatedPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/profile --user simple@mail.ru:simple

###### delete AuthorizedUser
curl -s -X DELETE http://localhost:8080/voting/profile --user volk27@mail.ru:NuZayatsPogodi

<hr>

## /admin/restaurants

###### get all Restaurants (without links)
curl -s http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### get Restaurant by id with all DayMenus
curl -s http://localhost:8080/voting/admin/restaurants/103 --user admin@gmail.com:password

###### delete Restaurant by id
curl -s -X DELETE http://localhost:8080/voting/admin/restaurants/106 --user admin@gmail.com:password

###### create Restaurant
curl -s -X POST -d '{"name": "One More Bar"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### update Restaurant
curl -s -X PUT -d '{"id": "109", "name": "He is Always Here Bar"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### get all Restaurants by day with DayMenus
curl -s http://localhost:8080/voting/admin/restaurants/for?day=2019-07-01 --user admin@gmail.com:password

###### get Restaurant by id and day with DayMenu
curl -s http://localhost:8080/voting/admin/restaurants/102/for?day=2019-07-01 --user admin@gmail.com:password

### statistics

###### get all Restaurants with statistics
curl -s http://localhost:8080/voting/admin/restaurants/stat --user admin@gmail.com:password

###### get all Restaurants for current day with statistics
curl -s http://localhost:8080/voting/admin/restaurants/stat/for?day=2019-07-03 --user admin@gmail.com:password

###### get quantity of votes for Restaurant
curl -s http://localhost:8080/voting/admin/restaurants/103/stat --user admin@gmail.com:password

###### get quantity of votes for Restaurant for current day
curl -s http://localhost:8080/voting/admin/restaurants/103/stat/for?day=2019-07-03 --user admin@gmail.com:password

### dishes

###### get all Dishes (without links)
curl -s http://localhost:8080/voting/admin/restaurants/dishes --user admin@gmail.com:password

###### get all Dishes for Restaurant
curl -s http://localhost:8080/voting/admin/restaurants/102/dishes --user admin@gmail.com:password

###### get Dish for Restaurant by id
curl -s http://localhost:8080/voting/admin/restaurants/102/dishes/1006 --user admin@gmail.com:password

###### delete Dish for Restaurant by id
curl -s -X DELETE http://localhost:8080/voting/admin/restaurants/109/dishes/1025 --user admin@gmail.com:password

###### create Dish for Restaurant
curl -s -X POST -d '{"name": "Yummy", "price": "100500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/109/dishes --user admin@gmail.com:password

###### update Dish for Restaurant
curl -s -X PUT -d '{"id": "1027", "name": "Neapolitan Pizza", "price": "500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/109/dishes --user admin@gmail.com:password

### menus

###### get all DayMenus (without links)
curl -s http://localhost:8080/voting/admin/restaurants/menus --user admin@gmail.com:password

###### get all DayMenus for Restaurant
curl -s http://localhost:8080/voting/admin/restaurants/104/menus --user admin@gmail.com:password

###### get all DayMenus for Restaurant for day
curl -s http://localhost:8080/voting/admin/restaurants/104/menus/for?day=2019-07-02 --user admin@gmail.com:password

###### get all DayMenus for day
curl -s http://localhost:8080/voting/admin/restaurants/menus/for?day=2019-07-02 --user admin@gmail.com:password

###### get DayMenu for Restaurant by id
curl -s http://localhost:8080/voting/admin/restaurants/102/menus/10017 --user admin@gmail.com:password

###### delete DayMenu for Restaurant by id
curl -s -X DELETE http://localhost:8080/voting/admin/restaurants/107/menus/10037 --user admin@gmail.com:password

###### delete all DayMenus for Restaurant for day
curl -s -X DELETE http://localhost:8080/voting/admin/restaurants/101/menus/for?day=2019-07-01 --user admin@gmail.com:password

###### create DayMenu for Restaurant
curl -s -X POST -d '{"menuDate": "2019-08-10"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/108/menus/1023 --user admin@gmail.com:password

###### update DayMenu for Restaurant
curl -s -X PUT -d '{"id": "10039", "menuDate": "2019-08-10"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/109/menus/1026 --user admin@gmail.com:password

### error examples

###### not found Restaurant error
curl -s http://localhost:8080/voting/admin/restaurants/99 --user admin@gmail.com:password

###### create Restaurant with duplicate name error
curl -s -X POST -d '{"name": "Why Not Bar"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### update Restaurant with duplicate name error
curl -s -X PUT -d '{"id": "109", "name": "Why Not Bar"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### create Restaurant with validation error
curl -s -X POST -d '{"name": "1"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### get all Restaurants with authentication error
curl -s http://localhost:8080/voting/admin/restaurants

###### get all Restaurant with forbidden error
curl -s http://localhost:8080/voting/admin/restaurants --user simple@mail.ru:simple

###### create Restaurant with unsafe http error
curl -s -X POST -d '{"name":"<script>alert(123)</script>"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### get Dish for not this Restaurant by id
curl -s http://localhost:8080/voting/admin/restaurants/107/dishes/1006 --user admin@gmail.com:password

###### create not new Dish
curl -s -X POST -d '{"id": "1000012", "name": "Yummy", "price": "100500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/109/dishes --user admin@gmail.com:password

###### create duplicate DayMenu for Restaurant
curl -s -X POST -d '{"menuDate": "2019-07-01"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/101/menus/1001 --user admin@gmail.com:password

###### update duplicate DayMenu for Restaurant
curl -s -X PUT -d '{"id": "10001", "menuDate": "2019-07-01"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/101/menus/1002 --user admin@gmail.com:password

###### delete not found DayMenu for Restaurant by id
curl -s -X DELETE http://localhost:8080/voting/admin/restaurants/107/menus/10001 --user admin@gmail.com:password

<hr>

## /restaurants

###### vote for Restaurant
curl -s -X POST http://localhost:8080/voting/restaurants/vote/108 --user simple@mail.ru:simple

###### vote for Restaurant second time (Till 11 a.m. o'clock - changed the decision, after 11 a.m. it will not work)
curl -s -X POST http://localhost:8080/voting/restaurants/vote/105 --user simple@mail.ru:simple
curl -s -X POST http://localhost:8080/voting/restaurants/vote/106 --user simple@mail.ru:simple

###### get all Restaurants for current day with DayMenus
curl -s http://localhost:8080/voting/restaurants/ --user simple@mail.ru:simple

###### get Restaurant by id for current day with DayMenu
curl -s http://localhost:8080/voting/restaurants/105 --user simple@mail.ru:simple

###### get all Restaurant for current day with statistics
curl -s http://localhost:8080/voting/restaurants/stat --user simple@mail.ru:simple

###### get quantity of votes for Restaurant for current day
curl -s http://localhost:8080/voting/restaurants/105/stat --user simple@mail.ru:simple

###### get all Dishes by Restaurant id;
curl -s http://localhost:8080/voting/restaurants/108/dishes --user simple@mail.ru:simple

###### get all DayMenus for Restaurant for current day
curl -s http://localhost:8080/voting/restaurants/104/menus --user simple@mail.ru:simple

###### get all DayMenus for current day
curl -s http://localhost:8080/voting/restaurants/menus --user simple@mail.ru:simple