import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Home } from './home/home';
import { AuthGuard } from './service/keycloak.guard';
import { AboutUs } from './about-us/about-us';
import { MyProfile } from './my-profile/my-profile';
import { Accomodation } from './accomodation/accomodation';
import { Logs } from './logs/logs';
import { MyAccomodation } from './my-accomodation/my-accomodation';

const routes: Routes = [
  {path: '', component: Home},
  {path: 'aboutus', component: AboutUs},
  {path: 'myProfile', component: MyProfile},
  {path: 'accomodation', component: Accomodation},
  {path: 'logs', component: Logs},
  {path: 'my-accomodation',component: MyAccomodation}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
