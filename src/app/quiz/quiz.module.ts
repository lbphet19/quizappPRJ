import { HeaderComponent } from './header/header.component';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuizRoutingModule } from './quiz-routing.module';
import { QuizComponent } from './quiz.component';
import { QuizListComponent } from './quiz-list/quiz-list.component';
import { QuizListByCategoryComponent } from './quiz-list-by-category/quiz-list-by-category.component';
import { QuizDetailComponent } from './quiz-detail/quiz-detail.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { QuizCreateComponent } from './quiz-create/quiz-create.component';
import { QuizFormComponent } from './quiz-form/quiz-form.component';
import { QuestionComponent } from './question/question.component';
import { ReactiveFormsModule } from '@angular/forms';
import { QuizQuestionComponent } from './quiz-question/quiz-question.component';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { AuqizScoreComponent } from './auqiz-score/auqiz-score.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { CategoryCreateComponent } from './category-create/category-create.component';
import { QuizIndexComponent } from './quiz-index/quiz-index.component';
import { CategoryViewQuizComponent } from './category-view-quiz/category-view-quiz.component';
import { CategoryViewExamComponent } from './category-view-exam/category-view-exam.component';
import { UserSidebarComponent } from './user-sidebar/user-sidebar.component';
import { HistoryComponent } from './history/history.component';
import { AdminComponent } from './admin/admin.component';
import { LoginComponent } from './login/login.component';
import { QuizSearchComponent } from './quiz-search/quiz-search.component';


@NgModule({
  declarations: [QuizComponent, QuizListComponent, QuizListByCategoryComponent, QuizDetailComponent,
  HeaderComponent,
  SidebarComponent,
  QuizCreateComponent,
  QuizFormComponent,
  QuestionComponent,
  QuizQuestionComponent,
  AuqizScoreComponent,
  CategoryListComponent,
  CategoryCreateComponent,
  QuizIndexComponent,
  CategoryViewQuizComponent,
  CategoryViewExamComponent,
  UserSidebarComponent,
  HistoryComponent,
  AdminComponent,
  LoginComponent,
  QuizSearchComponent],
  imports: [
    CommonModule,
    QuizRoutingModule,
    ReactiveFormsModule,
    NgbCarouselModule,

  ]
})
export class QuizModule { }
