import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user';
import { delay, first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly API_USERS = 'http://localhost:8080/api/v1/users'

  constructor(private httpClient: HttpClient) { }

  list(){
    return this.httpClient.get<User[]>(`${this.API_USERS}/all`)
      .pipe(
      first(),
      delay(5000)
    );
  }

  post(userData: User){
    return this.httpClient.post<User>(`${this.API_USERS}/new`, userData)
  }
}
