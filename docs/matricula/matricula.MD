# Matricula Routes

## All routes starts with `/matricula`

--- 

### POST `/`

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
* `404 - NOT FOUND` - Aluno not found | Curso not found.
* `409 - CONFLICT` - Matricula already exists for this aluno and curso.

--- 

### DELETE `/{id}`

#### Returns

* `204 - NO CONTENT` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `404 - NOT FOUND` - Matricula not found.

--- 
