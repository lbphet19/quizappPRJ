import { Router } from '@angular/router';
import { Question } from './../../Model/question';
import { Observable } from 'rxjs';
import { QuestionService } from './../../Services/question.service';
import { QuizService } from './../../Services/quiz.service';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ExamServiceService } from 'src/app/Services/exam-service.service';

@Component({
  selector: 'app-exam-add-question',
  templateUrl: './exam-add-question.component.html',
  styleUrls: ['./exam-add-question.component.css']
})
export class ExamAddQuestionComponent implements OnInit {
  examId!:any
  quizId!:any
  questions!:Observable<any[]>
  questionTest!:any

  //add q to exam
  questionId:any[]=[]
  constructor(private location:Location,private quizService:QuizService,
    private questionService:QuestionService, private examService:ExamServiceService,
    private router:Router) { }

  ngOnInit(): void {
    const state:any = this.location.getState()
    this.examId = state['examId'];
    this.quizId = state['quizId'];
    this.generateQuestions(this.quizId)
  }
  generateQuestions(quizId:any){
    this.questions = this.questionService.getByQuizId(quizId)
    // this.questionService.getByQuizId(quizId).subscribe(data => this.questionTest = data);

    // console.log(quizId)   asasd
  }
  onChange(questionId:any,event:any){
    if(!event.target.checked){
      for(let i = 0; i < this.questionId.length;i++){
        if(this.questionId[i] === questionId)
          this.questionId.splice(i,1)
      }
    }
    else{
      this.questionId.push(questionId)
    }
    // console.log(questionId)
    console.log(this.questionId)

  }
  submit(e:any){
    e.preventDefault()
    this.examService.addQuestion({
      examId:this.examId,
      questionId:this.questionId
    }).subscribe(data => {
      alert('scuccess')
      this.router.navigate(['quiz/all'])
    })
  }
}
