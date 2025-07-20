import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {Home} from './home/home'
import {AboutUs} from './about-us/about-us'

const routes: Routes = [
  {path: '', component: Home},
  {path:'about-us',component:AboutUs}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
