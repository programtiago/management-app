import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Department } from '../../../model/department/department';
import { catchError, Observable, of } from 'rxjs';
import { User } from '../../../model/user/user';
import { AdminService } from '../../services/admin.service';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';

import {SelectionModel} from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-modal-assignment-department-user',
  templateUrl: './modal-assignment-department-user.component.html',
  styleUrl: './modal-assignment-department-user.component.scss'
})
export class ModalAssignmentDepartmentUserComponent{

  users: User[] = [];
  users$?: Observable<User[]>;
  selectedUserIds: number[] = [];

  displayedColumns: string[] = ['select', 'firstName', 'lastName', 'workNumber', 'admissionDate'];
  dataSource = new MatTableDataSource<User>(this.users);
  selection = new SelectionModel<User>(true, []);

  constructor(public dialogRef: MatDialogRef<ModalAssignmentDepartmentUserComponent>, public dialog: MatDialog,
      @Inject(MAT_DIALOG_DATA) public data: Department, private adminService: AdminService){
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

    isAllSelected() {
      const numSelected = this.selection.selected.length;
      const numRows = this.dataSource.data.length;
      return numSelected === numRows;
    }

    toggleAllRows() {
      if (this.isAllSelected()) {
        this.selection.clear();
        return;
      }
  
      this.selection.select(...this.dataSource.data);
    }


}

    
        
        
        
    
