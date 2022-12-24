import { Router } from '@angular/router';
import { TokenStorageService } from './token-storage.service';
import { Observable } from 'rxjs';
// import { _throw } from 'rxjs/observable/throw'
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptorService implements HttpInterceptor{

  constructor(private TokenStorageService:TokenStorageService, private Router:Router) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(err => {
      if(err.status === 401){
          this.TokenStorageService.signOut()
          alert('error 401!')
          this.Router.navigate(['quiz','all'])
      }
      console.log(err)
      return Observable.throw(err.status)
    }))
  }
}
