import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user';
import { first, Observable } from 'rxjs';
import { Department } from '../../model/department';
import { Equipment } from '../../model/equiment';
import { UserEquipment } from '../../model/user-equipment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly BASE_API_URL_USERS = '/api/v1/users'
  private readonly BASE_API_URL_DEPARTMENTS = '/api/v1/departments'
  private readonly BASE_API_URL_EQUIPMENTS = '/api/v1/equipments'
  private readonly BASE_API_URL_USER_EQUIPMENTS = '/api/v1/user-equipments'

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

  deleteDepartmentById(departmentId: number){
    return this.httpClient.delete(`${this.BASE_API_URL_DEPARTMENTS}/` + departmentId)
  }

  getTotalEmployeesForDepartment(departmentId: number){
    return this.httpClient.get(`${this.BASE_API_URL_DEPARTMENTS}/totalEmployees/` + departmentId)
  }

  getEquipments(){
    return this.httpClient.get<Equipment[]>(`${this.BASE_API_URL_EQUIPMENTS}/all`).pipe(first())
  }

  getEquipmentById(equipmentId: number):Observable<Equipment>{
    return this.httpClient.get<Equipment>(`${this.BASE_API_URL_EQUIPMENTS}/${equipmentId}`)
  }

  assignEquipmentToUser(userId: number, equipmentId: number): Observable<UserEquipment>{
    return this.httpClient.post<UserEquipment>(`${this.BASE_API_URL_USER_EQUIPMENTS}/${userId}/equipment/${equipmentId}`, {})
  }
}
