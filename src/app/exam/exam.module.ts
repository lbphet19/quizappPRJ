import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExamRoutingModule } from './exam-routing.module';
import { ExamComponent } from './exam.component';
import { ExamCreateComponent } from './exam-create/exam-create.component';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ExamAddQuestionComponent } from './exam-add-question/exam-add-question.component';


@NgModule({
  declarations: [ExamComponent, ExamCreateComponent, HeaderComponent, SidebarComponent, ExamAddQuestionComponent],
  imports: [
    CommonModule,
    ExamRoutingModule,
    ReactiveFormsModule
  ]
})
export class ExamModule { }
