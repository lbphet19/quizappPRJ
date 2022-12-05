import { QuizCategoryService } from './../../Services/quiz-category.service';
import { Observable } from 'rxjs';
import { FileService } from './../../Services/file.service';
import { Quiz } from './../../Model/quiz';

import { QuizService } from './../../Services/quiz.service';
import { TestService } from './../../Services/test.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Category } from 'src/app/Model/category';
import { Router } from '@angular/router';

@Component({
  selector: 'app-quiz-create',
  templateUrl: './quiz-create.component.html',
  styleUrls: ['./quiz-create.component.css']
})
export class QuizCreateComponent implements OnInit {
  quizCreateForm!:FormGroup;
  content!:string
  quizzez!:Quiz[]
  quizCategories!:Observable<Category[]>
  file!:File
  constructor(private testService:TestService,
    private QuizService:QuizService,
    private FileService:FileService,
    private QuizCategoryService:QuizCategoryService,
    private fb:FormBuilder,
    private router:Router) { }

  ngOnInit(): void {
    this.quizCategories = this.QuizCategoryService.getChildCategory()
    this.QuizService.get().subscribe(
      quiz => this.quizzez = quiz
    )
    this.initForm()
  }
  initForm(){
    this.quizCreateForm = this.fb.group({
      quizName:new FormControl('',Validators.required),
      descriptions:new FormControl('',Validators.required),
      quizStatus: new FormControl(true,Validators.required),
      quizCategory: new FormControl('',Validators.required)
    })
  }
  onChange(event:any){
    this.file = event.target.files[0]
  }
  // async submit(){
    submit(){
    // console.log(this.quizCreateForm.value)
    // let data = await this.FileService.upload(this.file).toPromise()
    let quizCreate = this.quizCreateForm.value
    quizCreate.quizImage = "https://cdn.pixabay.com/photo/2021/02/03/05/37/question-mark-5976736_960_720.png"
    // quizCreate.quizImage = data.fileDownloadUri
    quizCreate.quizCategory = {
      id: this.quizCreateForm.controls['quizCategory'].value
    }
    this.QuizService.save(quizCreate).subscribe(data => {
      console.log(data)
      this.router.navigate(['user','quiz',data.quizId])
    })

    // api/v2/uploads (param 'file')
  }
}
