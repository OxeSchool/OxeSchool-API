# Curso Routes

## All routes starts with `/curso`

--- 

### GET `/{id}`

Response body expected:

   ```
   {
    "id": UUID
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
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
    "status": String //ATIVO or INATIVO
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
    "descricao": String
    "idProfessor": Long
    "categoria": String
    "cargaHoraria": Long
    }
   ```

Response body expected:

   ```
   {
    "id": UUID
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
    "modulos": []
    "status": String //ATIVO or INATIVO
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(nome/descricao/categoria/cargaHoraria null or blank).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `409 - CONFLICT` - This professor already has a curso with this nome.

--- 

### PUT `/{cursoId}`

### Only with Professor Role

Header expected:

``Authorization: Bearer <Access Token>``

Request body expected:

   ```
   {
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
    }
   ```

Response body expected:

   ```
   {
    "id": UUID
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
    "modulos": [ ... ]
    "status": String //ATIVO or INATIVO
    }
   ```

#### Returns

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(nome/descricao/categoria/cargaHoraria null or blank).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `403 - FORBIDDEN` - Curso does not belong to this professor.
* `404 - NOT FOUND` - Curso not found.

--- 

### PUT `/{cursoId}/status`

### Only with Professor Role

Header expected:

``Authorization: Bearer <Access Token>``

Request body expected:

   ```
   {
    "status": String //ATIVO or INATIVO
    }
   ```

Response body expected:

   ```
   {
    "id": UUID
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
    "modulos": [ ... ]
    "status": String //ATIVO or INATIVO
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(status null or blank).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `403 - FORBIDDEN` - Curso does not belong to this professor.
* `404 - NOT FOUND` - Curso not found.

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
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
    "modulos": [
        {
            "id": UUID
            "nome": String
            "aulas": []
        }
    ]
    "status": String //ATIVO or INATIVO
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `404 - NOT FOUND` - Curso not found.
* `409 - CONFLICT` - This curso already has a modulo with this nome.

--- 

### PUT `/{cursoId}/modulo/{moduloId}`

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
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
    "modulos": [ ... ]
    "status": String //ATIVO or INATIVO
    }
   ```

#### Returns

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(nome null or blank).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `403 - FORBIDDEN` - Curso does not belong to this professor.
* `404 - NOT FOUND` - Curso not found | Modulo not found.

--- 

### DELETE `/{cursoId}/modulo/{moduloId}`

### Only with Professor Role

Header expected:

``Authorization: Bearer <Access Token>``

Response body expected:

   ```
   {
    "id": UUID
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
    "modulos": [ ... ]
    "status": String //ATIVO or INATIVO
    }
   ```

#### Returns

* `204 - NO CONTENT` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `403 - FORBIDDEN` - Curso does not belong to this professor.
* `404 - NOT FOUND` - Curso not found | Modulo not found.

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
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
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
    "status": String //ATIVO or INATIVO
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `404 - NOT FOUND` - Curso not found | Modulo not found.
* `409 - CONFLICT` - This modulo already has an aula with the same titulo, texto and videoUrl.

--- 

### PUT `/{cursoId}/modulo/{moduloId}/aula/{aulaId}`

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
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
    "modulos": [ ... ]
    "status": String //ATIVO or INATIVO
    }
   ```

#### Returns

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(titulo/texto/videoUrl null or blank).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `403 - FORBIDDEN` - Curso does not belong to this professor.
* `404 - NOT FOUND` - Curso not found | Modulo not found | Aula not found.

--- 

### DELETE `/{cursoId}/modulo/{moduloId}/aula/{aulaId}`

### Only with Professor Role

Header expected:

``Authorization: Bearer <Access Token>``

Response body expected:

   ```
   {
    "id": UUID
    "idProfessor": Long
    "nome": String
    "descricao": String
    "categoria": String
    "cargaHoraria": Long
    "modulos": [ ... ]
    "status": String //ATIVO or INATIVO
    }
   ```

#### Returns

* `204 - NO CONTENT` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `403 - FORBIDDEN` - Curso does not belong to this professor.
* `404 - NOT FOUND` - Curso not found | Modulo not found | Aula not found.

--- 
