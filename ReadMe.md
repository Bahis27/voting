### curl samples

#### get All Users
`curl -s http://localhost:8080/voting/admin/users --user admin@gmail.com:password`

#### get User with ID 100006
`curl -s http://localhost:8080/voting/admin/users/100006 --user admin@gmail.com:password`

#### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/topjava/admin/users --user admin@gmail.com:password`
