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
    "cursosIds": List<Long>
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `409 - CONFLICT` - Aluno already exists (email already registered).

--- 
