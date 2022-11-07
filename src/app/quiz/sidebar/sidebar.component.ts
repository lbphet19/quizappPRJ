import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }
  create(){
    this.router.navigate(['quiz','create'])
  }
  createExam(){
    this.router.navigate(['exam','create'])
  }
  categoryIndex(){
    this.router.navigate(['quiz','category-list'])
  }
  navigateHome(){
    this.router.navigate(['quiz','all'])
  }
}
