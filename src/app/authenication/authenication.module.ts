import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthenicationRoutingModule } from './authenication-routing.module';
import { AuthenicationComponent } from './authenication.component';
import { LoginComponent } from './login/login.component';
import { SocialLoginModule, GoogleLoginProvider, SocialAuthServiceConfig } from 'angularx-social-login';


@NgModule({
  declarations: [AuthenicationComponent, LoginComponent],
  imports: [
    CommonModule,
    AuthenicationRoutingModule,
    SocialLoginModule
  ],
  providers:[{
    provide:'SocialAuthServiceConfig',
    useValue: {
      autoLogin: false,
      providers: [
        {
          id: GoogleLoginProvider.PROVIDER_ID,
          provider: new GoogleLoginProvider(
            '468393702933-f7jsd3trnsgt4nkdqu4ir6dpihgdp8sh.apps.googleusercontent.com'
          )
        }
    ]
    } as SocialAuthServiceConfig,
  }
  ]
})
export class AuthenicationModule { }
