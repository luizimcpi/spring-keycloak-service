package io.github.luizimcpi.springkeycloakservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AuthenticationController {

    private final RestTemplate restTemplate;
    private final String keyCloakTokenUrl;
    private final String clientId;
    private final String clientSecret;

    public AuthenticationController(RestTemplateBuilder builder,
                                    @Value("${keycloak.token.endpoint}") String keyCloakTokenUrl,
                                    @Value("${keycloak.frontend-app.client-id}") String clientId,
                                    @Value("${keycloak.frontend-app.client-secret}") String clientSecret) {
        this.restTemplate = builder.build();
        this.keyCloakTokenUrl = keyCloakTokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @PostMapping("/token")
    public ResponseEntity<KcToken> token(@RequestBody UserCredentials userCredentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "password");
        map.add("username", userCredentials.username());
        map.add("password", userCredentials.password());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        return restTemplate.exchange(keyCloakTokenUrl, HttpMethod.POST, entity, KcToken.class);
    }

    record KcToken(@JsonProperty("access_token") String accessToken,
                   @JsonProperty("expires_in") Integer expiresIn,
                   @JsonProperty("refresh_expires_in") Integer refreshExpiresIn,
                   @JsonProperty("refresh_token") String refreshToken,
                   @JsonProperty("token_type") String tokenType,
                   @JsonProperty("not-before-policy") Integer notBeforePolicy,
                   @JsonProperty("session_state") String sessionState,
                   String scope) {}

    record UserCredentials(String username, String password){}
}
