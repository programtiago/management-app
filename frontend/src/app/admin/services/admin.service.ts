import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user/user';
import { catchError, first, Observable, throwError } from 'rxjs';
import { Department } from '../../model/department/department';
import { Equipment } from '../../model/equipment/equiment';
import { UserEquipment } from '../../model/user-equipment/user-equipment';
import { CreateEquipmentAssignUserRequest } from '../../model/equipment/equipment-create-assign-user';
import { CreateEquipmentRequest } from '../../model/equipment/equipment-create-normal';
import { UserDepartment } from '../../model/department/user-department/user-department';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly BASE_API_URL_USERS = '/api/v1/users'
  private readonly BASE_API_URL_DEPARTMENTS = '/api/v1/departments'
  private readonly BASE_API_URL_EQUIPMENTS = '/api/v1/equipments'
  private readonly BASE_API_URL_USER_EQUIPMENTS = '/api/v1/user-equipments'
  private readonly BASE_API_URL_USER_DEPARTMENTS = '/api/v1/user-departments'

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

  createUserAndAssignToEquipment(newUser: any, equipmentId: number){
    return this.httpClient.post<UserEquipment>(`${this.BASE_API_URL_USERS}/${equipmentId}`, newUser)
  }

  deactivateUser(userId: number){
    return this.httpClient.put(`${this.BASE_API_URL_USERS}/deactivate/` + userId, {})
  }

  activateUser(userId: number){
    return this.httpClient.put(`${this.BASE_API_URL_USERS}/activate/` + userId, {})
  }

  updateUser(user: User, userId: number){
    return this.httpClient.put<User>(`${this.BASE_API_URL_USERS}/` + userId, user)
      .pipe(catchError(this.handleError));
  }

  deleteUserById(userId: number){
    return this.httpClient.delete(`${this.BASE_API_URL_USERS}/` + userId)
  }




  listDepartments(){
    return this.httpClient.get<Department[]>(`${this.BASE_API_URL_DEPARTMENTS}`)
      .pipe(first());
  }

  getDepartmentById(departmentId: number){
    return this.httpClient.get<Department>(`${this.BASE_API_URL_DEPARTMENTS}/${departmentId}`)
  }

  deleteDepartmentById(departmentId: number){
    return this.httpClient.delete(`${this.BASE_API_URL_DEPARTMENTS}/` + departmentId)
  }

  getTotalEmployeesForDepartment(departmentId: number){
    return this.httpClient.get(`${this.BASE_API_URL_DEPARTMENTS}/totalEmployees/` + departmentId)
  }

  assignEmployeeToDepartment(departmentId: number, selectedUsersIds: number[]){
    return this.httpClient.post<UserDepartment[]>(`${this.BASE_API_URL_USER_DEPARTMENTS}/${departmentId}/users`, selectedUsersIds);
  }



  getEquipments(){
    return this.httpClient.get<Equipment[]>(`${this.BASE_API_URL_EQUIPMENTS}`).pipe(first())
  }

  getEquipmentsAvailable(){
    return this.httpClient.get<Equipment[]>(`${this.BASE_API_URL_EQUIPMENTS}/all-available`)
      .pipe(catchError((error: HttpErrorResponse) => this.handleError(error))
    );
  }

  getEquipmentById(equipmentId: number):Observable<Equipment>{
    return this.httpClient.get<Equipment>(`${this.BASE_API_URL_EQUIPMENTS}/${equipmentId}`)
  }

  deactivateEquipmentById(equipmentId: number){
    return this.httpClient.put(`${this.BASE_API_URL_EQUIPMENTS}/deactivate/${equipmentId}`, {})
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
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      console.error('An error occurred:', error.error.message);
  }else{
    // Server-side error
    console.error(
      `Backend returned code ${error.status}, ` +
      `body was: ${error.error.errors}`);

      switch (error.status){
        case 400:
          console.log("Bad request")
          break;
        case 404:
          console.log("Not found")
          break;
        case 500:
          console.log("Internal server error")
          break;
        default:
          console.log("Error code: " + error.status)
          break;
      }
    }
      return throwError('Something bad happened; please try again later.');
  }
}
