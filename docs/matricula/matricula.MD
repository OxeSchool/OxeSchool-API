# Matricula Routes

## All routes starts with `/matricula`

--- 

### POST `/`

### Only with Aluno Role

Header expected:

``Authorization: Bearer <Access Token>``

Request body expected:

   ```
   {
    "idAluno": Long
    "idCurso": UUID
    }
   ```

Response body expected:

   ```
   {
    "id": UUID
    "idAluno": Long
    "idCurso": UUID
    "aulasConcluidas": List<UUID>
    "status": String //ATIVO or INATIVO
    "dataMatricula": String
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `404 - NOT FOUND` - Aluno not found | Curso not found.
* `409 - CONFLICT` - Matricula already exists for this aluno and curso.

--- 

### DELETE `/{id}`

### Only with Aluno Role

Header expected:

``Authorization: Bearer <Access Token>``

#### Returns

* `204 - NO CONTENT` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `404 - NOT FOUND` - Matricula not found.

--- 

### GET `/aluno/{idAluno}`

### Only with Aluno Role

Header expected:

``Authorization: Bearer <Access Token>``

Behavior expected:

`Only returns cursos with an ATIVO matricula and an ATIVO status.`

Response body expected:

   ```
   [
       {
            "idCurso": UUID
            "nomeCurso": String
            "status": String //ATIVO or INATIVO
            "dataMatricula": String
       }
   ]
   ```

#### Returns

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `404 - NOT FOUND` - Curso not found (for one of the enrolled cursos).

--- 

### GET `/curso/{idCurso}`

### Only with Professor Role

Pagination url (optional)

```/matricula/curso/{idCurso}?idProfessor=1```

``idProfessor`` - id of the professor making the request (required)

Behavior expected:

`Only returns alunos with an ATIVO matricula. Progresso is calculated as (aulasConcluidas / total de aulas do curso) * 100.`

Response body expected:

   ```
   [
       {
            "idAluno": Long
            "nome": String
            "email": String
            "status": String //ATIVO or INATIVO
            "progresso": Double
       }
   ]
   ```

#### Returns

* `200 - OK` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `403 - FORBIDDEN` - Curso does not belong to this professor.
* `404 - NOT FOUND` - Curso not found.

--- 
