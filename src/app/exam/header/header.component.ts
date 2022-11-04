import { TokenStorageService } from './../../Services/token-storage.service';
import { AuthServiceService } from './../../Services/auth-service.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/Model/user';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService:AuthServiceService,
    private TokenStorageService:TokenStorageService,
    private router:Router) { }

  isLoggedIn!:boolean
  user!:User
  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn()
    if(this.isLoggedIn === true){
      this.user = this.TokenStorageService.getUser()
    }
  }
  ngDoCheck(): void {
    this.isLoggedIn = this.authService.isLoggedIn()
    if(this.isLoggedIn === true){
      this.user = this.TokenStorageService.getUser()
    }
  }
  logout(){
    this.TokenStorageService.signOut()
    window.location.reload()
  }
  navigateLogin(){
    this.router.navigate(['auth','login'])
  }

}
