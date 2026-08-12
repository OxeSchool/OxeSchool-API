# Logout Routes

## All routes starts with `/logout`

--- 

### POST `/`

Header expected:

``Authorization: Bearer <Access Token>``

Request body expected:

   ```
   {
    "refreshToken": String
    }
   ```

#### Returns

* `204 - NO CONTENT` - Sucess.
* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid).
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.

--- 
