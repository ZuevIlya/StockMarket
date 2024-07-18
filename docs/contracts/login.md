### Вход
| Method | URL | Request |
| --- | --- | --- |
| POST | /auth/login | <br/><pre lang="json">{&#13; "username": "some name", &#13; "password": "some hash pass", &#13;}</pre> |

| Method | Status | URL | Response |
| --- | --- | --- | --- |
| POST | 200 | /auth/login | <br/><pre lang='json'>{&#13; "message": "ok", &#13;}</pre>|
| POST | 401 | /auth/login | <br><pre lang="json">{&#13; "message": "user does not exist", &#13;}</pre>