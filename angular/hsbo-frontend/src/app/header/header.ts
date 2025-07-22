import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header implements OnInit{
  token: string | undefined = undefined;

  constructor(private keycloakService: KeycloakService, private authService: AuthService) {}

  async ngOnInit() {
    if (this.isLoggedIn()) {
      await this.loadToken();
    }
  }

  async login() {
    await this.authService.login();
    await this.loadToken()
  }

  async logout() {
    await this.authService.logout();
    this.token = undefined;
  }

  async loadToken() {
    try {
      this.token = await this.authService.getToken();
      console.log('Token loaded:', this.token); // Add this for debugging
    } catch (error) {
      console.error('Error loading token:', error);
    }
  }

  isLoggedIn(): boolean {
    return this.keycloakService.isLoggedIn();
  }
}
