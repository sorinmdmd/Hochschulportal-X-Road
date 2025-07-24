import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { AuthService } from '../service/auth.service';
import {jwtDecode} from 'jwt-decode';


@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header implements OnInit{
  token: string | undefined = undefined;
  name: string | undefined = undefined;
  studentId : string | undefined = undefined;

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
    this.name = undefined;
    this.studentId = undefined;
  }

  async loadToken() {
    try {
      this.token = await this.authService.getToken();
      console.log('Token loaded:', this.token); // Debugging

      if (this.token) {
        this.decodeToken(this.token);
      }
    } catch (error) {
      console.error('Error loading token:', error);
    }
  }

  isLoggedIn(): boolean {
    return this.keycloakService.isLoggedIn();
  }

  decodeToken(token: string) {
    try {
      const decoded: any = jwtDecode(token);
      console.log('Decoded JWT:', decoded); // Debugging

      this.name = decoded.given_name || decoded.preferred_username || decoded['upn'] || undefined;
      this.studentId = decoded.studentId || decoded.preferred_username || decoded['upn'] || undefined;
      console.log('Extracted email:', this.name);
    } catch (error) {
      console.error('Error decoding token:', error);
      this.name = undefined;
    }
  }
}
