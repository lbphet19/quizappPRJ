import { UserQuizComponent } from './user-quiz/user-quiz.component';
import { QuizAdminComponent } from './quiz-admin/quiz-admin.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserComponent } from './user.component';

const routes: Routes = [
  { path: '', component: UserComponent },
  {path:'quiz',component:UserQuizComponent},
  {path:'quiz/:id',component:UserQuizComponent},
  {path:'admin/:id',component:QuizAdminComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class UserRoutingModule {

}
