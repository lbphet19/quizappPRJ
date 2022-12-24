import { ActivatedRoute, Router } from '@angular/router';
import { Question } from './../../Model/question';
import { Observable } from 'rxjs';
import { QuestionService } from './../../Services/question.service';
import { QuizService } from './../../Services/quiz.service';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ExamServiceService } from 'src/app/Services/exam-service.service';
import { windowWhen } from 'rxjs/operators';

@Component({
  selector: 'app-exam-add-question',
  templateUrl: './exam-add-question.component.html',
  styleUrls: ['./exam-add-question.component.css']
})
export class ExamAddQuestionComponent implements OnInit {
  examId!:any
  quizId!:any
  quizzes:any[]=[]
  questionTest!:any
  category!: any
  selectedQuestions:any[]=[]
  //add q to exam
  questions:any[]=[]
  questionId:any[]=[]
  constructor(private location:Location,private quizService:QuizService,
    private questionService:QuestionService, private examService:ExamServiceService,
    private router:Router,private route: ActivatedRoute) { }

  ngOnInit(): void {
    const state:any = this.location.getState()
    this.examId = this.route.snapshot.params['id']
    this.getExamQuestions(this.examId)
    this.route.queryParams.subscribe(params => this.category = params.category)
  }
  getExamQuestions(examId:any){
    this.examService.getQuestionIds(examId).subscribe(data =>{
      this.questionId = data
      this.generateQuestions(examId)
    })
  }
  contains(id:any){
    return this.questionId.includes(id)
  }
  generateQuestions(examId:any){
    this.examService.getQuizWithQuestions(this.examId).subscribe(data => {
      this.quizzes = data
      this.generateSelectedQuestions()
    })
  }
  generateSelectedQuestions(){
    this.quizzes.forEach(q => this.questions.push(...q.questions))
    const temp = this.questionId
    this.selectedQuestions = temp.map(q => this.questions.find(ques => ques.questionId === q))
  }
  back(){
    this.location.back()
  }
  onChange(questionId:any,event:any){
  
    if(!event.target.checked){
      for(let i = 0; i < this.questionId.length;i++){
        if(this.questionId[i] === questionId){
          this.questionId.splice(i,1)
          this.selectedQuestions.splice(i,1)
        }
      }
    }
    else{
      this.questionId.push(questionId)
      this.selectedQuestions.push(this.questions.find(q => q.questionId === questionId))
    }

  }
  // update: lay tat ca id -> check tat ca
  moveAnswerUp(index:any){
    let temp = this.selectedQuestions[index]
    console.log(temp)
    this.questionId.splice(index,1)
    this.questionId.splice(index-1,0,temp.questionId)
    this.selectedQuestions.splice(index,1)
    this.selectedQuestions.splice(index-1,0,temp)
    }
  moveAnswerDown(index:any){
      let temp = this.selectedQuestions[index]
      this.questionId.splice(index,1)
      this.questionId.splice(index+1,0,temp.questionId)
      this.selectedQuestions.splice(index,1)
      this.selectedQuestions.splice(index+1,0,temp)
      }
  

  submit(e:any){
    e.preventDefault()
    this.examService.addQuestion({
      examId:this.examId,
      questionId:this.questionId
    }).subscribe(data => {
      alert('success')
      this.router.navigate(['quiz','category',this.category,'viewExam'])
    })

  }
  
}
