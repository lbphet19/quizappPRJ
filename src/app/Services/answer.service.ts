import { Answer } from './../Model/answer';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  private API_URL:string = 'https://spring-boot-angular-quiz-app.herokuapp.com/api/v2/answer'
  constructor(private http:HttpClient) { }
  get():Observable<Answer[]>{
    return this.http.get<Answer[]>(this.API_URL)
  }
  getById(id:number):Observable<Answer>{
    return this.http.get<Answer>(`${this.API_URL}/${id}`)
  }
  save(answer:Answer):Observable<Answer>{
    return this.http.post<Answer>(`${this.API_URL}`,answer)
  }
  saveMultipleAnswers(answers:Answer[]):Observable<Answer[]>{
    return this.http.post<Answer[]>(`${this.API_URL}/multiple`,answers)
  }
  update(answer:Answer):Observable<Answer>{
    return this.http.put<Answer>(`${this.API_URL}/update`,answer)
  }
  updateMultipleAnswers(answers:Answer[]):Observable<Answer[]>{
    return this.http.put<Answer[]>(`${this.API_URL}/update/multiple`,answers)
  }
  updateMultiple(answers:any):Observable<any>{
    return this.http.put<any>(`${this.API_URL}/update/multiple`,answers)
  }
  delete(id:number):Observable<String>{
    return this.http.delete<String>(`${this.API_URL}/delete/${id}`)
  }
  deleteMultiple(ids:number[]):Observable<String>{
    return this.http.post<String>(`${this.API_URL}/delete/multiple`,ids)
  }
  convertAnswerFormToAnswerRequest(questionId:number,answers:any[]){
    const answerRequest:any[] = []
    for(let answer of answers){
        answerRequest.push({
          question:{
            questionId:questionId
          },
          answerContent:answer.answerContent,
          answerIsCorrect:answer.answerIsCorrect
        })
    }
    return answerRequest

  }
  convertAnswerUpdateForm(updateAnswers: any[], value: any): any {
    const answerRequest:any[] = []
    for(let answer of updateAnswers){
        answerRequest.push({...answer,
          question:{
            questionId:value
          }
        })
    }
    return answerRequest
  }
}
