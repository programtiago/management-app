import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../model/user/user';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { AdminService } from '../../services/admin.service';
import { catchError, of } from 'rxjs';
import { Department } from '../../../model/department/department';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-modal-users-department',
  templateUrl: './modal-users-department.component.html',
  styleUrl: './modal-users-department.component.scss'
})
export class ModalUsersDepartmentComponent {

  displayedColumns: string[] = ['firstName', 'lastName', 'workNumber', 'isActive', 'userRole', 'actions']

  departmentSelectedHasEmployees: boolean = false;
  departments: Department[] = []

  constructor(public dialogRef: MatDialogRef<ModalUsersDepartmentComponent>,
      @Inject(MAT_DIALOG_DATA) public data: { users: User[], departmentId: number }, private dialog: MatDialog, private adminService: AdminService, 
      private snackBar: MatSnackBar){
        for (let i = 0; i < data.users.length; i++){
          console.log(data.users[i])
          if (data.users[i].id == null || data.users[i].id == undefined || this.data.users.length == 0){
            this.departmentSelectedHasEmployees = false;
          }
          this.departmentSelectedHasEmployees = true;
        }
        if (this.departmentSelectedHasEmployees == false){
          this.onError("No employees to show on this department !")
        } 
  }


  removeUserFromDepartment(departmentId: number, userId: number){
    this.adminService.removeUserFromDepartment(departmentId, userId).pipe(
      catchError(error => {
        const statusCode = error.status;  
        this.onError(`Error ${statusCode}: Unable to remove user from department`);
          return of(null)
        })
      ).subscribe()
        this.dialogRef.close();
        this.loadDepartments();
        this.snackBar.open("User removed from department sucessfully ! ", "X")
    }

    //reload departments after delete a employee from department
    loadDepartments(){
      this.adminService.listDepartments().subscribe((res) => {
        this.departments = res;
      })
    }

    onClose(){
      this.dialogRef.close();
    }

    onError(errorMsg: string){
      this.dialog.open(ErrorDialogComponent, {
            data: 
              errorMsg
      })
    }
}
