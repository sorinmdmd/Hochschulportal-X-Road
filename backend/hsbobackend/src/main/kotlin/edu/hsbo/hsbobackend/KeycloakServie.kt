package edu.hsbo.hsbobackend

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class KeycloakService(
    @Value("\${auth.server.url}")
    private val serverUrl: String,

    @Value("\${keycloak.realm}")
    private val keycloakRealm: String,

    @Value("\${keycloak.clientIdForRegistration}")
    private val clientIdForRegistration: String,

    @Value("\${keycloak.clientSecretForRegistrationId}")
    private val clientSecretForRegistrationId: String,

    @Value("\${keycloak.redirectAfterRegistration}")
    private val redirectAfterRegistration: String,
)