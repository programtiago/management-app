import { Component, ViewChild } from '@angular/core';
import { AdminService } from '../services/admin.service';
import { catchError, Observable, of, tap } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { UserPage } from '../../model/user/user-page';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent {

  users$: Observable<UserPage> | null = null;

  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator

  pageIndex = 0;
  pageSize = 10;
  
  constructor(private adminService: AdminService, private dialog: MatDialog){
    this.refresh();
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10}){
    this.users$ = this.adminService.listAllUsers(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        tap(() => {
          this.pageIndex = pageEvent.pageIndex;
          this.pageSize = pageEvent.pageSize;
        }),
        catchError(error => {
          this.onError('Error loading users');
          return of({ content: [], totalElements: 0, totalPages: 0})
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
