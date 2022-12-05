import { ExamAddQuestionComponent } from './exam-add-question/exam-add-question.component';
import { ExamCreateComponent } from './exam-create/exam-create.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ExamComponent } from './exam.component';

const routes: Routes = [{ path: '', component: ExamComponent },
                        { path: 'create',component:ExamCreateComponent},
                        {path: 'addQuestion',component:ExamAddQuestionComponent},
                        {path: ':id/addQuestion',component:ExamAddQuestionComponent}
                      ];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExamRoutingModule { }
