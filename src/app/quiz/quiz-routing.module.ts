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
import { QuizIndexComponent } from './quiz-index/quiz-index.component';
import { CategoryViewExamComponent } from './category-view-exam/category-view-exam.component';
import { CategoryViewQuizComponent } from './category-view-quiz/category-view-quiz.component';
import { HistoryComponent } from './history/history.component';
import { QuizSearchComponent } from './quiz-search/quiz-search.component';

const routes: Routes = [
  { path: '', component: QuizListComponent },
  { path: 'history', component: HistoryComponent },
  {path:'detail/:id',component:QuizDetailComponent},
  {path:'detail',component:QuizDetailComponent},
  {path: 'all',component:QuizListComponent},
  {path:'category/:id',component:QuizListByCategoryComponent},
  {path:'create',component:QuizCreateComponent},
  {path:'attempt/:id',component:QuizFormComponent},
  {path:'score',component:AuqizScoreComponent},
  {path:'category-list',component:CategoryListComponent},
  {path:'createCategory',component:CategoryCreateComponent},
  {path:'index',component:QuizIndexComponent},
  {path:'category/:id/viewExam',component:CategoryViewExamComponent},
  {path:'category/:id/viewQuiz',component:CategoryViewQuizComponent},
  {path:'quiz/search',component:QuizSearchComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuizRoutingModule { }
