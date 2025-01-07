import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user';
import { catchError, delay, first, Observable, throwError } from 'rxjs';
import { Department } from '../../model/department';
import { Equipment } from '../../model/equiment';
import { UserEquipment } from '../../model/user-equipment';
import { CreateEquipmentAssignUserRequest } from '../../model/equipment-create-assign-user';
import { CreateEquipmentRequest } from '../../model/equipment-create-normal';

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
    return this.httpClient.get<User[]>(`${this.BASE_API_URL_USERS}`)
      .pipe(first());
  }

  getUserById(id: number){
    return this.httpClient.get<User>(`${this.BASE_API_URL_USERS}/` + id)
  }

  createUser(userData: any){
    return this.httpClient.post<User>(`${this.BASE_API_URL_USERS}`, userData)
  }

  deactivateUser(userId: number){
    return this.httpClient.put(`${this.BASE_API_URL_USERS}/deactivate/` + userId, {})
  }

  activateUser(userId: number){
    return this.httpClient.put(`${this.BASE_API_URL_USERS}/activate/` + userId, {})
  }

  updateUser(user: User, userId: number){
    return this.httpClient.put<User>(`${this.BASE_API_URL_USERS}/` + userId, user);
  }


  deleteUserById(userId: number){
    return this.httpClient.delete(`${this.BASE_API_URL_USERS}/` + userId)
  }

  listDepartments(){
    return this.httpClient.get<Department[]>(`${this.BASE_API_URL_DEPARTMENTS}`)
      .pipe(first());
  }

  deleteDepartmentById(departmentId: number){
    return this.httpClient.delete(`${this.BASE_API_URL_DEPARTMENTS}/` + departmentId)
  }

  getTotalEmployeesForDepartment(departmentId: number){
    return this.httpClient.get(`${this.BASE_API_URL_DEPARTMENTS}/totalEmployees/` + departmentId)
  }




  getEquipments(){
    return this.httpClient.get<Equipment[]>(`${this.BASE_API_URL_EQUIPMENTS}`).pipe(first())
  }

  getEquipmentsAvailable(){
    return this.httpClient.get<Equipment[]>(`${this.BASE_API_URL_EQUIPMENTS}/all-available`)
      .pipe(first(), delay(2000),
      catchError((error: HttpErrorResponse) => this.handleError(error))
    );
  }

  getEquipmentById(equipmentId: number):Observable<Equipment>{
    return this.httpClient.get<Equipment>(`${this.BASE_API_URL_EQUIPMENTS}/${equipmentId}`)
  }

  deleteEquipmentById(equipmentId: number){
    return this.httpClient.delete(`${this.BASE_API_URL_EQUIPMENTS}/${equipmentId}`)
  }

  //Normal post equipment object
  createEquipmentWithNoAssign(newEquipment: CreateEquipmentRequest){
    return this.httpClient.post<Equipment>(`${this.BASE_API_URL_EQUIPMENTS}`, newEquipment);
  }

  createEquipmentAndAssignToUser(newEquipment: CreateEquipmentAssignUserRequest, userId: number){
    return this.httpClient.post<Equipment>(`${this.BASE_API_URL_EQUIPMENTS}/${userId}`, newEquipment)
  }

  getMultipleEquipmenstByIds(equipmentIds: number[]): Observable<Equipment[]>{
    return this.httpClient.post<Equipment[]>(`${this.BASE_API_URL_EQUIPMENTS}/multipleEquipments`, equipmentIds);
  }

  assignEquipmentToUser(userId: number, equipmentId: number): Observable<UserEquipment>{
    return this.httpClient.post<UserEquipment>(`${this.BASE_API_URL_USER_EQUIPMENTS}/${userId}/equipment/${equipmentId}`, {})
  }

  assignMultipleEquipmentsToUser(userId: number, equipmentsIds: number[]){
    console.log("Equipments ids : " + equipmentsIds)
    return this.httpClient.post<UserEquipment[]>(`${this.BASE_API_URL_USER_EQUIPMENTS}/${userId}/equipments`,
      equipmentsIds
    )
  }

  getUserEquipments(): Observable<UserEquipment[]>{
    return this.httpClient.get<UserEquipment[]>(`${this.BASE_API_URL_USER_EQUIPMENTS}`)
  }

  getEquipmentsByUserId(userId: number): Observable<UserEquipment[]>{
    return this.httpClient.get<UserEquipment[]>(`${this.BASE_API_URL_USER_EQUIPMENTS}/${userId}/equipments`)
  }

  returnEquipmentFromUser(userId: number, equipmentId: number){
    return this.httpClient.delete(`${this.BASE_API_URL_USER_EQUIPMENTS}/${userId}/equipment/${equipmentId}`)
  }

  private handleError(error: HttpErrorResponse){
    console.error('An error occurred:', error);

    if (error.error.errors instanceof ErrorEvent){
      return throwError('A client-side error occurred: ' + error.error.message);
    }else{
      return throwError(`Server returned code: ${error.status}, error message is: ${error.error.errors}`)
    }
  }
}
