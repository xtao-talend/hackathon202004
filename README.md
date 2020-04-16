HACKATHON 2020
==========================================================================================================================================================================================

Typelevel Stack QuickStart
--------------------------

1. Install sbt
2. `cd hackatonxtao042020`
3. Install PostgreSQL and configure access for user `postgres` and password `postgres` (or change it in `Module`)
4. Create database `hackathonxtao` and table `users` (see `src/main/resources/V1_Create_User_Table.sql` or use `Flyway` as in the tests).
5. `sbt run`

About structure
--------------

- `repository` package: Defines operation with database.
- `HttpErrorHandler`: Mapping business errors to http responses in a single place.
- `http` package: Includes all authentication httpserver oauth etc.
- `validation` object: Includes fields validation using `cats.data.Validated`.
- `Config`: server and database configuration.
- `Server`: The main application that wires all the components and starts the web server.
- `model`: all models in project.

Acknowledgement
-------------

Inspired greatly by https://github.com/profunktor/typelevel-stack.g8.git
