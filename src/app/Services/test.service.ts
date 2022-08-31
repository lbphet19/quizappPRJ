import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TestService {
  private API_URL : string = 'https://spring-boot-angular-quiz-app.herokuapp.com/api/test'
  constructor(private http:HttpClient) { }
  getUserContent():Observable<string>{
    return this.http.get<string>(`${this.API_URL}/user`)
  }
  getAll():Observable<string>{
    return this.http.get<string>(`${this.API_URL}/all`)
  }
}
