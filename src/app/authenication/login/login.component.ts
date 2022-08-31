import { User } from './../../Model/user';
import { TokenStorageService } from './../../Services/token-storage.service';
import { AuthServiceService } from './../../Services/auth-service.service';
import { Component, OnInit } from '@angular/core';
import { SocialAuthService, GoogleLoginProvider, SocialUser } from 'angularx-social-login';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  u:any
  user!: SocialUser;
  loggedIn:boolean = false
  constructor(private socialAuthService: SocialAuthService,
    private authService:AuthServiceService,
    private tokenService:TokenStorageService,
    private route:Router) { }

  ngOnInit(): void {
    this.socialAuthService.authState.subscribe(user => {this.user = user})
  }
  async signInWithGoogle(){
    let socialUser =  await this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID);
    console.log(socialUser)
    this.authService.signinWithGoogle(socialUser.idToken).subscribe(data =>{
      console.log(data)
      this.tokenService.saveToken(data.accessToken)
      this.tokenService.saveUser(
        new User(data.id,data.username,data.name,data.roles,data.email)
      )
    })
    this.route.navigate(['quiz/all'])
  }
  signOut():void{
    this.socialAuthService.signOut()
  }
}
