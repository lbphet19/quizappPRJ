import { TokenStorageService } from './token-storage.service';
import { SignupRequest } from './../Model/signup-request';
import { LoginRequest } from './../Model/login-request';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  private API_URL:string = 'https://spring-boot-angular-quiz-app.herokuapp.com/api/auth'
  constructor(private http:HttpClient, private tokenService:TokenStorageService) { }
  signin(req:LoginRequest):Observable<any>{
    return this.http.post(`${this.API_URL}/signin`,req)
  }
  signup(req:SignupRequest):Observable<any>{
    return this.http.post(`${this.API_URL}/signup`,req)
  }
  signinWithGoogle(tokenId:String):Observable<any>{
    return this.http.post(`https://spring-boot-angular-quiz-app.herokuapp.com/api/social/google`,
      {value:tokenId},httpOptions)
  }
  isLoggedIn():boolean{
    return this.tokenService.getToken() === null? false:true
  }
}
