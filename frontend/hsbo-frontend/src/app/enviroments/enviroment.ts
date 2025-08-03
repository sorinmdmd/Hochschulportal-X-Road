export const environment = {
    production: true,
    keycloakConfig: {
      url: 'http://localhost:8080',
      realm: 'EDUHSBO',
      clientId: 'angularclient',
      redirectUriLogin: 'http://localhost:4200/',
      redirectUriLogout: 'http://localhost:4200/',
    },
    backendConfig: {
      baseUrl: 'http://localhost:8085/api'
    },
  };
  