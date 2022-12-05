import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { QuizCategory } from 'src/app/Model/quizCategory';
import { QuizService } from 'src/app/Services/quiz.service';
declare var sliderr:any;

@Component({
  selector: 'app-quiz-index',
  templateUrl: './quiz-index.component.html',
  styleUrls: ['./quiz-index.component.css']
})
export class QuizIndexComponent implements OnInit {
    // quizCatList!:Observable<QuizCategory[]>
    quizCatList!:any[]
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

    createQuiz(){
      // nav trang tao quiz
      this.router.navigate(['quiz','create'])

    }
    update(id:number){
      this.router.navigate(['user','quiz',id])
    }
  

}
