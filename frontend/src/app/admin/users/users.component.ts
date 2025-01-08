import { Component } from '@angular/core';
import { AdminService } from '../services/admin.service';
import { catchError, Observable, of } from 'rxjs';
import { User } from '../../model/user/user';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent {

  users$: Observable<User[]>;
  
  constructor(private adminService: AdminService, private dialog: MatDialog, private snackbar: MatSnackBar
  ){
    this.users$ = this.adminService.listUsers().pipe(
      catchError(error => {
        this.onError('Error loading the users.')
        return of([])
      })
    );
  }

  onError(errorMsg: string){
    this.dialog.open(ErrorDialogComponent, {
      data: 
        errorMsg
    })
  }
}
