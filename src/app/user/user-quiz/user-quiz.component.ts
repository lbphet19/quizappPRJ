import { Answer } from './../../Model/answer';
import { mergeMap, toArray } from 'rxjs/operators';
import { AnswerService } from './../../Services/answer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from './../../Services/question.service';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { Observable, of, forkJoin } from 'rxjs';
import { Question } from './../../Model/question';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-quiz',
  templateUrl: './user-quiz.component.html',
  styleUrls: ['./user-quiz.component.css']
})
export class UserQuizComponent implements OnInit {

  isSubmitting = false
  isSubmittingUpdate = false

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
    private modalService:NgbModal,
    private location: Location) { }

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
          questionContent:new FormControl('',Validators.required),
          questionType:new FormControl(),
          answers:new FormArray([])
    })
  }
  getAnswersFromQuestionUpdateForm():FormArray{
    return this.questionUpdateForm.get('answers') as FormArray
  }
  questionFormArray():FormArray{
    return this.quizForm.get('questionForm') as FormArray
  }
  questionFormControl(){
    return (this.quizForm.get('questionForm') as FormArray).controls
  }
  newQuestion(questionId?:number,questionContent?:string,questionType?:string):FormGroup{
    const question = new FormGroup({
      questionId: new FormControl(questionId?questionId:''),
      questionContent: new FormControl(questionContent?questionContent:'', Validators.required),
      questionType:new FormControl('single-choice'),
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

  addQuestion(){
    const question = this.newQuestion();
    this.questionFormArray().push(question)
  }
  addAnswer(questionIndex?:number){
    const answer = this.newAnswer()
    if(questionIndex !== undefined)
      this.getAnswerAtIndex(questionIndex).push(answer)
    else
      this.getAnswersFromQuestionUpdateForm().push(answer)
  }
  getQuestionAtIndex(questionIndex:number):FormGroup{
    return this.questionFormArray().at(questionIndex) as FormGroup
  }
  getAnswerAtIndex(questionIndex:any){
    return this.getQuestionAtIndex(questionIndex).get('answers') as FormArray
  }
  removeQuestion(index:number){
    this.questionFormArray().removeAt(index)
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
  deleteQuestion(questionId:number){
    //get question theo index cua listQuestion lay tu http, neu delete thi sau khi delete http cap nhat lai listQuestion
    
    console.log(questionId)
  }
  deleteQuestionAdd(questionIndex:number){
    this.questionFormArray().removeAt(questionIndex)
  }
  deleteAnswerAdd(answerIndex:number,questionIndex:number){
    let answers = this.getAnswerAtIndex(questionIndex)
    answers.removeAt(answerIndex)
  }
  removeAnswer(index:number){
    /* if(index <= this.updateAnswerIndex)
      {
        this.deleteAnswerIds.push(this.getAnswersFromQuestionUpdateForm().at(index).get('answerId')!.value)
        this.updateAnswerIndex--;
      }
    this.getAnswersFromQuestionUpdateForm().removeAt(index) */
    if(this.getAnswersFromQuestionUpdateForm()!.at(index)!.get('answerId')!.value !== null){
      this.deleteAnswerIds.push(this.getAnswersFromQuestionUpdateForm().at(index).get('answerId')!.value)
    }
    this.getAnswersFromQuestionUpdateForm().removeAt(index) 
  }

  moveAnswerUp(aIndex:any,qIndex?:any){
    if(qIndex !== undefined){
    let answers = this.getAnswerAtIndex(qIndex)
    let selectAnswer = answers.at(aIndex)
    answers.removeAt(aIndex)
    answers.insert(aIndex-1,selectAnswer)
    }
    else{
    let answers = this.getAnswersFromQuestionUpdateForm()
    let selectAnswer = answers.at(aIndex)
    answers.removeAt(aIndex)
    answers.insert(aIndex-1,selectAnswer)
    }
  }
  moveAnswerDown(aIndex:any,qIndex?:any){
    if(qIndex !== undefined){
      let answers = this.getAnswerAtIndex(qIndex)
      let selectAnswer = answers.at(aIndex)
      answers.removeAt(aIndex)
      answers.insert(aIndex+1,selectAnswer)
      }
      else{
      let answers = this.getAnswersFromQuestionUpdateForm()
      let selectAnswer = answers.at(aIndex)
      answers.removeAt(aIndex)
      answers.insert(aIndex+1,selectAnswer)
      }
  }

  viewForm(){
      this.isSubmitting = true
      if(!this.questionFormArray().invalid){
      let questionRequest = this.questionService.convertQuestionFormToQuestionRequest(
        this.quizId,
        this.questionFormArray().value
      )

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
                    // this.router.navigate(['quiz','category-list'])
                    this.location.back()
      })
    }
      // this.isSubmitting = false
  }
  setAnswerStatus(answerIndex:number,questionIndex?:number){
    console.log(questionIndex)
    if(questionIndex !== undefined){
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
  resetAnswerStatus(questionIndex?:number){
    if(questionIndex !== undefined){
      let answers = this.getAnswerAtIndex(questionIndex)
      for(let i = 0; i < answers.length; i++){
          answers.at(i).get('answerIsCorrect')!.setValue(false)
      }
     }
    
    else{
      // chi co answer Index nghia la dang update
        let answers = this.getAnswersFromQuestionUpdateForm()
        for(let i = 0; i < answers.length; i++){
            answers.at(i).get('answerIsCorrect')!.setValue(false)
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
  /* saveUpdateQuestion(){
    const answersFromQuestionUpdateForm = this.getAnswersFromQuestionUpdateForm().value
    console.log(answersFromQuestionUpdateForm)
    let deleted = this.deleteAnswerIds
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
    ).subscribe(res => {
        window.location.reload()
    },
    err => {
      window.location.reload()
    }
    )
    this.deleteAnswerIds = []
    this.updateAnswerIndex = -1
    this.modalService.dismissAll('Saved')
    //delete + update ans+ insert  + update quest
  } */
  saveUpdateQuestion(){
    this.isSubmittingUpdate = true
    if(!this.questionUpdateForm.invalid){
    const answersFromQuestionUpdateForm = this.getAnswersFromQuestionUpdateForm().value
    let deleted = this.deleteAnswerIds
    let updateAnswers:any[] = []
    let newAnswers:any[] = []
    for(let i = 0; i < answersFromQuestionUpdateForm.length;i++){
      if(answersFromQuestionUpdateForm[i].answerId === null){
        newAnswers.push({
          ...answersFromQuestionUpdateForm[i],
          position:i
        })
      }
      else{
      // updateAnswers.push(answersFromQuestionUpdateForm[i])
      updateAnswers.push({
        ...answersFromQuestionUpdateForm[i],
        position:i
      })
      }
      // newAnswers
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
    ).subscribe(res => {
        window.location.reload()
    },
    err => {
      window.location.reload()
    }
    )
    this.deleteAnswerIds = []
    this.updateAnswerIndex = -1
    this.modalService.dismissAll('Saved')
    //delete + update ans+ insert  + update quest
  }
}
}
