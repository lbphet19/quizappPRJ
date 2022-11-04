import { Router } from '@angular/router';
import { QuizService } from './../../Services/quiz.service';
import { Observable } from 'rxjs';
import { FormGroup, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ExamServiceService } from 'src/app/Services/exam-service.service';

@Component({
  selector: 'app-exam-create',
  templateUrl: './exam-create.component.html',
  styleUrls: ['./exam-create.component.css']
})
export class ExamCreateComponent implements OnInit {

  constructor(private quizService:QuizService,
              private examService:ExamServiceService,
              private router:Router
    ) { }

  quizzes!:Observable<any[]>
  examForm!:FormGroup
  ngOnInit(): void {
    this.initForm()
    this.quizzes = this.quizService.get()

  }

  initForm(){
    this.examForm = new FormGroup({
      examName: new FormControl(''),
      descriptions:new FormControl(''),
      examImage:new FormControl('https://cdn.pixabay.com/photo/2021/02/03/05/37/question-mark-5976736_960_720.png'),
      quiz:new FormGroup({
        quizId:new FormControl()
      }
      )
    })
  }
  submit(event:any){
    event.preventDefault()
    // console.log(this.examForm.get('quiz')?.get('quizId')?.value);
    const val = {...this.examForm.value,quiz:{
      quizId:+this.examForm.get('quiz')?.get('quizId')?.value
    }}
    // console.log(val);

    this.examService.save(val).subscribe(ex => {
      this.router.navigateByUrl('/exam/addQuestion',{state:
        {
        quizId:Number(this.examForm.get('quiz')?.get('quizId')?.value),
        examId:ex.examId
        }
      })
    },
    err => alert(err))
  }
}
