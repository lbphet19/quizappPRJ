import { Observable } from 'rxjs';
import { QuizCategory } from './../../Model/quizCategory';
import { QuizService } from './../../Services/quiz.service';

import { Answer } from './../../Model/answer';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ExamServiceService } from 'src/app/Services/exam-service.service';
import { QuizCategoryService } from 'src/app/Services/quiz-category.service';

declare var sliderr:any;
@Component({
  selector: 'app-quiz-list',
  templateUrl: './quiz-list.component.html',
  styleUrls: ['./quiz-list.component.css']
})
export class QuizListComponent implements OnInit {
  // quizCatList!:Observable<QuizCategory[]>
  // quizCatList!:QuizCategory[]
  catList!: any[]
  constructor(private QuizService:QuizService,private catService:QuizCategoryService,
    private router:Router, private examService: ExamServiceService) { }

  ngOnInit(): void {
     this.catService.getRootCategory().subscribe(data =>
      this.catList = data)
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
