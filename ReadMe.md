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
curl -s -X POST -d '{"name":"New", "email":"new@gmail.com", "password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' curl -s http://localhost:8080/voting/admin/users --user admin@gmail.com:password

###### update User
curl -s -X PUT -d '{"name":"UpdatedName", "email":"updatedmail@gmail.com", "password":"updatedPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/users/100005 --user admin@gmail.com:password

###### get User by email
curl -s http://localhost:8080/voting/admin/users/by?email=simple@mail.ru --user admin@gmail.com:password

### error examples

###### not found User error
curl -s http://localhost:8080/voting/admin/users/1 --user admin@gmail.com:password

###### create User with duplicate email error
curl -s -X POST -d '{"name":"New", "email":"simple@mail.ru", "password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' curl -s http://localhost:8080/voting/admin/users --user admin@gmail.com:password

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
curl -s -X POST -d '{"name":"New", "email":"new@gmail.com", "password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' curl -s http://localhost:8080/voting/profile/register

###### update AuthorizedUser
curl -s -X PUT -d '{"name":"UpdatedName", "email":"updatedmail@gmail.com", "password":"updatedPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/profile --user simple@mail.ru:simple

###### delete AuthorizedUser
curl -s -X DELETE http://localhost:8080/voting/profile --user volk27@mail.ru:NuZayatsPogodi

<hr>

## /admin/restaurants

###### get all Restaurants (Only name list)
curl -s http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### get Restaurant by id with all DayMenus
curl -s http://localhost:8080/voting/admin/restaurants/103 --user admin@gmail.com:password

###### delete Restaurant by id
curl -s -X DELETE http://localhost:8080/voting/admin/restaurants/106 --user admin@gmail.com:password

###### create Restaurant
curl -s -X POST -d '{"name": "One More Bar"}' -H 'Content-Type:application/json;charset=UTF-8' curl -s http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### update Restaurant
curl -s -X PUT -d '{"id": "109", "name": "He is Always Here Bar"}' -H 'Content-Type:application/json;charset=UTF-8' curl -s http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### get all Restaurants by day with DayMenus
curl -s http://localhost:8080/voting/admin/restaurants/for?day=2019-07-01 --user admin@gmail.com:password

###### get Restaurant by id and day with DayMenu
curl -s http://localhost:8080/voting/admin/restaurants/102/for?day=2019-07-01 --user admin@gmail.com:password

### error examples

###### not found Restaurant error
curl -s http://localhost:8080/voting/admin/restaurants/99 --user admin@gmail.com:password

###### create Restaurant with duplicate name error
curl -s -X POST -d '{"name": "Why Not Bar"}' -H 'Content-Type:application/json;charset=UTF-8' curl -s http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### update Restaurant with duplicate name error
curl -s -X PUT -d '{"id": "109", "name": "Why Not Bar"}' -H 'Content-Type:application/json;charset=UTF-8' curl -s http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### create Restaurant with validation error
curl -s -X POST -d '{"name": "1"}' -H 'Content-Type:application/json;charset=UTF-8' curl -s http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### get all Restaurants with authentication error
curl -s http://localhost:8080/voting/admin/restaurants

###### get all Restaurant with forbidden error
curl -s http://localhost:8080/voting/admin/restaurants --user simple@mail.ru:simple

###### create Restaurant with unsafe http error
curl -s -X POST -d '{"name":"<script>alert(123)</script>"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

<hr>

## /restaurants

###### vote for Restaurant
curl -s -X POST http://localhost:8080/voting/restaurants/vote/108 --user simple@mail.ru:simple

###### vote for Restaurant second time (Till 11 a.m. o'clock - changed the decision, after 11 a.m. it will not work)
curl -s -X POST http://localhost:8080/voting/restaurants/vote/105 --user simple@mail.ru:simple
curl -s -X POST http://localhost:8080/voting/restaurants/vote/106 --user simple@mail.ru:simple

###### get all Dishes by Restaurant id;
curl -s http://localhost:8080/voting/restaurants/108/all --user simple@mail.ru:simple

###### get all Restaurants for current day with DayMenus
curl -s http://localhost:8080/voting/restaurants/ --user simple@mail.ru:simple

###### get Restaurant by id for current day with DayMenu
curl -s http://localhost:8080/voting/restaurants/105 --user simple@mail.ru:simple
