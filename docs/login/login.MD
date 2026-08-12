# Login Routes

## All routes starts with `/login`

--- 

### POST `/aluno`

Request body expected:

   ```
   {
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

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `404 - NOT FOUND` - Aluno not found | invalid password.

--- 

### POST `/professor`

Request body expected:

   ```
   {
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

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `404 - NOT FOUND` - Professor not found | invalid password.

--- 
