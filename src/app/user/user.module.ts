import { QuizAdminComponent } from './quiz-admin/quiz-admin.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { UserQuizComponent } from './user-quiz/user-quiz.component';
import { QuizCreateComponent } from './quiz-create/quiz-create.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';


@NgModule({
  declarations: [UserComponent,QuizAdminComponent, UserQuizComponent, QuizCreateComponent, SidebarComponent, HeaderComponent, LoginComponent],
  imports: [
    CommonModule,
    UserRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class UserModule { }
