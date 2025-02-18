import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../model/user/user';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';

@Component({
  selector: 'app-modal-users-department',
  templateUrl: './modal-users-department.component.html',
  styleUrl: './modal-users-department.component.scss'
})
export class ModalUsersDepartmentComponent {

  displayedColumns: string[] = ['firstName', 'lastName', 'workNumber', 'isActive', 'userRole', 'actions']

  departmentSelectedHasEmployees: boolean = false;

  constructor(public dialogRef: MatDialogRef<ModalUsersDepartmentComponent>,
      @Inject(MAT_DIALOG_DATA) public users: User[], private dialog: MatDialog){
        for (let i = 0; i < users.length; i++){
          console.log(users[i])
          if (users[i].id == null || users[i].id == undefined || this.users.length == 0){
            this.departmentSelectedHasEmployees = false;
          }
          this.departmentSelectedHasEmployees = true;
        }
        if (this.departmentSelectedHasEmployees == false){
          this.onError("No employees to show on this department !")
        }
      }

    removeUserFromDepartment(userId: number){

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
