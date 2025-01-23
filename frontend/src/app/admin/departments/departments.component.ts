import { Component } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import { Department } from '../../model/department/department';
import { AdminService } from '../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-departments',
  templateUrl: './departments.component.html',
  styleUrl: './departments.component.scss'
})
export class DepartmentsComponent {

  departments$: Observable<Department[]>;

  constructor(private adminService: AdminService, private dialog : MatDialog, private snackBar: MatSnackBar){
    this.departments$ = this.adminService.listDepartments().pipe(
      catchError(error => {
        this.onError('Error loading departments.')
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

}
