import { Quiz } from './../../Model/quiz';
import { Observable } from 'rxjs';

import { QuizService } from './../../Services/quiz.service';
import { Component, OnInit,  } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Route } from '@angular/compiler/src/core';
import { ExamServiceService } from 'src/app/Services/exam-service.service';
import { QuizCategoryService } from 'src/app/Services/quiz-category.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-quiz-detail',
  templateUrl: './quiz-detail.component.html',
  styleUrls: ['./quiz-detail.component.css']
})
export class QuizDetailComponent implements OnInit {
  item = 
  {
  examId: -1,
  examName:''
  }
  quiz!:Quiz
  id!:number
  exams!:any
  category!: any
  constructor(private route:ActivatedRoute,
    private QuizService:QuizService,
    private examService : ExamServiceService,
    private catService : QuizCategoryService,
    private router:Router,
    private modalService:NgbModal) {
      this.id = this.route.snapshot.params['id'];
      // this.QuizService.getById(this.id).subscribe(data => this.quiz = data)
      this.catService.getById(this.id).subscribe(data => {
        this.category = data
      }
      )
    }
    
    
    ngOnInit(): void {
      this.examService.getByCategoryId(this.id).subscribe(data => 
        {
          this.exams = this.formatTime(data)
        })
      }

      open(content:any,exam:any) {
        this.item.examName = exam.examName
        this.item.examId = exam.examId
        this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
          (result) => {
            // this.closeResult = `Closed with: ${result}`;
          },
          (reason) => {
            // this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
          },
        );
      }

      formatTime(exams:any[]){
        return exams.map( item => {return {
          ...item,
          hours: Math.floor(item.time/3600),
          mins: Math.floor((item.time % 3600)/60),
          secs: item.time % 60
        }})
        
      }
      back(){
        this.router.navigate([''])
      }
      attempt(id:number){
        this.modalService.dismissAll()
        this.router.navigate(['quiz','attempt',id])
      }
      attemptExam(id:number){
        this.router.navigate(['quiz','attempt',id])
      }
    }
    