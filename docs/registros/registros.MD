# Registros Routes

## All routes starts with `/registrar`

--- 

### POST `/aluno`

Request body expected:

   ```
   {
    "nome": String
    "email": String
    "senha": String
    }
   ```

Response body expected:

   ```
   {
    "tokens": {
        "accessToken": String
        "refreshToken": String
    },
    "usuario": {
        "id": Long
        "nome": String
        "email": String
    }
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(nome/email/senha null or blank, email invalid, senha with less than 8 characters).
* `409 - CONFLICT` - Usuario already exists (email already registered).

--- 

### POST `/professor`

Request body expected:

   ```
   {
    "nome": String
    "email": String
    "senha": String
    }
   ```

Response body expected:

   ```
   {
    "tokens": {
        "accessToken": String
        "refreshToken": String
    },
    "usuario": {
        "id": Long
        "nome": String
        "email": String
    }
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(nome/email/senha null or blank, email invalid, senha with less than 8 characters).
* `409 - CONFLICT` - Usuario already exists (email already registered).

--- 
