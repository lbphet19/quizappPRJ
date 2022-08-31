import { Question } from './../Model/question';
import { Exam } from './../Model/exam';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class ExamServiceService {
  private API_URL : string = 'https://spring-boot-angular-quiz-app.herokuapp.com/api/v2/exam'
  constructor(private http:HttpClient) { }
  get():Observable<Exam[]>{
    return this.http.get<Exam[]>(this.API_URL)
  }
  getById(id:number):Observable<Exam>{
    return this.http.get<Exam>(`${this.API_URL}/${id}`)
  }
  addQuestion(obj:any):Observable<any>{
    return this.http.post<any>(`${this.API_URL}/addQuestion`,obj)
  }
  // getByCategory(catId:number):Observable<Quiz[]>{
  //   return this.http.get<Quiz[]>(`${this.API_URL}/category/${catId}`)
  // }
  // getQuizAtHomePage():Observable<QuizCategory[]>{
  //   return this.http.get<QuizCategory[]>(`${this.API_URL}/getQuizByCatAtHomepage`)
  // }
  // getUserQuiz():Observable<Quiz[]>{
  //   return this.http.get<Quiz[]>(`${this.API_URL}/getUserQuiz`)
  // }
  // search(keyword:string):Observable<Quiz[]>{
  //   return this.http.get<Quiz[]>(`${this.API_URL}/search?keyword=${keyword}`)
  // }
  // update(quiz:Quiz):Observable<Quiz>{
  //   return this.http.put<Quiz>(`${this.API_URL}/update`,quiz)
  // }
  save(exam:Exam):Observable<Exam>{
    return this.http.post<Exam>(`${this.API_URL}`,exam)
  }
  getQuestionByExamId(examId:number):Observable<Question[]>{
    return this.http.get<Question[]>(`${this.API_URL}/${examId}/getQuestion`)
  }
  // saveMultipleQuiz(quiz:Quiz[]):Observable<Quiz[]>{
  //   return this.http.post<Quiz[]>(`${this.API_URL}/multiple`,quiz)
  // }
  // delete(id:number):Observable<String>{
  //   return this.http.delete<String>(`${this.API_URL}/delete/${id}`)
  // }
}
