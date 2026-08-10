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
    "id": Long
    "nome": String
    "email": String
    "cursosMatriculados": List<Long>
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(nome/email/senha null or blank, email invalid, senha with less than 8 characters).
* `409 - CONFLICT` - Aluno already exists (email already registered).

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
    "id": Long
    "nome": String
    "email": String
    "cursosMinistrados": List<Long>
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(nome/email/senha null or blank, email invalid, senha with less than 8 characters).
* `409 - CONFLICT` - Professor already exists (email already registered).

--- 
