import { AuqizScoreComponent } from './auqiz-score/auqiz-score.component';
import { QuizFormComponent } from './quiz-form/quiz-form.component';
import { QuizCreateComponent } from './quiz-create/quiz-create.component';
import { QuizDetailComponent } from './quiz-detail/quiz-detail.component';
import { QuizListByCategoryComponent } from './quiz-list-by-category/quiz-list-by-category.component';
import { QuizListComponent } from './quiz-list/quiz-list.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { QuizComponent } from './quiz.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { CategoryCreateComponent } from './category-create/category-create.component';

const routes: Routes = [
  { path: '', component: QuizListComponent },
  {path:'detail/:id',component:QuizDetailComponent},
  {path:'detail',component:QuizDetailComponent},
  {path: 'all',component:QuizListComponent},
  {path:'category/:id',component:QuizListByCategoryComponent},
  {path:'create',component:QuizCreateComponent},
  {path:'attempt/:id',component:QuizFormComponent},
  {path:'score',component:AuqizScoreComponent},
  {path:'category-list',component:CategoryListComponent},
  {path:'createCategory',component:CategoryCreateComponent},
  

  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuizRoutingModule { }
