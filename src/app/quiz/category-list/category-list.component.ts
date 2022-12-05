import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { QuizCategoryService } from 'src/app/Services/quiz-category.service';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {
  
  constructor(private quizCatService:QuizCategoryService,
    private router:Router,private modalService: NgbModal,
    private fb : FormBuilder) { }
  catUpdateForm!:FormGroup
  updateId = 0
  catList:any
  closeResult = ''
  rootCategory:any

  ngOnInit(): void {
    this.initForm()
    // this.catList = this.quizCatService.getRootCategory()
 this.quizCatService.getRootCategory().subscribe(data => 
  {
    this.catList = data
  })
  this.quizCatService.getRootCategory().subscribe(data => this.rootCategory = data)


  }

  initForm(){
    this.catUpdateForm = this.fb.group({
      categoryName: new FormControl(''),
      id: new FormControl('')
    })
  }
  createCategory(){
    this.router.navigate(['quiz','createCategory'])
  }


	open(content:any,parent:any,item:any) {
    this.updateId = item.id
    this.catUpdateForm.patchValue(
      {
        categoryName:item.categoryName,
        id:parent.id
      }
    )
    // this.updateId = id
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
			},
			(reason) => {
				this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
			},
		);
	}
  view(cat:any){
    // console.log(cat.id)
    this.router.navigate(['quiz','category',cat.id,'viewQuiz'])
  }
  viewExam(cat:any){
    this.router.navigate(['quiz','category',cat.id,'viewExam'])
  }
  async updateModal(){
    const parentVal = this.catUpdateForm.get('id')?.value === "0" ? null : {id : +this.catUpdateForm.get('id')?.value}
    const val = {
      id: this.updateId,
      categoryName: this.catUpdateForm.get('categoryName')?.value,
      parent: parentVal
    };
    this.quizCatService.put(val).subscribe(
      data => {
        alert('Cập nhật thành công')
        this.modalService.dismissAll('Cross click')
        window.location.reload()
      }, error => {
        this.modalService.dismissAll('Cross click')
        alert('Có lỗi!')
      }
    )
    this.updateId = -1
  
  }
  closeModal(){
    this.updateId = -1
    this.modalService.dismissAll('Cross click')
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
}
