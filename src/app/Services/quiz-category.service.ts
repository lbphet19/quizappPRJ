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
  save(category:any):Observable<Category>{
    return this.http.post<Category>(`${API_URL}`,category)
  }
  put(category:any):Observable<Category>{
    return this.http.put<Category>(`${API_URL}`,category)
  }
  get():Observable<Category[]>{
    return this.http.get<Category[]>(`${API_URL}`)
  }  
  getById(id:any):Observable<Category>{
    return this.http.get<Category>(`${API_URL}/${id}`)
  }  
  getRootCategory():Observable<Category[]>{
    return this.http.get<Category[]>(`${API_URL}/rootCategory`)
  }
  getChildCategory():Observable<Category[]>{
    return this.http.get<Category[]>(`${API_URL}/childCategory`)
  }
}
