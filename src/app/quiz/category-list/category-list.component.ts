import { Component, OnInit } from '@angular/core';
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
    private router:Router,private modalService: NgbModal) { }
    
  updateId = 0
  catList:any
  closeResult = ''

  ngOnInit(): void {
    // this.catList = this.quizCatService.getRootCategory()
 this.quizCatService.getRootCategory().subscribe(data => 
  {
    this.catList = data
    console.log(data)
  })


  }
  createCategory(){
    this.router.navigate(['quiz','createCategory'])
  }


	open(content:any,id:number) {
    this.updateId = id
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
			},
			(reason) => {
				this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
			},
		);
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
