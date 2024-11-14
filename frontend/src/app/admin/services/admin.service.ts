import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user';
import { delay, first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly API = 'http://localhost:8080/api/v1/users/all'

  constructor(private httpClient: HttpClient) { }

  list(){
    return this.httpClient.get<User[]>(this.API)
      .pipe(
      first(),
      delay(5000)
    );
  }
}
