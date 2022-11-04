import { answerResponseDTO } from './../Model/answerResponseDTO';
import { AnswerRequestDTO } from './../Model/answer-request-dto';
import { Question } from './../Model/question';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private API_URL:string = 'http://localhost:8080/api/v2/question'
  constructor(private http:HttpClient) { }
  get():Observable<Question[]>{
    return this.http.get<Question[]>(this.API_URL)
  }
  getById(id:number):Observable<Question>{
    return this.http.get<Question>(`${this.API_URL}/${id}`)
  }
  getByQuizId(quizId:number):Observable<Question[]>{
    return this.http.get<Question[]>(`${this.API_URL}/quizId/${quizId}`)
  }
  getByExamId(quizId:number):Observable<Question[]>{
    return this.http.get<Question[]>(`${this.API_URL}/quizId/${quizId}`)
  }
  save(question:Question):Observable<Question>{
    return this.http.post<Question>(`${this.API_URL}`,question)
  }
  saveMultipleQuestions(questions:Question[]):Observable<Question[]>{
    return this.http.post<Question[]>(`${this.API_URL}/multiple`,questions)
  }
  update(question:Question):Observable<Question>{
    return this.http.put<Question>(`${this.API_URL}/update`,question)
  }
  updateMultipleQuestions(questions:Question[]):Observable<Question[]>{
    return this.http.put<Question[]>(`${this.API_URL}/update/multiple`,questions)
  }
  // postAnswers(quizId:number,body:AnswerRequestDTO[]):Observable<any>{
  //   return this.http.post(`${this.API_URL}/postAnswers/${quizId}`,body)
  // }
  delete(id:number):Observable<String>{
    return this.http.delete<String>(`${this.API_URL}/delete/${id}`)
  }
  deleteMultiple(ids:number[]):Observable<String>{
    return this.http.post<String>(`${this.API_URL}/delete/multiple`,ids)
  }
  postAnswers(quizId:number,AnswerRequestDTO: AnswerRequestDTO[]):Observable<answerResponseDTO>{
    return this.http.post<answerResponseDTO>(`${this.API_URL}/postAnswers/${quizId}`,AnswerRequestDTO)
  }
  postAnswersTest(quizId:number,AnswerRequestDTO: AnswerRequestDTO[]):Observable<answerResponseDTO>{
    return this.http.post<answerResponseDTO>(`${this.API_URL}/postAnswersTest/${quizId}`,AnswerRequestDTO)
  }
  postExamAnswer(quizId:number,AnswerRequestDTO: AnswerRequestDTO[]):Observable<answerResponseDTO>{
    return this.http.post<answerResponseDTO>(`${this.API_URL}/postAnswer/${quizId}`,AnswerRequestDTO)
  }
  convertQuestionFormToQuestionRequest(quizId:number,questions:any[]){
    const questionRequest:any[] = []
    questions.forEach(question => {
      questionRequest.push({
        quiz:{
          quizId:Number(quizId)
        },
        questionContent:question.questionContent,
        questionType:question.questionType
      })
    })
    return questionRequest
  }
  convertQuestionUpdate(value: any) {
    let questionReq:any = {
      questionId:value.questionId,
          questionContent:value.questionContent,
          questionType:value.questionType
    }
    return questionReq
  }
}
