import { User } from './../../Model/user';
import { TokenStorageService } from './../../Services/token-storage.service';
import { AuthServiceService } from './../../Services/auth-service.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

declare var google:any
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  u:any
  loggedIn:boolean = false
  constructor(
    private authService:AuthServiceService,
    private tokenService:TokenStorageService,
    private route:Router) { }

  ngOnInit(): void {
  }

  ngAfterViewInit():void{
    google.accounts.id.initialize({
      client_id: "487928071533-rht5j9fqeqontj8g5cq72rk6vvmvukq0.apps.googleusercontent.com",
      callback: (response: any) => this.handleGoogleSignIn(response)
    });
    google.accounts.id.renderButton(
      document.getElementById("buttonDiv"),
      { size: "large", type: "icon", shape: "pill" }  // customization attributes
    );
  }

  handleGoogleSignIn(response: any) {
    this.authService.signinWithGoogle(response.credential).subscribe(data =>{
      this.tokenService.saveToken(data.accessToken)
      this.tokenService.saveUser(
        new User(data.id,data.username,data.name,data.roles,data.email)
      )
      window.location.reload()
    })
   /*  // This next is for decoding the idToken to an object if you want to see the details.
    let base64Url = response.credential.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    console.log(JSON.parse(jsonPayload)); */
  }

  signOut():void{
    // this.socialAuthService.signOut()
  }
}
