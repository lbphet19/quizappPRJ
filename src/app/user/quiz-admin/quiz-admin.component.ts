import { QuestionService } from './../../Services/question.service';
import { AnswerService } from './../../Services/answer.service';
import { FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';
import { Question } from '../../Model/question';
import { Observable, of, forkJoin, merge } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { mergeMap, toArray, map } from 'rxjs/operators';
import {Component,OnInit} from '@angular/core'



@Component({
  selector: 'app-quiz-admin',
  templateUrl: './quiz-admin.component.html',
  styleUrls: ['./quiz-admin.component.css']
})
export class QuizAdminComponent implements OnInit {


  // questionTest:Question[]
  quizId!:number
  questions!:Observable<Question[]>
  quizForm!:FormGroup;
  questionUpdateForm!:FormGroup
  constructor(private questionService:QuestionService,
    private route:ActivatedRoute,
    private fb:FormBuilder,
    private answerService:AnswerService) { }

  ngOnInit(): void {
    this.quizId = this.route.snapshot.params['id']
    this.getQuestionFromQuizId(this.quizId)
    this.createQuestionUpdateForm()
    this.createQuizForm()
    // this.initQuizForm()
  }
  getQuestionFromQuizId(quizId:number){
    this.questions = this.questionService.getByQuizId(quizId)
  }
  createQuizForm(){
    this.quizForm = this.fb.group({
      questionForm : new FormArray([])
    })
  }
  createQuestionUpdateForm(){
    this.questionUpdateForm = this.fb.group({
          questionId:new FormControl(),
          questionContent:new FormControl(),
          questionType:new FormControl(),
          answers:new FormArray([])
    })
  }
  getAnswersFromQuestionUpdateForm():FormArray{
    return this.questionUpdateForm.get('answers') as FormArray
  }
  questionForm():FormArray{
    return this.quizForm.get('questionForm') as FormArray
  }
  newQuestion(questionId?:number,questionContent?:string,questionType?:string):FormGroup{
    const question = new FormGroup({
      questionId: new FormControl(questionId?questionId:''),
      questionContent: new FormControl(questionContent?questionContent:''),
      questionType:new FormControl(questionType?questionType:''),
      answers:new FormArray([])
    })
    return question
  }
  newAnswer(questionId?:number,answerId?:number,answerContent?:string,answerIsCorrect?:boolean):FormGroup{
    const answer = new FormGroup({
      questionId:new FormControl(questionId?questionId:''),
      answerId:new FormControl(answerId?answerId:''),
      answerContent:new FormControl(answerContent?answerContent:''),
      answerIsCorrect:new FormControl(answerIsCorrect?answerIsCorrect:false)
    })
    return answer
  }
  // generateQuestion(questionId:number, questionContent:string,questionType:string){
  //   const question = this.newQuestion(questionId,questionContent,questionType)
  //   this.questionForm().push(question)
  // }
  // generateanswer(questionIndex:number,questionId:number,answerId:number,
  //   answerContent:string,answerIsCorrect:boolean){
  //   const answer = this.newAnswer(questionId,answerId,answerContent,answerIsCorrect)
  //   this.getAnswerAtIndex(questionIndex).push(answer)
  // }
  addQuestion(){
    const question = this.newQuestion();
    this.questionForm().push(question)
  }
  addAnswer(questionIndex?:number){
    const answer = this.newAnswer()
    if(questionIndex)
      this.getAnswerAtIndex(questionIndex).push(answer)
    else
      this.getAnswersFromQuestionUpdateForm().push(answer)
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
  test(){
    console.log( this.answerService.convertAnswerFormToAnswerRequest(
      1,
      this.getAnswerAtIndex(0).value
    ))
  }



}
