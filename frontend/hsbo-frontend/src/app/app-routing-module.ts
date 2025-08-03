import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Home } from './home/home';
import { AuthGuard } from './service/keycloak.guard';
import { AboutUs } from './about-us/about-us';
import { MyProfile } from './my-profile/my-profile';
import { Accomodation } from './accomodation/accomodation';
import { Logs } from './logs/logs';
import { MyAccomodation } from './my-accomodation/my-accomodation';
import { LanguageCourses } from './language-courses/language-courses';
import { MyLanguageCourse } from './my-language-course/my-language-course';

const routes: Routes = [
  {path: '', component: Home},
  {path: 'myProfile', component: MyProfile},
  {path: 'accomodation', component: Accomodation},
  {path: 'logs', component: Logs},
  {path: 'my-accomodation',component: MyAccomodation},
  {path: 'language-courses', component: LanguageCourses},
  {path: 'my-language-course', component: MyLanguageCourse}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
