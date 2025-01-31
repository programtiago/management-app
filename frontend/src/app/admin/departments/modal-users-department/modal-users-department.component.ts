import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../model/user/user';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-modal-users-department',
  templateUrl: './modal-users-department.component.html',
  styleUrl: './modal-users-department.component.scss'
})
export class ModalUsersDepartmentComponent {

  displayedColumns: string[] = ['firstName', 'lastName', 'workNumber', 'isActive', 'userRole', 'actions']

  departmentSelectedHasEmployees: boolean = false;

  constructor(public dialogRef: MatDialogRef<ModalUsersDepartmentComponent>,
      @Inject(MAT_DIALOG_DATA) public users: User[], private adminService: AdminService){
        for (let i = 0; i < users.length; i++){
          if (users[i].id == null || users[i].id == undefined || this.users.length == 0){
            this.departmentSelectedHasEmployees = false;
          }
          this.departmentSelectedHasEmployees = true;
        }
        if (this.departmentSelectedHasEmployees == false){
          this.dialogRef.updateSize('400px', '350px')
        }
      }

      removeUserFromDepartment(userId: number){

      }


}
