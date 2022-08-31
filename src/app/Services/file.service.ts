import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
const API_URL = 'http://localhost:8080/api/v2'
@Injectable({
  providedIn: 'root'
})

export class FileService {

  constructor(private http:HttpClient) { }
  upload(file:File):Observable<any>{
    let fd : FormData = new FormData
    fd.append('file',file)
    return this.http.post<any>(`${API_URL}/uploads`,fd)
  }
}
