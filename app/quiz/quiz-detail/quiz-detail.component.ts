import { Quiz } from './../../Model/quiz';
import { Observable } from 'rxjs';

import { QuizService } from './../../Services/quiz.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Route } from '@angular/compiler/src/core';

@Component({
  selector: 'app-quiz-detail',
  templateUrl: './quiz-detail.component.html',
  styleUrls: ['./quiz-detail.component.css']
})
export class QuizDetailComponent implements OnInit {
  quiz!:Quiz
  constructor(private route:ActivatedRoute,
    private QuizService:QuizService,
    private router:Router) {
    const id = this.route.snapshot.params['id'];
    this.QuizService.getById(id).subscribe(data => this.quiz = data)
   }

  ngOnInit(): void {
  }
  attempt(id:number){
    this.router.navigate(['quiz','attempt',id])
  }
}
