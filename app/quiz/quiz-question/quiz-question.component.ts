import { Answer } from './../../Model/answer';
import { FormControl, FormGroup } from '@angular/forms';
import { Question } from './../../Model/question';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
// import * as EventEmitter from 'events';

@Component({
  selector: 'app-quiz-question',
  templateUrl: './quiz-question.component.html',
  styleUrls: ['./quiz-question.component.css']
})
export class QuizQuestionComponent implements OnInit {

  @Input() question!:Question
  @Input() index!:number
  @Output() out: EventEmitter<any> = new EventEmitter()
  answer!:FormGroup
  answersObject:any
  constructor() { }

  ngOnInit(): void {
    this.answersObject = {
      index:this.index,
      answers:[]
    }
    // console.log(this.index)
    this.answer = new FormGroup({
      answer:new FormControl('')
    })

  }
  onChange(ans:number,event:any){
    if(this.question.questionType === 'multiple-choice'){
    if(event.target.checked){
      this.answersObject.answers.push(ans)
    }else{
      for(let i = 0; i < this.question.answers.length; i++){
        if(this.question.answers[i].answerId === ans){
          this.answersObject.answers.splice(i,1)
          break;
        }
      }
    }
  }
  else if (this.question.questionType === 'single-choice'){
    this.answersObject.answers.pop()
    this.answersObject.answers.push(ans)
  }
    this.out.emit(this.answersObject)
  }
}
