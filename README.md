# spring-keycloak-service

## How to run
```
cd keycloak
change local path of postgres volume in docker-compose.yml
docker-compose up --build

http://localhost:8180/
admin
password

Run SpringKeycloakServiceApplication.java class
```

## Generate Token (Authentication)
```
curl --request POST \
  --url http://localhost:8080/token \
  --header 'Content-Type: application/json' 
  --data '{
	"username": "testuser-1",
	"password": "testuser1"
}'
```

## Hello Endpoint
``` curl
curl --request GET \
  --url http://localhost:8080/hello \
  --header 'Authorization: Bearer {generated_token_here}'
```