# Curso Routes

## All routes start with `/curso`

---

### POST `/curso`

Request body expected:

   ```
   {
    "nome": String
    "descricao": String
    "professorId": Long
    "categoria": String
    "cargaHoraria": Integer
    }
   ```

Response body expected:

   ```
   {
    "id": Long
    "nome": String
    "descricao": String
    "professorId": Long
    "categoria": String
    "cargaHoraria": Integer
    "status": String ("ATIVO", "INATIVO", "EM_ANDAMENTO", "CONCLUIDO")
    }
   ```

#### Returns

* `201 - CREATED` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters (null parameters, size invalid).
* `409 - CONFLICT` - Curso already exists (same nome + professorId already registered).
