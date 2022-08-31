import { ExamServiceService } from './../../Services/exam-service.service';
import { CorrectAnswerResponseDTO } from './../../Model/CorrectAnswerResponseDTO';
import { Observable } from 'rxjs';
import { Question } from './../../Model/question';
import { AnswerService } from './../../Services/answer.service';
import { FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';
import { QuestionService } from './../../Services/question.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-auqiz-score',
  templateUrl: './auqiz-score.component.html',
  styleUrls: ['./auqiz-score.component.css']
})
export class AuqizScoreComponent implements OnInit {

  constructor(private questionService:QuestionService,
    private route:ActivatedRoute,
    private fb:FormBuilder,
    private answerService:AnswerService,
    private examService:ExamServiceService) { }
  score!:any
  answerResponses!:CorrectAnswerResponseDTO[]
  correctAnswerIds:number[] = []
  selected!:number[]
  ngOnInit(): void {
    let tmp:any = window.sessionStorage.getItem('result')
    this.selected = JSON.parse(window.sessionStorage.getItem('selected')!)
    if(tmp !== null){
      tmp = JSON.parse(tmp)
    this.score = tmp.score
    console.log(this.score)
    this.answerResponses = tmp.answerResponses
    this.initAnswerIds()
    console.log(this.answerResponses)
    // console.log(this.correctAnswerIds)
    }
    this.quizId = this.route.snapshot.params['quiz']
    this.getQuestionFromExamId(this.quizId)
    this.createQuizForm()
  }
  initAnswerIds() {
    for(let i of this.answerResponses){
      this.correctAnswerIds.push(...i.correctAnswerIds)
    }
    console.log(this.correctAnswerIds)
  }
  isAnswerSelected(id:number){
    return this.selected.indexOf(id) > -1
  }
  isAnswerCorrect(id:number){
    return this.correctAnswerIds.indexOf(id) > -1
  }
  // questionTest:Question[]
  quizId!:number
  questions!:Observable<Question[]>
  quizForm!:FormGroup;



  getQuestionFromExamId(quizId:number){
    this.questions = this.examService.getQuestionByExamId(quizId)
  }
  createQuizForm(){
    this.quizForm = this.fb.group({
      questionForm : new FormArray([])
    })
  }
  questionForm():FormArray{
    return this.quizForm.get('questionForm') as FormArray
  }
  getQuestionAtIndex(questionIndex:number):FormGroup{
    return this.questionForm().at(questionIndex) as FormGroup
  }
  getAnswerAtIndex(questionIndex:number){
    return this.getQuestionAtIndex(questionIndex).get('answers') as FormArray
  }
  removeQuestion(index:number){
    this.questionForm().removeAt(index)
  }
  removeAnswer(questionIndex:number,answerIndex:number){
    this.getAnswerAtIndex(questionIndex).removeAt(answerIndex)
  }

}
