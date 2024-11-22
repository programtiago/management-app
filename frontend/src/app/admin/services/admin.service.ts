import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user';
import { first } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly API_USERS = 'http://localhost:8080/api/v1/users'

  constructor(private httpClient: HttpClient) { }

  list(){
    return this.httpClient.get<User[]>(`${this.API_USERS}/all`)
      .pipe(first());
  }

  getById(id: string){
    return this.httpClient.get<User>(`${this.API_USERS}/` + id)
  }

  post(userData: any){
    return this.httpClient.post<User>(`${this.API_USERS}/new`, userData)
  }

  desativateUser(userId: string){
    return this.httpClient.post(`${this.API_USERS}/desativate/` + userId, {})
  }

  activateUser(userId: string){
    return this.httpClient.post(`${this.API_USERS}/activate/` + userId, {})
  }
}
