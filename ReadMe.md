# Restaurant Voting REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.
  
  This voting system is designed to decide where to have lunch.
  
  > 2 types of users: admin and regular users <br>
  > Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price) <br>
  > Menu changes each day (admins do the updates) <br>
  > Users can vote on which restaurant they want to have lunch at <br>
  > Only one vote counted per user <br>
  > Each restaurant provides new menu each day <br>
  > If user votes again the same day:
  > - If it is before 11:00 we asume that he changed his mind <br>
  > - If it is after 11:00 then it is too late, vote can't be changed <br>

# curl samples

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
curl -s -X POST -d '{"name": "Yummy"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/109/dishes --user admin@gmail.com:password

###### update Dish for Restaurant
curl -s -X PUT -d '{"id": "1027", "name": "Neapolitan Pizza"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/109/dishes --user admin@gmail.com:password

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
curl -s -X POST -d '{"price": "555"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/108/menus/1023 --user admin@gmail.com:password

###### update DayMenu for Restaurant
curl -s -X PUT -d '{"id": "10039", "menuDate": "2019-08-10", "price": "333"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/109/menus/1026 --user admin@gmail.com:password

### votes

###### get all Votes for current day for Restaurant (creating DayMenu for today and voting before seeing votes) 
curl -s -X POST -d '{"price": "500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/108/menus/1023 --user admin@gmail.com:password 
curl -s -X POST http://localhost:8080/voting/restaurants/108/vote --user simple@mail.ru:simple 
curl -s http://localhost:8080/voting/admin/restaurants/108/votes --user admin@gmail.com:password

###### get all Votes for day for Restaurant
curl -s http://localhost:8080/voting/admin/restaurants/108/votes/for?day=2019-07-03 --user admin@gmail.com:password

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
curl -s -X POST -d '{"id": "1000012", "name": "Yummy"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/109/dishes --user admin@gmail.com:password

###### create duplicate DayMenu for Restaurant
curl -s -X POST -d '{"menuDate": "2019-07-01", "price": "777"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/101/menus/1001 --user admin@gmail.com:password

###### update duplicate DayMenu for Restaurant
curl -s -X PUT -d '{"id": "10001", "menuDate": "2019-07-01", "price": "777"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/101/menus/1002 --user admin@gmail.com:password

###### delete not found DayMenu for Restaurant by id
curl -s -X DELETE http://localhost:8080/voting/admin/restaurants/107/menus/10001 --user admin@gmail.com:password

<hr>

## /admin/votes

###### get all Votes for current day
curl -s http://localhost:8080/voting/admin/votes --user admin@gmail.com:password

###### get all Votes for day
curl -s http://localhost:8080/voting/admin/votes/for?day=2019-07-02 --user admin@gmail.com:password

###### get Vote by id
curl -s http://localhost:8080/voting/admin/votes/50009 --user admin@gmail.com:password

<hr>

## /restaurants

###### vote for Restaurant (create DayMenu for today before voting)
curl -s -X POST -d '{"price": "500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/108/menus/1023 --user admin@gmail.com:password <br>
curl -s -X POST http://localhost:8080/voting/restaurants/108/vote --user simple@mail.ru:simple

###### vote for Restaurant second time (Till 11 a.m. o'clock - changed the decision, after 11 a.m. it will not work)(create DayMenu for today before voting)
curl -s -X POST -d '{"price": "500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/108/menus/1023 --user admin@gmail.com:password <br>
curl -s -X POST http://localhost:8080/voting/restaurants/108/vote --user simple@mail.ru:simple <br>
curl -s -X POST -d '{"price": "500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/106/menus/1017 --user admin@gmail.com:password <br>
curl -s -X POST http://localhost:8080/voting/restaurants/106/vote --user simple@mail.ru:simple <br>

###### get all Restaurants for current day with DayMenus
curl -s http://localhost:8080/voting/restaurants/ --user simple@mail.ru:simple

###### get Restaurant by id for current day with DayMenu
curl -s http://localhost:8080/voting/restaurants/105 --user simple@mail.ru:simple

###### get all DayMenus for Restaurant for current day
curl -s http://localhost:8080/voting/restaurants/104/menus --user simple@mail.ru:simple

###### get all DayMenus for current day
curl -s http://localhost:8080/voting/restaurants/menus --user simple@mail.ru:simple

### votes

###### get all Votes for current day for current User (creating DayMenu for today and voting before seeing votes) 
curl -s -X POST -d '{"price": "500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/108/menus/1023 --user admin@gmail.com:password 
curl -s -X POST http://localhost:8080/voting/restaurants/108/vote --user admin@gmail.com:password
curl -s http://localhost:8080/voting/restaurants/votes --user admin@gmail.com:password

<hr>

## Full Case

###### create new restaurant
curl -s -X POST -d '{"name": "The Most Amazing Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants --user admin@gmail.com:password

###### check
curl -s http://localhost:8080/voting/admin/restaurants/100000 --user admin@gmail.com:password

###### create new dish
curl -s -X POST -d '{"name": "The Most Delicious Rib Eye Steak"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/100000/dishes --user admin@gmail.com:password

###### check
curl -s http://localhost:8080/voting/admin/restaurants/100000/dishes --user admin@gmail.com:password

###### create new DayMenu
curl -s -X POST -d '{"price": "100500"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/admin/restaurants/100000/menus/100001 --user admin@gmail.com:password

###### check
curl -s http://localhost:8080/voting/admin/restaurants/100000 --user admin@gmail.com:password

###### voting for new DayMenu
curl -s -X POST http://localhost:8080/voting/restaurants/100000/vote --user admin@gmail.com:password

###### check
curl -s http://localhost:8080/voting/admin/votes/100003 --user admin@gmail.com:password