import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/Model/category';
import { QuizCategoryService } from 'src/app/Services/quiz-category.service';

@Component({
  selector: 'app-quiz-search',
  templateUrl: './quiz-search.component.html',
  styleUrls: ['./quiz-search.component.css']
})
export class QuizSearchComponent implements OnInit {
  search = ''
  categories: any[] = []
  isLoading = true
  constructor(private catService:QuizCategoryService, 
    private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.searchCategory(params.search)
    })
  }
  searchCategory(search:any){
    this.catService.search(search).subscribe(data => {
      this.categories = data
      this.isLoading = false
    })
  }
  navigate(id:string){
    this.router.navigate(['quiz','detail',id])
  }

}
