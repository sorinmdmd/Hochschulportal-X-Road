import { inject, Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { environment } from '../enviroments/enviroment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  keycloak = inject(KeycloakService);
  private apiUrl = environment.backendConfig.baseUrl;
  constructor(private http: HttpClient) {
    Promise.resolve(this.keycloak.isLoggedIn()).then(async (loggedIn) => {
    });
  }
  public logout(): void {
    this.keycloak.logout(environment.keycloakConfig.redirectUriLogout).then();
  }
  public login(redirectUri?: string): void {
    this.keycloak
      .login({
        redirectUri: redirectUri || environment.keycloakConfig.redirectUriLogin,
      })
      .then();
  }
  public isLoggedIn(): Promise<boolean> {
    return Promise.resolve(this.keycloak.isLoggedIn());
  }
  public getUsername(): string {
    return this.keycloak.getKeycloakInstance()?.profile?.username as string;
  }
  public getId(): string {
    return this.keycloak?.getKeycloakInstance()?.subject as string;
  }
  public getTokenExpirationDate(): number {
    return (
      this.keycloak.getKeycloakInstance().refreshTokenParsed as { exp: number }
    )['exp'] as number;
  }
  public isExpired(): boolean {
    return this.keycloak.getKeycloakInstance().isTokenExpired();
  }
  public getUserGroups(): string[] {
    const tokenParsed = this.keycloak.getKeycloakInstance().tokenParsed as {
      groups?: string[];
    };
    return tokenParsed?.['groups'] || [];
  }
  public getUserRoles(): string[] {
    return this.keycloak.getUserRoles();
  }
  public resetUserAgentSentFlag(): void {
    localStorage.removeItem('isUserAgentSent');
  }

  public async getToken(): Promise<string> {
    try {
      const token = await this.keycloak.getToken();

      console.log('Raw token from Keycloak:', token); // Add this for debugging
      return token || ''; // Return empty string instead of undefined
    } catch (error) {
      console.error('Error getting token:', error);
      return '';
    }
  }
}
