import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Department } from '../../../model/department/department';
import { catchError, Observable, of } from 'rxjs';
import { User } from '../../../model/user/user';
import { AdminService } from '../../services/admin.service';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';

import {SelectionModel} from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-modal-assignment-department-user',
  templateUrl: './modal-assignment-department-user.component.html',
  styleUrl: './modal-assignment-department-user.component.scss'
})
export class ModalAssignmentDepartmentUserComponent{

  users: User[] = [];
  users$?: Observable<User[]>;
  selectedUserIds: number[] = []; //Ids that will be sended on the request to assign user/s to department
  selectedUser!: User;

  displayedColumns: string[] = ['select', 'firstName', 'lastName', 'workNumber', 'admissionDate'];
  dataSource = new MatTableDataSource<User>(this.users);
  selection = new SelectionModel<User>(true, []);

  constructor(public dialogRef: MatDialogRef<ModalAssignmentDepartmentUserComponent>, public dialog: MatDialog,
      @Inject(MAT_DIALOG_DATA) public data: Department, private adminService: AdminService, private snackBar: MatSnackBar){
    this.adminService.listUsers().subscribe((res) => {
    this.users = res;
    })

    this.users$ = this.adminService.listUsers().pipe(
      catchError(error => {
        this.onError(error);
        return of([])
      })
    )
  }

  onError(errorMsg: string){
    this.dialog.open(ErrorDialogComponent, {
      data: 
        errorMsg
      })
  }

  isAllSelected(){
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
        
    return numSelected === numRows;
  }

  toggleAllRows(){
    if (this.isAllSelected()) {
      this.selection.clear();
        return;
      }
      this.selection.select(...this.dataSource.data);
      }


  selectionUsers(event: MatCheckboxChange, userId: number){
      if (event.checked){
        this.adminService.getUserById(userId).subscribe((res) => {
          this.selectedUser = res;
          this.selectedUserIds.push(res.id)
        });
    }
      if (!event.source.checked){
        this.selectedUserIds = this.selectedUserIds.filter(function(user){
          return user !== userId
        })
      }
    }

  assignDepartmentToUser(){
    console.log("DEPARTMENT ID: ", this.data.id + "\nUSER IDS: " + " [ " + this.selectedUserIds + " ] ")
    this.adminService.assignEmployeeToDepartment(this.data.id, this.selectedUserIds).subscribe((res) => {
      if (res != null){
        if (this.selectedUserIds.length < 2){
          this.snackBar.open(this.selectedUserIds.length + " employee was assigned to the department: " + this.data.value, 'X')
        }else{
          this.snackBar.open(this.selectedUserIds.length + " employees were assigned to the department: " + this.data.value, 'X')
        }
        this.dialogRef.close();
      }
    });
  }  
}



    
        
        
        
    
