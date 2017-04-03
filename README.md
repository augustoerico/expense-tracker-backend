# Expense Tracker - Backend

## Description

This is the backend application for the project Expense Tracker. Refer to [Expense Tracker - Frontend](https://github.com/augustoerico/expense-tracker-frontend) for complementary information.

## TODO

- [ ] delete expense entry
- [ ] add unit/integration tests to `Repository` class
- [ ] add `sign_out` endpoint

## FIXME

- [ ] use JWT with expiration
- [ ] return roles on `sign_in`
- [ ] running tests with ```bash ./gradlew build``` breaks; the tests run smoothly on IDE environment, though
- [ ] remove `/admin` routes; return data based on authenticated user permissions

## API

### Accounts

(*) optional argument

Path        | Method    | Arguments                                 | Description
------------|-----------|-------------------------------------------|---------------------------------------------------
`/sign_up`  | \[POST\]  | `username`, `password`, `accountType`*    | Creates an user account
`/sign_in`  | \[POST\]  | `username`, `password`                    | Creates an access token and return it

### Expenses

Path            | Method    | Arguments                             | Description
----------------|-----------|---------------------------------------|---------------------------------------------------
`/expenses`     | \[GET\]   |                                       | Returns all expenses for authenticated user
`/expenses`     | \[POST\]  | `amount`, `description`, `datetime`*  | Creates an expense entry for authenticated user
`/expenses/:id` | \[PUT\]   | `amount`, `description`, `datetime`*  | Updates an expense entry

### Expenses - Admin

Path                | Method    | Arguments | Description
--------------------|-----------|-----------|---------------------------------------------------------------------------
`/admin/expenses`   | \[GET\]   |           | Returns all expenses

## Running

### Env Vars

- `MONGO_DB_URI`: MongoDB URI
- `MONGO_DB_NAME`: MongoDB database name
- `KEYSTORE_PASSWORD`: Password for keystore file
- `KEYSTORE`: `<file>::<type>`

### Run

```bash ./gradle clean run```

The application will run on `http://localhost:3000`

### Hosted app

May be unavailable due hosting limitations

`https://expenses-tracker-backend.herokuapp.com`