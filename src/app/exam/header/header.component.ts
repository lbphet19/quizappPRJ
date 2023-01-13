import { User } from './../../Model/user';
import { Observable } from 'rxjs';
import { AuthServiceService } from './../../Services/auth-service.service';
import { TokenStorageService } from './../../Services/token-storage.service';
import { Component, OnInit, DoCheck } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, DoCheck{

  constructor(private authService:AuthServiceService,
    private TokenStorageService:TokenStorageService,
    private router:Router, private modalService: NgbModal) { }

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
  search(event:any){
    this.router.navigate(['quiz','search'],{queryParams:{search:event.target.value}})
  }
  navigateLogin(){
    this.router.navigate(['auth','login'])
  }

  open(content:any){
    this.modalService.open(content)
  }
  closeModal(){
    this.modalService.dismissAll()
  }
}
