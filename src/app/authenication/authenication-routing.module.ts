import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthenicationComponent } from './authenication.component';

const routes: Routes = [
  { path: '', component: AuthenicationComponent },
  {path: 'login',component:LoginComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthenicationRoutingModule { }
