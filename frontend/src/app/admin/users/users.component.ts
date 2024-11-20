import { Component } from '@angular/core';
import { AdminService } from '../services/admin.service';
import { catchError, Observable, of } from 'rxjs';
import { User } from '../../model/user';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent {

  users$: Observable<User[]>;
  displayedColumns: String[] = ['firstName', 'lastName', 'workNumber', 'department', 'registryDate', 'isActive', 
    'userRole',  'email', 'contactNumber', 'updatedAt', 'birthdayDate', 'actions'
  ]
  /*
  users: User[] = [
    { _id: '1', firstName: 'Tiago', lastName: 'Silva', workNumber: 30035, department: 'IT', registryDate: '15/11/2021', isActive: true,
      userRole: 'ADMIN', email: 'programtiagodev@gmail.com', contactNumber: '912568714', updatedAt: ''
    }
  ];
  */

  constructor(private adminService: AdminService, private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ){
    this.users$ = this.adminService.list().pipe(
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

  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route})
  }

}
