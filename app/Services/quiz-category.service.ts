import { Category } from './../Model/category';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
const API_URL = 'http://localhost:8080/api/v2/category'
@Injectable({
  providedIn: 'root'
})
export class QuizCategoryService {

  constructor(private http:HttpClient) { }
  get():Observable<Category[]>{
    return this.http.get<Category[]>(`${API_URL}`)
  }
}
