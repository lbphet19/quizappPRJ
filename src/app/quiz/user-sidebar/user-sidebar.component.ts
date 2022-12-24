import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-sidebar',
  templateUrl: './user-sidebar.component.html',
  styleUrls: ['./user-sidebar.component.css']
})
export class UserSidebarComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }
  create(){
    this.router.navigate(['quiz','create'])
  }
  createExam(){
    this.router.navigate(['exam','create'])
  }
  index(){
    this.router.navigate(['quiz','all'])
  }
  historyIndex(){
    this.router.navigate(['quiz','history'])
  }
  navigateHome(){
    this.router.navigate(['quiz','all'])
  }
}
