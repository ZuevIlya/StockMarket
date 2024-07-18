### Регистрация нового пользователя
| Method | URL | Request |
| --- | --- | --- |
| POST | /auth/registration | <br/><pre lang="json">{&#13; "username": "some name", &#13; "password": "some hash pass", &#13; "email": "some email", &#13;}</pre> |

| Method | Status | URL | Response |
| --- | --- | --- | --- |
| POST | 200 | /auth/registration | <br/><pre lang='json'>{&#13; "message": "ok", &#13;}</pre>|
| POST | 401 | /auth/registration | <br><pre lang="json">{&#13; "message": "user already exists", &#13;}</pre>