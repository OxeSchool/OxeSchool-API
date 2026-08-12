# Refresh Routes

## All routes starts with `/refresh`

--- 

### POST `/`

Header expected:

``Authorization: Bearer <Refresh Token>``

Behavior expected:

`This route does not go through the auth filter, only the refresh token itself is validated.`

Response body expected:

   ```
   {
    "accessToken": String
    "refreshToken": String
    }
   ```

#### Returns

* `200 - OK` - Sucess.
* `401 - UNAUTHORIZED` - token invalid, expired or in blacklist.
* `400 - BAD REQUEST` - Invalid format.

--- 
