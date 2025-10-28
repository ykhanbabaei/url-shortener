# URL Shortener Application

Url Shortener application with spring boot.
## Tech Stack
-  Spring boot
-  H2 in memory DB
-  Caffeine caching


## API:

GET   /api/{code}
Response: {url}

POST    /api/register
Request body: {"url":"your url"}
Response:
{url code}

GET     /{code}
Response: code: 302 redirect to the url




