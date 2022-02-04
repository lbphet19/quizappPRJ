import { Observable } from 'rxjs';
import { QuizCategory } from './../../Model/quizCategory';
import { QuizService } from './../../Services/quiz.service';

import { Answer } from './../../Model/answer';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

declare var sliderr:any;
@Component({
  selector: 'app-quiz-list',
  templateUrl: './quiz-list.component.html',
  styleUrls: ['./quiz-list.component.css']
})
export class QuizListComponent implements OnInit {
  // quizCatList!:Observable<QuizCategory[]>
  quizCatList!:QuizCategory[]
  constructor(private QuizService:QuizService,
    private router:Router) { }

  ngOnInit(): void {
     this.QuizService.getQuizAtHomePage().subscribe(data =>
      this.quizCatList = data)
  }
  navigate(id:string){
    this.router.navigate(['quiz','detail',id])
  }

  alert(abc:string){
    alert(abc)
  }
  slider(direction:any,index:any){
    sliderr.slide(direction,index)
  }
}
