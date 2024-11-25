import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user';
import { first } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly BASE_API_URL_USERS = '/api/v1/users'

  constructor(private httpClient: HttpClient) { }

  list(){
    return this.httpClient.get<User[]>(`${this.BASE_API_URL_USERS}/all`)
      .pipe(first());
  }

  getById(id: number){
    return this.httpClient.get<User>(`${this.BASE_API_URL_USERS}/` + id)
  }

  post(userData: any){
    return this.httpClient.post<User>(`${this.BASE_API_URL_USERS}/new`, userData)
  }

  deactivateUser(userId: number){
    return this.httpClient.post(`${this.BASE_API_URL_USERS}/deactivate/` + userId, {})
  }

  activateUser(userId: number){
    return this.httpClient.post(`${this.BASE_API_URL_USERS}/activate/` + userId, {})
  }

  update(user: User, userId: number){
    return this.httpClient.put<User>(`${this.BASE_API_URL_USERS}/` + userId, user);
  }

  deleteUserById(userId: number){
    return this.httpClient.delete(`${this.BASE_API_URL_USERS}/` + userId)
  }
}
