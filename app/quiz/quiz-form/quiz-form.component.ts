import { FormBuilder, FormArray, FormGroup, FormControl } from '@angular/forms';
import { Question } from './../../Model/question';
import { QuestionService } from './../../Services/question.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Route } from '@angular/compiler/src/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbCarousel } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-quiz-form',
  templateUrl: './quiz-form.component.html',
  styleUrls: ['./quiz-form.component.css']
})
export class QuizFormComponent implements OnInit {
  @ViewChild('myCarousel') myCarousel! : NgbCarousel
  answerForm!: FormArray
  questions!:Question[]
  id!:number
  constructor(private route:ActivatedRoute,
    private QuestionService:QuestionService,
    private fb:FormBuilder,
    private router:Router
    ) { }

  ngOnInit(): void {

    this.id = this.route.snapshot.params['id']
    this.QuestionService.getByQuizId(this.id).subscribe(data =>
      {this.questions = data
       this.initForm(data)
      })


  }
  initForm(questions:Question[]){

    this.answerForm = new FormArray([])
    for(let i of questions){
      this.answerForm.push(new FormGroup(
        {
          questionId: new FormControl(i.questionId),
          answerIds:new FormControl()
        }
      ))
    }
    // this.answerForm.at(0).patchValue({answerIds:[1,2]})

  }
  updateAnswer(answer:any){
    this.answerForm.at(answer.index).patchValue({answerIds:answer.answers})
    console.log(this.answerForm.value)
  }
  submit(){
    this.QuestionService.postAnswersTest(this.id,this.answerForm.value).subscribe(
      value => {
        const ids:number[] = []
        window.sessionStorage.setItem('result',JSON.stringify(value))
        for(let i = 0; i < this.answerForm.length;i++){
            ids.push(...this.answerForm.at(i).get('answerIds')!.value)
        }
        window.sessionStorage.setItem('selected',JSON.stringify(ids))
        this.router.navigate(['quiz','score',{quiz:this.id}])
      }
    )
  }
}
