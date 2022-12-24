import { ExamServiceService } from './../../Services/exam-service.service';
import { FormBuilder, FormArray, FormGroup, FormControl } from '@angular/forms';
import { Question } from './../../Model/question';
import { QuestionService } from './../../Services/question.service';
import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Route } from '@angular/compiler/src/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbCarousel, NgbCarouselConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-quiz-form',
  templateUrl: './quiz-form.component.html',
  styleUrls: ['./quiz-form.component.css'],
  providers:[NgbCarouselConfig]
})
export class QuizFormComponent implements OnInit {
  @ViewChild('myCarousel') myCarousel! : NgbCarousel
  @ViewChild('content') modal!: TemplateRef<any>
  answerForm!: FormArray
  questions!:any[]
  id!:number
  countdown!: number
  examSize!:number
  currentIndex = 0
  constructor(private route:ActivatedRoute,
    private QuestionService:QuestionService,
    private fb:FormBuilder,
    private router:Router,
    private examService:ExamServiceService,
    private modalService:NgbModal,
    private config:NgbCarouselConfig,
    private elementRef: ElementRef
    ) { 
      config.showNavigationArrows = false
      
    }
    
    ngOnInit(): void {
      var timerDisplay = this.elementRef.nativeElement.querySelector('#timer')
      this.id = this.route.snapshot.params['id']
      this.examService.getQuestionByExamId(this.id).subscribe(data =>
        {
          if(data.countdown > 0){
            this.initCountdown(timerDisplay,data.countdown)
            this.countdown = data.countdown
            this.questions = data.questions
            this.examSize = data.questions.length
            this.initForm(data.questions)
          } else{
            this.modalService.open(this.modal)
          }
        })
        
      }
      initForm(questions:Question[]){
        
        this.answerForm = new FormArray([])
        for(let i of questions){
          this.answerForm.push(new FormGroup(
            {
              questionId: new FormControl(i.questionId),
              answerIds:new FormControl([])
            }
            ))
          }
          // this.answerForm.at(0).patchValue({answerIds:[1,2]})
          
        }
        
        initCountdown(timerDisplay:any, timer:number){
          
          setTimeout(() => {
           var hours = Math.floor(timer/3600)
           var minutes = Math.floor((timer % 3600)/60)
           var seconds = timer % 60
           
           var Shours = hours < 10 ? "0" + hours : String(hours);
           var Sminutes = minutes < 10 ? "0" + minutes : String(minutes);
           var Sseconds = seconds < 10 ? "0" + seconds : String(seconds);
           
           timerDisplay.textContent = Shours + ":" + Sminutes + ":" + Sseconds;
           if (timer === 0) {
             console.log('timer is ended');
             this.submit()
           } else{
             timer -= 1
             this.initCountdown(timerDisplay,timer)
           }
          },1000)
          
        
      }
      
      updateAnswer(answer:any){
        this.answerForm.at(answer.index).patchValue({answerIds:answer.answers})
        // console.log(this.answerForm.value)
      }
      submit(){
        
        this.QuestionService.postExamAnswer(this.id,this.answerForm.value).subscribe(
          value => {
            const ids:number[] = []
            window.sessionStorage.setItem('result',JSON.stringify(value))
            for(let i = 0; i < this.answerForm.length;i++){
              ids.push(...this.answerForm.at(i).get('answerIds')!.value)
            }
            window.sessionStorage.setItem('selected',JSON.stringify(ids))
            window.sessionStorage.setItem('examId',JSON.stringify(this.id))
            this.router.navigate(['quiz','score',{quiz:this.id}])
          }
          )
        }
        back(){
          this.examService.getExamCategoryId(this.id).subscribe(id => {
            this.modalService.dismissAll()
            this.router.navigate(['quiz','detail',id])
          })
        }
        jumpTo(index:number){
          this.currentIndex = index
          this.myCarousel.select(index.toString())
        }
      }
      