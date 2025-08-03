import { NgModule, provideBrowserGlobalErrorListeners,APP_INITIALIZER } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { environment } from '../app/enviroments/enviroment';
import { HttpClientModule,HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Home } from './home/home';
import { AboutUs } from './about-us/about-us';
import { Header } from './header/header';
import { AuthInterceptor } from './service/authInterceptor';
import { MyProfile } from './my-profile/my-profile';
import { Accomodation } from './accomodation/accomodation';
import { Logs } from './logs/logs';
import { MyAccomodation } from './my-accomodation/my-accomodation';
import { LanguageCourses } from './language-courses/language-courses';
import { MyLanguageCourse } from './my-language-course/my-language-course';

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: environment.keycloakConfig.url,
        realm: environment.keycloakConfig.realm,
        clientId: environment.keycloakConfig.clientId,
      },
      initOptions: {
        onLoad: 'check-sso',
        redirectUri: environment.keycloakConfig.redirectUriLogin,
      },
      enableBearerInterceptor: true,
      bearerPrefix: 'Bearer',
      bearerExcludedUrls: [],
    });
}


@NgModule({
  declarations: [
    App,
    Home,
    AboutUs,
    Header,
    MyProfile,
    Accomodation,
    Logs,
    MyAccomodation,
    LanguageCourses,
    MyLanguageCourse
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    KeycloakAngularModule,
    HttpClientModule,
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
  ],
  bootstrap: [App]
})
export class AppModule { }
