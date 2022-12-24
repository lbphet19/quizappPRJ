import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { QuizCategoryService } from 'src/app/Services/quiz-category.service';
import { QuizService } from 'src/app/Services/quiz.service';

@Component({
  selector: 'app-category-view-quiz',
  templateUrl: './category-view-quiz.component.html',
  styleUrls: ['./category-view-quiz.component.css']
})
export class CategoryViewQuizComponent implements OnInit {

  constructor(private router:Router, private route:ActivatedRoute,
    private quizService: QuizService, private categoryService: QuizCategoryService,
    private location: Location, private modalService: NgbModal) { }

  updateId = -1
  quizUpdateForm!:FormGroup
  quizList :any[] = []
  category:any
  ngOnInit(): void {
    let categoryId = this.route.snapshot.params['id']
    this.categoryService.getById(categoryId).subscribe(data => 
      this.category = data)
    this.quizService.getByCategory(categoryId).subscribe(
      data =>{
        this.quizList = data
      } 
    )
    this.initQuizUpdateForm()
  }

  initQuizUpdateForm(){
    this.quizUpdateForm = new FormGroup({
      quizId: new FormControl(''),
      quizName: new FormControl('')
  })
  }

  createQuiz(){
    this.router.navigate(['quiz','create'])
  }
  view(id:any){
    this.router.navigate(['user','quiz',id])
  }
  back(){
    this.router.navigate(['quiz','category-list'])
  }
  closeModal(){
    this.modalService.dismissAll()
  }
  update(content:any,item:any){
    this.updateId = item.quizId
    this.quizUpdateForm.patchValue(
      {
        quizName:item.quizName,
        quizId:item.quizId
      }
    )
    // this.updateId = id
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
			},
			(reason) => {
			},
		);
  } 
  updateModal(){
    this.quizService.update(this.quizUpdateForm.value).subscribe(data => window.location.reload())
  }
}
