import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ExamServiceService } from 'src/app/Services/exam-service.service';
import { QuizCategoryService } from 'src/app/Services/quiz-category.service';
import { QuizService } from 'src/app/Services/quiz.service';

@Component({
  selector: 'app-category-create',
  templateUrl: './category-create.component.html',
  styleUrls: ['./category-create.component.css'],
})
export class CategoryCreateComponent implements OnInit {
  constructor(
    private quizService: QuizService,
    private examService: ExamServiceService,
    private router: Router,
    private quizCatService:QuizCategoryService
  ) {}

  quizzes!: Observable<any[]>;
  rootCategory:any
  catForm!: FormGroup;
  ngOnInit(): void {
    this.initForm();
    this.quizzes = this.quizService.get();
    this.quizCatService.getRootCategory().subscribe(data => this.rootCategory = data)
  }

  initForm() {
    this.catForm = new FormGroup({
      categoryName: new FormControl(''),
      id: new FormControl(''),
    });
  }
  submit(event: any) {
    event.preventDefault();
    // console.log(this.examForm.get('quiz')?.get('quizId')?.value);    
    const parentVal = this.catForm.get('id')?.value === "0" ? null : {id : +this.catForm.get('id')?.value}
    const val = {
      categoryName: this.catForm.get('categoryName')?.value,
      parent: parentVal
    };
    // console.log(val);

     this.quizCatService.save(val).subscribe(
      (ex) => {
        // error?
        this.router.navigate(['quiz','category-list'])
      },
      (err) => alert(err)
    ); 
  }
}
