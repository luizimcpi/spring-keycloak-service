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

## Generate Token ( Proxy to keycloak Authentication - Draft...) 
```
curl --request POST \
  --url http://localhost:8080/token \
  --header 'Content-Type: application/json' 
  --data '{
	"username": "testuser-1",
	"password": "testuser1"
}'
```

## Generate Token (using keycloak)
```
curl --request POST \
  --url http://localhost:8180/realms/my-test-realm/protocol/openid-connect/token \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data client_id=my-webapp-client \
  --data grant_type=password \
  --data username=testuser-1 \
  --data client_secret=my-webapp-client \
  --data password=testuser1
```

## Hello Endpoint
``` curl
curl --request GET \
  --url http://localhost:8080/hello \
  --header 'Authorization: Bearer {generated_token_here}'
```