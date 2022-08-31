import { Quiz } from './../Model/quiz';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { QuizCategory } from '../Model/quizCategory';

@Injectable({
  providedIn: 'root'
})
export class QuizService {
  private API_URL : string = 'https://spring-boot-angular-quiz-app.herokuapp.com/api/v2/quiz'
  constructor(private http:HttpClient) { }
  get():Observable<Quiz[]>{
    return this.http.get<Quiz[]>(this.API_URL)
  }
  getById(id:number):Observable<Quiz>{
    return this.http.get<Quiz>(`${this.API_URL}/${id}`)
  }
  getExamByQuizId(id:number):Observable<any[]>{
    return this.http.get<any[]>(`${this.API_URL}/getQuizExam/${id}`)
  }
  getByCategory(catId:number):Observable<Quiz[]>{
    return this.http.get<Quiz[]>(`${this.API_URL}/category/${catId}`)
  }
  getQuizAtHomePage():Observable<QuizCategory[]>{
    return this.http.get<QuizCategory[]>(`${this.API_URL}/getQuizByCatAtHomepage`)
  }
  getUserQuiz():Observable<Quiz[]>{
    return this.http.get<Quiz[]>(`${this.API_URL}/getUserQuiz`)
  }
  search(keyword:string):Observable<Quiz[]>{
    return this.http.get<Quiz[]>(`${this.API_URL}/search?keyword=${keyword}`)
  }
  update(quiz:Quiz):Observable<Quiz>{
    return this.http.put<Quiz>(`${this.API_URL}/update`,quiz)
  }
  save(quiz:Quiz):Observable<Quiz>{
    return this.http.post<Quiz>(`${this.API_URL}`,quiz)
  }
  saveMultipleQuiz(quiz:Quiz[]):Observable<Quiz[]>{
    return this.http.post<Quiz[]>(`${this.API_URL}/multiple`,quiz)
  }
  delete(id:number):Observable<String>{
    return this.http.delete<String>(`${this.API_URL}/delete/${id}`)
  }
}
