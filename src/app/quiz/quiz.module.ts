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


@NgModule({
  declarations: [QuizComponent, QuizListComponent, QuizListByCategoryComponent, QuizDetailComponent,
  HeaderComponent,
  SidebarComponent,
  QuizCreateComponent,
  QuizFormComponent,
  QuestionComponent,
  QuizQuestionComponent,
  AuqizScoreComponent],
  imports: [
    CommonModule,
    QuizRoutingModule,
    ReactiveFormsModule,
    NgbCarouselModule,

  ]
})
export class QuizModule { }
