import { Answer } from './../../Model/answer';
import { mergeMap, toArray } from 'rxjs/operators';
import { AnswerService } from './../../Services/answer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from './../../Services/question.service';
import { FormGroup, FormBuilder, FormControl, FormArray } from '@angular/forms';
import { Observable, of, forkJoin } from 'rxjs';
import { Question } from './../../Model/question';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-user-quiz',
  templateUrl: './user-quiz.component.html',
  styleUrls: ['./user-quiz.component.css']
})
export class UserQuizComponent implements OnInit {

  // questionTest:Question[]
  updateAnswerIndex:number = 0 //last index cua mang cac phan tu can update
  deleteAnswerIds:number[] = []  //cac ID cua answer can xoa
  quizId!:number
  questions!:Question[]
  quizForm!:FormGroup;  //form tao moi question va answer
  questionUpdateForm!:FormGroup  //modal form update question
  constructor(private questionService:QuestionService,
    private route:ActivatedRoute,
    private router:Router,
    private fb:FormBuilder,
    private answerService:AnswerService,
    private modalService:NgbModal) { }

  ngOnInit(): void {
    this.quizId = this.route.snapshot.params['id']
    this.getQuestionFromQuizId(this.quizId)
    this.createQuestionUpdateForm()
    this.createQuizForm()
    this.initQuizForm()
  }
  getQuestionFromQuizId(quizId:number){
    this.questionService.getByQuizId(quizId).subscribe(
      data => this.questions = data
    )
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
      questionId:new FormControl(questionId?questionId:null),
      answerId:new FormControl(answerId?answerId:null),
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
    if(questionIndex !== undefined)
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
  test(){
    console.log( this.answerService.convertAnswerFormToAnswerRequest(
      1,
      this.getAnswerAtIndex(0).value
    ))
  }
  updateQuestion(index:number){
    let question =  this.questions[index]
    // patch value cho update form roi open bootstrap
    this.questionUpdateForm.patchValue({
      questionId:question.questionId,
      questionType:question.questionType,
      questionContent:question.questionContent
    })
    this.getAnswersFromQuestionUpdateForm().clear()
    for(let answer of question.answers){
      this.getAnswersFromQuestionUpdateForm().push(new FormGroup({
        answerId:new FormControl(answer.answerId),
        answerContent:new FormControl(answer.answerContent),
        answerIsCorrect:new FormControl(answer.answerIsCorrect)
      }))
    }
    this.updateAnswerIndex = question.answers.length-1
  }
  deleteQuestion(questionIndex:number){
    //get question theo index cua listQuestion lay tu http, neu delete thi sau khi delete http cap nhat lai listQuestion
    const question = this.questions[questionIndex]
    console.log(question)
  }
  deleteQuestionAdd(questionIndex:number){
    this.questionForm().removeAt(questionIndex)
  }
  deleteAnswerAdd(answserIndex:number,questionIndex:number){
    let answers = this.getAnswerAtIndex(questionIndex)
    answers.removeAt(answserIndex)
  }
  removeAnswer(index:number){
    if(index <= this.updateAnswerIndex)
      {
        this.deleteAnswerIds.push(this.getAnswersFromQuestionUpdateForm().at(index).get('answerId')!.value)
        this.updateAnswerIndex--;
      }
    this.getAnswersFromQuestionUpdateForm().removeAt(index)
  }
  viewForm(){
      let questionRequest = this.questionService.convertQuestionFormToQuestionRequest(
        this.quizId,
        this.questionForm().value
      )

      console.log(this.questionForm().value)
      // let questionReq = of(1,2,3)
      of(...questionRequest).pipe(
        mergeMap((question,index) => {
          const quest = this.questionService.save(question)
          // console.log(index + ':' + question.questionContent)
          // console.log('Hello!')
          return forkJoin(quest,of(index))
        }),
        mergeMap(question => {
          let answerRequest = this.answerService.convertAnswerFormToAnswerRequest(
            question[0].questionId,
            this.getAnswerAtIndex(question[1]).value
          )
          let answerResponse = this.answerService.saveMultipleAnswers(answerRequest)
          return answerResponse
        }),
        toArray()
        ).subscribe(res => {alert('thanh cong')
                    this.router.navigate(['quiz','all'])
      })

  }
  setAnswerStatus(answerIndex:number,questionIndex?:number){
    if(questionIndex){
    if(this.getQuestionAtIndex(questionIndex).get('questionType')!.value === 'single-choice'){
      let answers = this.getAnswerAtIndex(questionIndex)
      for(let i = 0; i < answers.length; i++){
        if(i !== answerIndex)
          answers.at(i).get('answerIsCorrect')!.setValue(false)
      }
      answers.at(answerIndex).get('answerIsCorrect')!.setValue(true)
      }
    }
    else{
      // chi co answer Index nghia la dang update
      if(this.questionUpdateForm.get('questionType')!.value === 'single-choice'){
        let answers = this.getAnswersFromQuestionUpdateForm()
        for(let i = 0; i < answers.length; i++){
          if(i !== answerIndex)
            answers.at(i).get('answerIsCorrect')!.setValue(false)
        }
        answers.at(answerIndex).get('answerIsCorrect')!.setValue(true)
      }
    }
  }
  submitForm(){
    return
  }
  initQuizForm(){
  //   this.questionTest.map((question,index) => {
  //     const questionElement = this.newQuestion(question.questionId,question.questionContent,
  //       question.questionType)
  //       this.existingQuestionForm().push(questionElement)
  //       for(let answer of question.answers){
  //         const answerElement = this.newAnswer(question.questionId,answer.answerId,
  //           answer.answerContent,answer.answerIsCorrect)
  //           this.getAnswerAtIndex(index).push(answerElement)
  //       }
  //   })

    // real: get question roi generate form
  }
  // modal update
  openUpdateModal(modalName:any,index:number){
    // update Form
    this.updateQuestion(index)
    this.modalService.open(modalName,{ scrollable : false, size:'lg' })
    .result.then((result) => {}, (reason) => {} )
  }
  closeModal(){
    this.deleteAnswerIds=[]
    this.modalService.dismissAll('Cross click')
    this.updateAnswerIndex = -1
  }
  saveUpdateQuestion(){
    const answersFromQuestionUpdateForm = this.getAnswersFromQuestionUpdateForm().value
    console.log('deletes: ' + this.deleteAnswerIds) //delete IDs
    let deleted = this.deleteAnswerIds
    console.log(deleted)
    let updateAnswers:any[] = []
    let newAnswers:any[] = []
    for(let i = 0; i <= this.updateAnswerIndex;i++){
      // updateAnswers.push(answersFromQuestionUpdateForm[i])
      updateAnswers.push(this.getAnswersFromQuestionUpdateForm().at(i).value)
    }
    for(let i = this.updateAnswerIndex + 1; i < answersFromQuestionUpdateForm.length; i++){
      // newAnswers.push(answersFromQuestionUpdateForm[i])
      newAnswers.push(this.getAnswersFromQuestionUpdateForm().at(i).value)
    }
    // sau khi update+create+delete thi reset du lieu
    updateAnswers = this.answerService.convertAnswerUpdateForm(updateAnswers,this.questionUpdateForm.controls['questionId'].value)
    newAnswers = this.answerService.convertAnswerUpdateForm(newAnswers,this.questionUpdateForm.controls['questionId'].value)
    let questionUpdate = this.questionService.convertQuestionUpdate(this.questionUpdateForm.value)
    forkJoin(
    this.answerService.updateMultipleAnswers(updateAnswers),
      this.answerService.saveMultipleAnswers(newAnswers),
      this.answerService.deleteMultiple(deleted),
      this.questionService.update(questionUpdate)
    ).subscribe(res => console.log('success'),
                err => console.log('Error'))
    this.deleteAnswerIds = []
    this.updateAnswerIndex = -1
    this.modalService.dismissAll('Saved')
    //delete + update ans+ insert  + update quest
  }

}
