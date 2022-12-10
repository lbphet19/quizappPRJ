import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ExamServiceService } from 'src/app/Services/exam-service.service';
import { QuizCategoryService } from 'src/app/Services/quiz-category.service';

@Component({
  selector: 'app-category-view-exam',
  templateUrl: './category-view-exam.component.html',
  styleUrls: ['./category-view-exam.component.css']
})
export class CategoryViewExamComponent implements OnInit {
  updateId:any
  exams:any
  category : any
  categoryId:any
  examUpdateForm!:FormGroup
  closeResult=''
  constructor(private examService: ExamServiceService,
    private catService: QuizCategoryService,
    private route:ActivatedRoute,
    private location : Location,
    private router:Router,
    private modalService: NgbModal,
    private formBuilder:FormBuilder) { }
    
    ngOnInit(): void {
      const id = this.route.snapshot.params['id']
      this.categoryId = id
      this.catService.getById(id).subscribe(data => this.category = data)
      this.examService.getByCategoryId(id).subscribe(data => this.exams = data)
      this.initForm()
    }
    initForm(){
      this.examUpdateForm = this.formBuilder.group(
        {
          examName: new FormControl(''),
          descriptions:new FormControl(''),
          examImage:new FormControl('https://cdn.pixabay.com/photo/2021/02/03/05/37/question-mark-5976736_960_720.png'),
          shuffleQuestion:new FormControl(false),
          shuffleAnswer:new FormControl(false),
          hours:new FormControl(''),
          mins: new FormControl(''),
          secs: new FormControl('')
        }
        )
      }
      createExam(){
        this.router.navigate(['exam','create'])
      }
      view(examId:any){ 
        this.router.navigate(['exam',examId,'addQuestion'],
        {queryParams:{category:this.categoryId}})
      }
      open(content:any,exam:any) {
        this.updateId = exam.examId
        this.examUpdateForm.patchValue(
          {
            examName: exam.examName,
            descriptions :exam.descriptions,
            examImage:exam.examImage,
            shuffleQuestion:exam.shuffleQuestion,
            shuffleAnswer:exam.shuffleAnswer,
            hours: Math.floor(exam.time/3600),
            mins: Math.floor((exam.time % 3600)/60),
            secs: exam.time % 60
          }
          )
          // this.updateId = id
          this.modalService.open(content, { size:'lg', ariaLabelledBy: 'modal-basic-title' }).result.then(
            (result) => {
              this.closeResult = `Closed with: ${result}`;
            },
            (reason) => {
              this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
            },
            );
          }
          closeModal(){
            this.updateId = -1
            this.modalService.dismissAll('Cross click')
          }
          updateModal(){
            const val = {
              examName: this.examUpdateForm.get('examName')?.value,
              descriptions: this.examUpdateForm.get('descriptions')?.value,
              examImage: this.examUpdateForm.get('examImage')?.value,
              examId: this.updateId,
              shuffleQuestion: this.examUpdateForm.get('shuffleQuestion')?.value,
              shuffleAnswer: this.examUpdateForm.get('shuffleAnswer')?.value,
              time: this.convertTimeToSec(),
            }
            // console.log(val);
            
            this.examService.put(val).subscribe(ex => {
              location.reload()
            },
            err => alert(err))
          }
          
          
          convertTimeToSec(){
            let hrs = Number(this.examUpdateForm.get('hours')?.value)
            let mins = Number(this.examUpdateForm.get('mins')?.value)
            let secs = Number(this.examUpdateForm.get('secs')?.value)
            console.log(hrs * 3600 + mins * 60 + secs)
            return hrs * 3600 + mins * 60 + secs
          }
          
          private getDismissReason(reason: any): string {
            if (reason === ModalDismissReasons.ESC) {
              return 'by pressing ESC';
            } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
              return 'by clicking on a backdrop';
            } else {
              return `with: ${reason}`;
            }
          }
          back(){
            this.router.navigate(['quiz','category-list'])
          }
        }
        