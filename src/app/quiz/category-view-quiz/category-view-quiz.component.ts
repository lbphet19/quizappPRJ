import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { QuizCategoryService } from 'src/app/Services/quiz-category.service';
import { QuizService } from 'src/app/Services/quiz.service';

@Component({
  selector: 'app-category-view-quiz',
  templateUrl: './category-view-quiz.component.html',
  styleUrls: ['./category-view-quiz.component.css']
})
export class CategoryViewQuizComponent implements OnInit {

  constructor(private router:Router, private route:ActivatedRoute,
    private quizService: QuizService, private categoryService: QuizCategoryService) { }

  quizList :any[] = []
  category:any
  ngOnInit(): void {
    let categoryId = this.route.snapshot.params['id']
    this.categoryService.getById(categoryId).subscribe(data => 
      this.category = data)
    this.quizService.getByCategory(categoryId).subscribe(
      data =>{
        console.log(data)
        this.quizList = data
      } 
    )
  }

  createQuiz(){
    this.router.navigate(['quiz','create'])
  }
  view(id:any){
    this.router.navigate(['user','quiz',id])
  }
}
