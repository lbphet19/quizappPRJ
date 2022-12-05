import { Router } from '@angular/router';
import { QuizService } from './../../Services/quiz.service';
import { Observable } from 'rxjs';
import { FormGroup, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ExamServiceService } from 'src/app/Services/exam-service.service';
import { QuizCategoryService } from 'src/app/Services/quiz-category.service';

@Component({
  selector: 'app-exam-create',
  templateUrl: './exam-create.component.html',
  styleUrls: ['./exam-create.component.css']
})
export class ExamCreateComponent implements OnInit {

  constructor(private quizService:QuizService,
              private examService:ExamServiceService,
              private categoryService: QuizCategoryService,
              private router:Router
    ) { }

  categories!:Observable<any[]>
  examForm!:FormGroup
  ngOnInit(): void {
    this.initForm()
    this.categories = this.categoryService.getChildCategory()

  }

  initForm(){
    this.examForm = new FormGroup({
      examName: new FormControl(''),
      descriptions:new FormControl(''),
      examImage:new FormControl('https://cdn.pixabay.com/photo/2021/02/03/05/37/question-mark-5976736_960_720.png'),
      quizCategory:new FormGroup({
        id:new FormControl()
      }
      )
    })
  }
  submit(event:any){
    event.preventDefault()
    const val = this.examForm.value
    // console.log(val);

    this.examService.save(val).subscribe(ex => {
      this.router.navigateByUrl('/exam/addQuestion',{state:
        {
        quizId:Number(this.examForm.get('quiz')?.get('quizId')?.value),
        examId:ex.examId
        }
      })
      this.router.navigate(['exam',ex.examId,'addQuestion'])
    },
    err => alert(err))
  }
}
