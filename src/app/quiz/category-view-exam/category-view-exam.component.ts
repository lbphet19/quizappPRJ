import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ExamServiceService } from 'src/app/Services/exam-service.service';

@Component({
  selector: 'app-category-view-exam',
  templateUrl: './category-view-exam.component.html',
  styleUrls: ['./category-view-exam.component.css']
})
export class CategoryViewExamComponent implements OnInit {

  constructor(private examService: ExamServiceService,
    private route:ActivatedRoute) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id']
    this.examService.getByCategoryId(id).subscribe(data => console.log(data))
  }

}
