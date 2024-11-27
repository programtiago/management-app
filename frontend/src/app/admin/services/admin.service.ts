import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user';
import { first } from 'rxjs';
import { Department } from '../../model/department';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly BASE_API_URL_USERS = '/api/v1/users'
  private readonly BASE_API_URL_DEPARTMENTS = '/api/v1/departments'

  constructor(private httpClient: HttpClient) { }

  listUsers(){
    return this.httpClient.get<User[]>(`${this.BASE_API_URL_USERS}/all`)
      .pipe(first());
  }

  getUserById(id: number){
    return this.httpClient.get<User>(`${this.BASE_API_URL_USERS}/` + id)
  }

  createUser(userData: any){
    return this.httpClient.post<User>(`${this.BASE_API_URL_USERS}/new`, userData)
  }

  deactivateUser(userId: number){
    return this.httpClient.post(`${this.BASE_API_URL_USERS}/deactivate/` + userId, {})
  }

  activateUser(userId: number){
    return this.httpClient.post(`${this.BASE_API_URL_USERS}/activate/` + userId, {})
  }

  updateUser(user: User, userId: number){
    return this.httpClient.put<User>(`${this.BASE_API_URL_USERS}/` + userId, user);
  }

  deleteUserById(userId: number){
    return this.httpClient.delete(`${this.BASE_API_URL_USERS}/` + userId)
  }

  listDepartments(){
    return this.httpClient.get<Department[]>(`${this.BASE_API_URL_DEPARTMENTS}/all`)
      .pipe(first());
  }
}
