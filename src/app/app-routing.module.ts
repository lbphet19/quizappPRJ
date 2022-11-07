import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  // {path : '', component:}
  { path: '', loadChildren: () => import('./quiz/quiz.module').then(m => m.QuizModule) },
  { path: 'auth', loadChildren: () => import('./authenication/authenication.module').then(m => m.AuthenicationModule) },
  { path: 'user', loadChildren: () => import('./user/user.module').then(m => m.UserModule) },
  { path: 'quiz', loadChildren: () => import('./quiz/quiz.module').then(m => m.QuizModule) },
  { path: 'exam', loadChildren: () => import('./exam/exam.module').then(m => m.ExamModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
