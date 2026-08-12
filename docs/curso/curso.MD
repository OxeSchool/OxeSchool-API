# Curso Routes

## All routes starts with `/curso`

--- 

### GET `/{id}`

Response body expected:

   ```
   {
    "id": UUID
    "nome": String
    "idProfessor": Long
    "modulos": [
        {
            "id": UUID
            "nome": String
            "aulas": [
                {
                    "id": UUID
                    "titulo": String
                    "texto": String
                    "videoUrl": String
                }
            ]
        }
    ]
    }
   ```

#### Returns

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `404 - NOT FOUND` - Curso not found.

--- 

### POST `/`

### Only with Professor Role

Header expected:

``Authorization: Bearer <Access Token>``

Request body expected:

   ```
   {
    "nome": String
    "idProfessor": Long
    }
   ```

Response body expected:

   ```
   {
    "id": UUID
    "nome": String
    "idProfessor": Long
    "modulos": []
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `409 - CONFLICT` - This professor already has a curso with this nome.

--- 

### POST `/{cursoId}/modulo`

### Only with Professor Role

Header expected:

``Authorization: Bearer <Access Token>``

Request body expected:

   ```
   {
    "nome": String
    }
   ```

Response body expected:

   ```
   {
    "id": UUID
    "nome": String
    "idProfessor": Long
    "modulos": [
        {
            "id": UUID
            "nome": String
            "aulas": []
        }
    ]
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `404 - NOT FOUND` - Curso not found.
* `409 - CONFLICT` - This curso already has a modulo with this nome.

--- 

### POST `/{cursoId}/modulo/{moduloId}/aula`

### Only with Professor Role

Header expected:

``Authorization: Bearer <Access Token>``

Request body expected:

   ```
   {
    "titulo": String
    "texto": String
    "videoUrl": String
    }
   ```

Response body expected:

   ```
   {
    "id": UUID
    "nome": String
    "idProfessor": Long
    "modulos": [
        {
            "id": UUID
            "nome": String
            "aulas": [
                {
                    "id": UUID
                    "titulo": String
                    "texto": String
                    "videoUrl": String
                }
            ]
        }
    ]
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `404 - NOT FOUND` - Curso not found | Modulo not found.
* `409 - CONFLICT` - This modulo already has an aula with the same titulo, texto and videoUrl.

--- 
